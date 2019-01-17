# ReCiter-PubMed-Retrieval-Tool

![Build Status](https://codebuild.us-east-1.amazonaws.com/badges?uuid=eyJlbmNyeXB0ZWREYXRhIjoiV0w5MExveXNpdzBrL1hRMDlmYjhLNjRFek1NdTVxMk9BOWZEcDdxVENuZXNQS0FGdlZxY3h3Smd1b3ArTVhNTzUvK1pXVlI3N1JkdmRXNiswc1VPcHNjPSIsIml2UGFyYW1ldGVyU3BlYyI6IllneSs4bG9NNmMyeEtWOTkiLCJtYXRlcmlhbFNldFNlcmlhbCI6MX0%3D&branch=master)
![version](https://img.shields.io/badge/version-1.0-blue.svg?maxAge=2592000)
[![codebeat badge](https://codebeat.co/badges/26e88904-3263-47f3-a246-7c65979cca46)](https://codebeat.co/projects/github-com-wcmc-its-reciter-pubmed-retrieval-tool-master)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
[![Pending Pull-Requests](http://githubbadges.herokuapp.com/wcmc-its/ReCiter-PubMed-Retrieval-Tool/pulls.svg?style=flat)](https://github.com/wcmc-its/ReCiter-PubMed-Retrieval-Tool/pulls)
[![Open Issues](http://githubbadges.herokuapp.com/wcmc-its/ReCiter-PubMed-Retrieval-Tool/issues.svg?style=flat)](https://github.com/wcmc-its/ReCiter-PubMed-Retrieval-Tool/issues)
[![star this repo](http://githubbadges.com/star.svg?user=wcmc-its&repo=ReCiter-PubMed-Retrieval-Tool&style=flat)](https://github.com/wcmc-its/ReCiter-PubMed-Retrieval-Tool)
[![fork this repo](http://githubbadges.com/fork.svg?user=wcmc-its&repo=ReCiter-PubMed-Retrieval-Tool&style=flat)](https://github.com/wcmc-its/ReCiter-PubMed-Retrieval-Tool/fork)
[![Github All Releases](https://img.shields.io/github/downloads/wcmc-its/ReCiter-PubMed-Retrieval-Tool/total.svg)]()
[![Open Source Love](https://badges.frapsoft.com/os/v3/open-source.svg?v=102)](https://github.com/wcmc-its/ReCiter-PubMed-Retrieval-Tool/) 

ReCiter PubMed Retrieval Tool is a REST API for retrieving PubMed articles from https://www.ncbi.nlm.nih.gov/pubmed/. You can pass a PubMed query to the REST API and it will return list of PubMed article objects.

## Installing


## Configuring the API key

As a default, the PubMed Retrieval Tool works without a PubMed API key, however we recommend that you get an API key issued by NCBI. This allows you to make more requests per second.

Here's how you can get an API key and use it with this application:

1. Get an API key as per [these directions](https://ncbiinsights.ncbi.nlm.nih.gov/2017/11/02/new-api-keys-for-the-e-utilities/) from NCBI.

2. Enter the API key into your Environment Variables where field name = `PUBMED_API_KEY`.
- If you are deploying locally, go to terminal and write `export PUBMED_API_KEY={api-key}`. 
- If you are deploying to an AWS instance, [add the environment variable](https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/environments-cfg-softwaresettings.html#environments-cfg-softwaresettings-console) in the Elastic Beanstalk configuration section.




