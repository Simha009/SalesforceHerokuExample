package com.salesforceHeroku.listener;

import com.salesforceHeroku.bean.Tweet;
import com.salesforceHeroku.dao.TweetDAOImpl;
import com.salesforceHeroku.processor.TweetProcessor;
import com.salesforceHeroku.utils.PropertySet;
import twitter4j.*;

import java.io.IOException;

/**
 * Created by vivek on 29/9/14.
 */
public class TweetListener {

    public static TweetDAOImpl tweetDAO = new TweetDAOImpl();

    public static void main(String[] args) throws TwitterException, IOException {
        StatusListener listener = new StatusListener(){
            public void onStatus(Status status) {
                System.out.println(status.getUser().getScreenName() + " : " + status.getText());
                String text = status.getText();
                Tweet tweet = new Tweet();
                tweet.setHandle(TwitterHandles.generateRandomTweetHandle());
                //tweet.setMessage(text+" #AcmeInc");
                tweet.setDate(status.getCreatedAt());
                tweet.setScore(TweetProcessor.detectMood(tweet));
                tweetDAO.insert(tweet);
            }
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}

            @Override
            public void onScrubGeo(long l, long l2) {

            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {

            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(listener);
        FilterQuery filterQuery = new FilterQuery();
        setKeywords(filterQuery);
        twitterStream.filter(filterQuery);
    }

    private static void setKeywords(FilterQuery filterQuery) throws IOException {
        filterQuery.track(getKeyWordsToListen());
    }

    private static String[] getKeyWordsToListen()  {
        return ((String)PropertySet.getPropertyValue("listen_keywords")).split(",");
    }
}
