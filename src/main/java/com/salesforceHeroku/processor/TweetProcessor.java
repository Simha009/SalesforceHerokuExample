package com.salesforceHeroku.processor;

import com.google.common.collect.Lists;
import com.salesforceHeroku.bean.Tweet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by vivek on 4/8/14.
 */
public class TweetProcessor {

    private static List<String> negativeWords = Lists.newArrayList();
    private static List<String> happyWords = Lists.newArrayList();
    private static List<String> unhappyWords = Lists.newArrayList();

    static {
        initialize();
    }

    public static double detectMood(List<Tweet> tweets) {
        if (tweets.isEmpty()) {
            return 0;
        }
        double total = 0;

        for (Tweet tweet : tweets) {
            total = getScore(tweet);
        }

        return total / tweets.size();
    }

    private static double getScore(Tweet tweet) {
        double happyScore = getScoreFromList(tweet.getMessage(), happyWords); //getHappyScore
        double unhappyScore = getScoreFromList(tweet.getMessage(), unhappyWords); //getUnhappyScore
        return (happyScore - unhappyScore);
    }

    public static double detectMood(Tweet tweet) {
        if (tweet == null || tweet.getMessage() == null) {
            return 0;
        }
        return getScore(tweet);
    }


    private static double getScoreFromList(String message, List<String> wordsList) {
        double score = 0;
        String[] words = message.split(" ");

        for (int i = 0; i < words.length; i++) {
            if (wordsList.contains(words[i].toLowerCase())) {

                if (i > 0) {
                    if (negativeWords.contains(words[i - 1].toLowerCase())) {
                        score -= 2; //correct for future +1 addition
                    }
                }
                score += 1;
            }
        }

        return score;
    }


    private static void initialize() {
        //add negative words
        negativeWords.add("not");
        negativeWords.add("no");
        negativeWords.add("never");
        negativeWords.add("seldom");


        //add happy words
        addWordsFromFileToList(happyWords, "positive_words.txt");

        //add unhappy words
        addWordsFromFileToList(unhappyWords, "negative_words.txt");
    }

    private static void addWordsFromFileToList(List list, String fileName) {
        BufferedReader file = new BufferedReader(new InputStreamReader(TweetProcessor.class.getClassLoader().getResourceAsStream(fileName)));
        String line = null;

        try {
            while ((line = file.readLine())!= null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
