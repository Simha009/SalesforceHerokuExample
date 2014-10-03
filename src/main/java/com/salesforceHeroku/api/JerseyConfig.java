package com.salesforceHeroku.api;

import com.salesforceHeroku.filter.CORSFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by vivek on 7/8/14.
 */
public class JerseyConfig extends ResourceConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(JerseyConfig.class);

    /**
     * Register JAX-RS application components.
     */
    public JerseyConfig() {

        //Controllers
        packages("com.salesforceHeroku");

        //multi-part
        register(MultiPartFeature.class);

        //Response Filters
        register(JacksonFeature.class);
        register(CORSFilter.class);

     }
}
