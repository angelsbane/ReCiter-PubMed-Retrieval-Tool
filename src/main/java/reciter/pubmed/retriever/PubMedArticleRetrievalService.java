package reciter.pubmed.retriever;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import reciter.model.pubmed.PubMedArticle;
import reciter.pubmed.callable.PubMedUriParserCallable;
import reciter.pubmed.querybuilder.PubmedXmlQuery;
import reciter.pubmed.xmlparser.PubmedEFetchHandler;
import reciter.pubmed.xmlparser.PubmedESearchHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PubMedArticleRetrievalService {

    private final static Logger slf4jLogger = LoggerFactory.getLogger(PubMedArticleRetrievalService.class);

    /**
     * Initializes and starts threads that handles the retrieval process. Partition the number of articles
     * into manageable pieces and ask each thread to handle one partition.
     */
    public List<PubMedArticle> retrieve(String pubMedQuery) throws IOException {

        int numberOfPubmedArticles = getNumberOfPubMedArticles(pubMedQuery);
        List<PubMedArticle> pubMedArticles = new ArrayList<>();

        if (numberOfPubmedArticles <= 2000) {
            ExecutorService executor = Executors.newWorkStealingPool();

            // Get the count (number of publications for this query).
            PubmedXmlQuery pubmedXmlQuery = new PubmedXmlQuery();
            pubmedXmlQuery.setTerm(pubMedQuery);

            slf4jLogger.info("retMax=[" + pubmedXmlQuery.getRetMax() + "], pubMedQuery=[" + pubMedQuery + "], "
                    + "numberOfPubmedArticles=[" + numberOfPubmedArticles + "].");

            // Retrieve the publications retMax records at one time and store to disk.
            int currentRetStart = 0;

            List<Callable<List<PubMedArticle>>> callables = new ArrayList<>();

            // Use the retstart value to iteratively fetch all XMLs.
            while (numberOfPubmedArticles > 0) {
                // Get webenv value.
                pubmedXmlQuery.setRetStart(currentRetStart);
                String eSearchUrl = pubmedXmlQuery.buildESearchQuery();

                pubmedXmlQuery.setWevEnv(PubmedESearchHandler.executeESearchQuery(eSearchUrl).getWebEnv());

                // Use the webenv value to retrieve xml.
                String eFetchUrl = pubmedXmlQuery.buildEFetchQuery();
                slf4jLogger.info("eFetchUrl=[" + eFetchUrl + "].");

                callables.add(new PubMedUriParserCallable(new PubmedEFetchHandler(), eFetchUrl));

                // Update the retstart value.
                currentRetStart += pubmedXmlQuery.getRetMax();
                pubmedXmlQuery.setRetStart(currentRetStart);
                numberOfPubmedArticles -= pubmedXmlQuery.getRetMax();
            }

            try {
                executor.invokeAll(callables)
                        .stream()
                        .map(future -> {
                            try {
                                return future.get();
                            } catch (Exception e) {
                                slf4jLogger.error("Unsable to retrieve result using future get.");
                                throw new IllegalStateException(e);
                            }
                        }).forEach(pubMedArticles::addAll);
            } catch (InterruptedException e) {
                slf4jLogger.error("Unable to invoke callable.", e);
            }
        } else {
            throw new IOException("Number of PubMed Articles retrieved " + numberOfPubmedArticles + " exceeded the threshold level 2000");
        }
        return pubMedArticles;
    }

    protected int getNumberOfPubMedArticles(String query) throws IOException {
        PubmedXmlQuery pubmedXmlQuery = new PubmedXmlQuery(query);
        pubmedXmlQuery.setRetMax(1);
        String fullUrl = pubmedXmlQuery.buildESearchQuery(); // build eSearch query.
        slf4jLogger.info("ESearch Query=[" + fullUrl + "]");

        PubmedESearchHandler pubmedESearchHandler = new PubmedESearchHandler();
        InputStream esearchStream = new URL(fullUrl).openStream();

        try {
            SAXParserFactory.newInstance().newSAXParser().parse(esearchStream, pubmedESearchHandler);
        } catch (SAXException | ParserConfigurationException e) {
            slf4jLogger.error("Error parsing XML file for query=[" + query + "], full url=[" + fullUrl + "]", e);
        }
        return pubmedESearchHandler.getCount();
    }
}
