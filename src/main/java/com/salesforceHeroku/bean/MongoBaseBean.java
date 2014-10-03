package com.salesforceHeroku.bean;

/**
 * Created by vivek on 4/8/14.
 */

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * User: santa
 * Date: 29/12/13
 * Time: 11:48 AM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MongoBaseBean implements Serializable {
    private static final long serialVersionUID = -1L;
    @JsonIgnore
    private String id;

    @JsonIgnore
    private boolean deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "MongoBaseBean{" +
                "id='" + id + '\'' +
                ", deleted=" + deleted +
                '}';
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


}

