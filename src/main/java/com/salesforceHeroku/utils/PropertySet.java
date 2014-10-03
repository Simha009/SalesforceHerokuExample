package com.salesforceHeroku.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by vivek on 2/10/14.
 */
public class PropertySet {

    public static Properties appProperties = new Properties();

    static{
        InputStream appProps = PropertySet.class.getClassLoader().getResourceAsStream("app.properties");
        try {
            appProperties.load(appProps);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getPropertyValue(String key){
        return appProperties.get(key);
    }

}
