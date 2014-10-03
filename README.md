Salesforce-Heroku Sample Application
=========

This repository contains sample code-base for an application to extend the capabilities of Salesforce using Heroku.

What does this application do?
----
This sample application listens to Twitter for tweets with specific hash tags, scores the tweets on a numeric scale of happiness (postiveness) and stores the scored tweets into a MongoDB collection with name `tweet`.

This repository also contains a Salesforce Visualforce page `ContactInterestAnalyzer.vfp`, which can be used as a front-end to the data created by this application.