package com.salesforceHeroku.listener;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

/**
 * Created by vivek on 29/9/14.
 */
public class TwitterHandles {

    private static List<String> handlesList = Lists.newArrayList();
    private static Random random = new Random();
    static{
        intializeHandlesList();
    }

    private static void intializeHandlesList() {

        handlesList.add("TomDickHarry");
        handlesList.add("i_am_the_Lord");
        handlesList.add("customer_is_king");
        handlesList.add("Indian_Maharaja");
        handlesList.add("Vivek_Ganesan");
        handlesList.add("The_Invisible_Dude");

    }


    private static int getRandomNumberBetween(int low, int high){
        int R = random.nextInt(high - low) + low;
        return R;
    }


    public static String generateRandomTweetHandle(){
        return handlesList.get(getRandomNumberBetween(0,handlesList.size()));
    }

}
