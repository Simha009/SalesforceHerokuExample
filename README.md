Salesforce-Heroku Sample Application
=========

This repository contains sample code-base for an application to extend the capabilities of Salesforce using Heroku.

This application was demonstrated during [Dreamforce 2014](http://www.salesforce.com/dreamforce/DF14/) at the session "Add Big Data Analytics to Your Salesforce App Using Heroku
"

What does this application do?
----
This sample application listens to Twitter for tweets with specific hash tags, scores the tweets on a numeric scale of happiness (postiveness) and stores the scored tweets into a MongoDB collection with name `tweet`.

This repository also contains a Salesforce Visualforce page `ContactInterestAnalyzer.vfp`, which can be used as a front-end to the data created by this application.

Pre-requisites
----
1. [Java 7 SE Development Kit](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html) (Preferably Oracle Java)
2. [Maven 3+](http://maven.apache.org/download.cgi)
3. MongoDB (Preferably as a web service, like [Compose.io](http://www.compose.io))
4. [Heroku](http://www.heroku.com) Account
5. Your own [Twitter application](https://apps.twitter.com/) with
  * Consumer key
  * Consumer Secret
6. [Salesforce.com](http://www.salesforce.com) Developer Account
7. A [Salesforce.com](http://www.salesforce.com) Developer Organization

How to use?
----
* Clone this repository to your local computer

    ```
    git clone https://github.com/vivganes/SalesforceHerokuExample.git
    ```
* Fill in appropriate Twitter Application credentials in ```src/main/resources/twitter4j.properties```

    ```
    debug=false
    oauth.consumerKey=
    oauth.consumerSecret=
    oauth.accessToken=
    oauth.accessTokenSecret=
    ```
* Fill in the list of keywords to be listened and appropriate MongoDB credentials in ```src/main/resources/app.properties```

    ```
    listen_keywords=#AcmeInc,#Gainsight
    mongo_hosts=mongohost.example.com
    mongo_ports=10094
    mongo_dbname=mymongodb
    mongo_username=mydbuser
    mongo_pass=mydbpass
    ```
* Create a Heroku application and [deploy this app](https://devcenter.heroku.com/articles/git) into it.

* Go to [Heroku Apps Dashboard](http://dashboard.heroku.com) and set worker dyno to size '1' and web dyno size to '1'.

* Copy the contents of the file ```ContactInterestAnalyzer.page``` and paste it into a newly created Visualforce Page in your Salesforce Developer organization.

* Add a custom field to with name ```Twitter_Handle__c``` to the Contact Salesforce Object.