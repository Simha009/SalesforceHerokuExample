package com.salesforceHeroku.dao;

import com.salesforceHeroku.bean.Tweet;
import com.salesforceHeroku.processor.TweetProcessor;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by vivek on 7/8/14.
 */
public class TweetDAOImplTest {

    //@Test
    public void testSingleCreationAndDeletion(){
        Tweet tweet = new Tweet();
        Date createdDate = new Date();

        tweet.setDate(createdDate);
        tweet.setMessage("Happy happy happy");
        tweet.setHandle("vivek_ganesan");

        TweetDAOImpl tweetDAO = new TweetDAOImpl(Tweet.class,"tenantId");
        tweet = tweetDAO.insert(tweet);


        Query timeQuery = Query.query(Criteria.where("date").lt(new Date()).gte(createdDate));
        List<Tweet> tweets = tweetDAO.find(timeQuery);

        double mood = TweetProcessor.detectMood(tweets);


        System.out.println(tweets);

        System.out.println(mood);

        tweetDAO.remove(timeQuery);
    }

    //@Test
    public void testRandomTweetCreationAndDeletion(){

        Date startDate = new Date(1406871858000l);
        Date endDate = new Date(1411624863000l);

        TweetDAOImpl tweetDAO = new TweetDAOImpl(Tweet.class,"tenantId");

        tweetDAO.insertAll(RandomTweetGenerator.getRandomTweets(100,startDate,endDate));


        Query timeQuery = Query.query(Criteria.where("date").lte(endDate).gte(startDate));
        List<Tweet> tweets = tweetDAO.find(timeQuery);

        System.out.println(tweets);

        //tweetDAO.remove(timeQuery);

    }
}
