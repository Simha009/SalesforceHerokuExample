package com.salesforceHeroku.dao;

import com.google.common.collect.Lists;
import com.salesforceHeroku.bean.Tweet;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by vivek on 7/8/14.
 */
public class RandomTweetGenerator {

    private static List<String> handlesList = Lists.newArrayList();
    private static Random random = new Random();

    static{
        intializeHandlesList();
    }

    private static int getRandomNumberBetween(int low, int high){
        int R = random.nextInt(high - low) + low;
        return R;
    }

    private static double getRandomDoubleNumberBetween(double low, double high){
        return high + (high - low) * random.nextDouble();
    }

    public static List<Tweet> getRandomTweets(long numberOfTweets, Date startDate, Date endDate){
        List<Tweet> tweets = Lists.newArrayList();
        for(long i = 0; i<numberOfTweets; i++){
            Tweet tweet = new Tweet();
            tweet.setDate(getRandomTimeBetweenTwoDates(startDate, endDate));
            tweet.setMessage(generateRandomTweetMessage());
            tweet.setHandle(generateRandomTweetHandle());
            tweets.add(tweet);
        }
        return tweets;
    }

    private static String generateRandomTweetHandle(){
        return handlesList.get(getRandomNumberBetween(0,handlesList.size()));
    }

    /**
     * Method should generate random number that represents
     * a time between two dates.
     *
     * @return
     */
    private static Date getRandomTimeBetweenTwoDates (Date startDate,Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        return new Date(Math.round(startDate.getTime() +  (getRandomDoubleNumberBetween(0.0,0.9) * diff)));
    }


    private static String generateRandomTweetMessage(){
    
        String tweet = "";

        //chooses a random number
        int num1 = getRandomNumberBetween (0,3) ;
        int num2 = getRandomNumberBetween (0,3) ;
        int num3 = getRandomNumberBetween (0,3) ;
        int num4 = getRandomNumberBetween (0,3) ;
        int num5 = getRandomNumberBetween (0,3) ;

        //starts the random quote switch loop
        switch (num1)
        {
            case 0:
                tweet+= "I feel ";
                break;

            case 1:
                tweet+= "I am ";
                break;

            case 2:
                tweet+= "We are ";
                break;

            case 3:
                tweet+= "I am all ";
                break;
        }



        switch (num2)
        {
            case 0:
                tweet+= "happy ";
                break;

            case 1:
                tweet+= "sad ";
                break;

            case 2:
                tweet+= "excited ";
                break;

            case 3:
                tweet+= "boring ";
                break;
        }




        switch (num3)
        {
            case 0:
                tweet+= "to think about ";
                break;

            case 1:
                tweet+= "to see ";
                break;

            case 2:
                tweet+= "to hear about ";
                break;

            case 3:
                tweet+= "to describe ";
                break;
        }

        switch (num4)
        {
            case 0:
                tweet+= "silly ";
                break;

            case 1:
                tweet+= "stupid ";
                break;

            case 2:
                tweet+= "awesome ";
                break;

            case 3:
                tweet+= "exemplary ";
                break;
        }

        switch (num5)
        {
            case 0:
                tweet+= "customers #TheFakeCompany";
                break;

            case 1:
                tweet+= "conferences #TheFakeCompany";
                break;

            case 2:
                tweet+= "big data software #TheFakeCompany";
                break;

            case 3:
                tweet+= "data science #TheFakeCompany";
                break;
        }

        return tweet;
    }

    private static void intializeHandlesList() {

        handlesList.add("TomDickHarry");
        handlesList.add("i_am_the_Lord");
        handlesList.add("customer_is_king");
        handlesList.add("Indian_Maharaja");
        handlesList.add("Vivek_Ganesan");
        handlesList.add("The_Invisible_Dude");

    }

}
