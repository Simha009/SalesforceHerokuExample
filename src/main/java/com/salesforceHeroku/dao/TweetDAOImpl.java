package com.salesforceHeroku.dao;

import com.salesforceHeroku.bean.Tweet;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by vivek on 4/8/14.
 */
public class TweetDAOImpl  extends AbstractEntityDaoImpl<Tweet> implements TweetDAO {

    public TweetDAOImpl(){
        super(Tweet.class,"tenantId");
    }

    public TweetDAOImpl(Class<Tweet> clazz, String tenantIdFieldName) {
        super(clazz, tenantIdFieldName);
    }

    @Override
    protected MongoTemplate getMongoTemplate() {
        return getGlobalMongoTemplate();
    }

    @Override
    protected void setEntityId(Tweet entity, String entityId) {
        entity.setTweetId(entityId);
    }

    @Override
    protected void setTenantId(Tweet entity, String tenantId) {
        entity.setTenantId(tenantId);
    }
}
