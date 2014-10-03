package com.salesforceHeroku.bean;

import com.salesforceHeroku.processor.TweetProcessor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

/**
 * Created by vivek on 4/8/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet extends MongoBaseBean {

    private String tenantId;
    private String tweetId;

    @Indexed
    private String handle;
    private String message;
    private Date date;
    private Double score;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }


    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        setScore(TweetProcessor.detectMood(this));
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Tweet(String handle, String message, Date date) {
        this.handle = handle;
        this.message = message;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "tenantId='" + tenantId + '\'' +
                ", tweetId='" + tweetId + '\'' +
                ", handle='" + handle + '\'' +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", score=" + score +
                "} " + super.toString();
    }

    public Tweet() {
    }

}
