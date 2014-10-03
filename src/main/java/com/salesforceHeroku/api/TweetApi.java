package com.salesforceHeroku.api;

import com.salesforceHeroku.bean.Tweet;
import com.salesforceHeroku.dao.TweetDAOImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

/**
 * Created by vivek on 7/8/14.
 */

@Path("tweets")
public class TweetApi {
    @GET

    @Path("/handles/{handle}")
    public List<Tweet> getTweetsForHandle(@PathParam("handle") String handle) {
        TweetDAOImpl tweetDAO = new TweetDAOImpl(Tweet.class, "tenantId");
        return tweetDAO.find(Query.query(Criteria.where("handle").is(handle)).with(new Sort(Sort.Direction.DESC,"date")).limit(2000));
    }

}
