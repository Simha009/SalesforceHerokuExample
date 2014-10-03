package com.salesforceHeroku.dao;

import com.salesforceHeroku.bean.MongoBaseBean;
import com.salesforceHeroku.database.MongoClientWrapper;
import com.mongodb.*;
import com.salesforceHeroku.utils.PropertySet;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.InvalidMongoDbApiUsageException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * User: Swatantra Agrawal
 * Date: 20/01/14
 * Time: 11:13 AM
 */

/**
 *
 * @param <E> The type of Entity
 */
public abstract class AbstractEntityDaoImpl<E extends MongoBaseBean> implements AbstractEntityDao<E> {
    protected Class<E> clazz;
    private String tenantIdFieldName;
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractEntityDaoImpl.class);
    private MongoTemplate globalMongoTemplate;

    protected AbstractEntityDaoImpl(Class<E> clazz, String tenantIdFieldName) {
        this.clazz = clazz;
        this.tenantIdFieldName = tenantIdFieldName;
    }

    /**
     * Gets the list of Entity
     *
     * @return The entities
     */
    @Override
    public List<E> find() {
        return find(null);
    }

    /**
     * Gets the Entity
     *
     * @param query The Query
     * @return The entities
     */
    @Override
    public E findOne(Query query) {
        return getMongoTemplate().findOne(getTenantQuery(query), clazz);
    }

    /**
     * Gets the list of Entity
     *
     * @param query The Query
     * @return The entities
     */
    @Override
    public List<E> find(Query query) {
        return getMongoTemplate().find(getTenantQuery(query), clazz);
    }

    /**
     * Inserts the entity
     *
     * @param entity The entity
     * @return Entity with all ID's set
     */
    @Override
    public E insert(E entity) {
        entity.setId(null);
        setValues(entity);
        getMongoTemplate().insert(entity);
        return entity;
    }

    public E insert(E entity, String collectionName) {
        entity.setId(null);
        setValues(entity);
        getMongoTemplate().insert(entity, collectionName);
        return entity;
    }

    @Override
    public E update(E entity) {
        Query query = Query.query(Criteria.where("_id").is(entity.getId()));
        boolean exist = getMongoTemplate().exists(getTenantQuery(query), clazz);

        setValues(entity);
        getMongoTemplate().save(entity);
        return entity;
    }

    public E update(E entity, String collectionName) {
        Query query = Query.query(Criteria.where("_id").is(entity.getId()));
        boolean exist = getMongoTemplate().exists(getTenantQuery(query), clazz, collectionName);

        setValues(entity);
        getMongoTemplate().save(entity, collectionName);
        return entity;
    }

    @Override
    public void updateFirst(Query query, Update update) {
        getMongoTemplate().updateFirst(getTenantQuery(query), update, clazz);
    }

    @Override
    public void updateMulti(Query query, Update update) {
        getMongoTemplate().updateMulti(getTenantQuery(query), update, clazz);
    }

    /**
     * Inserts all the entities
     *
     * @param entities The entities
     * @return Entities with all the ID's set
     */
    @Override
    public List<E> insertAll(List<E> entities) {
        if (CollectionUtils.isNotEmpty(entities)) {
            for (E entity : entities) {
                entity.setId(null);
                setValues(entity);
            }
            getMongoTemplate().insertAll(entities);
        }
        return entities;
    }

    /**
     * Removes the entities based on Criteria
     *
     * @param query The Query
     */
    @Override
    public void remove(Query query) {
        getMongoTemplate().remove(getTenantQuery(query), clazz);
    }

    /**
     * Gets the count of records based on Criteria
     *
     * @param query The Query
     * @return The count
     */
    @Override
    public long count(Query query) {
        return getMongoTemplate().count(getTenantQuery(query), clazz);
    }

    @Override
    public boolean exists(Query query) {
        return getMongoTemplate().exists(getTenantQuery(query), clazz);
    }

    protected void setValues(E entity) {
        String entityId = entity.getId();
        if (entityId == null) {
            entityId = UUID.randomUUID().toString();
            setEntityId(entity, entityId);
        }
        setTenantId(entity, "number-1-tenant");
    }

    protected Query getTenantQuery(Query query) {
        if (query == null) {
            query = new Query();
        }
        if (tenantIdFieldName != null) {
            String tenantId = "number-1-tenant";
            try {
                query.addCriteria(Criteria.where(tenantIdFieldName).is(tenantId));
            } catch (InvalidMongoDbApiUsageException e) {
                String message = e.getMessage();
                if (!message.contains(tenantId)) {
                    throw e;
                }
            }
        }
        try {
            query.addCriteria(Criteria.where("deleted").is(false));
        } catch (InvalidMongoDbApiUsageException e) {
            String message = e.getMessage();
            if (!message.contains("false")) {
                throw e;
            }
        }
        return query;
    }

    /**
     * Get the Mongo Template to work on
     *
     * @return The Mongo Template
     */
    protected abstract MongoTemplate getMongoTemplate();

    /**
     * Sets the Entity Id and Tenant Id. This method is called only on insert.
     *
     * @param entity   The entity
     * @param entityId The Entity Id
     */
    protected abstract void setEntityId(E entity, String entityId);

    /**
     * Sets the Tenant Id. This is to make sure, that current context is only populated.
     *
     * @param entity The entity
     */
    protected abstract void setTenantId(E entity, String tenantId);


    private Mongo getGlobalMongo() {
        try {
            MongoClientOptions.Builder builder = MongoClientOptions.builder();
            builder.connectionsPerHost(1);
            String[] hosts = ((String)PropertySet.getPropertyValue("mongo_hosts")).split(",");
            String[] ports = ((String)PropertySet.getPropertyValue("mongo_ports")).split(",");

            if (hosts.length != ports.length) {
                LOGGER.error("The number of entry for host and port are not matching.");
                throw new RuntimeException("The number of entry for host and port are not matching.");
            }
            List<ServerAddress> serverAddresses = new ArrayList<ServerAddress>();
            for (int iterator = 0; iterator < hosts.length; iterator++) {
                serverAddresses.add(new ServerAddress(hosts[iterator], Integer.valueOf(ports[iterator])));
            }
            return getMongoClient(serverAddresses, null, builder);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private MongoClient getMongoClient(List<ServerAddress> serverAddresses, List<MongoCredential> mongoCredentials,
                                       MongoClientOptions.Builder builder) {
        //Mongo Template closes the cursors. Making this true, spawns a thread on every new MongoClient
        builder.cursorFinalizerEnabled(false);

        //If serverAddress(s) are passed then it creates a long running thread to poll each server
        if (serverAddresses.size() == 1) {
            return new MongoClientWrapper(serverAddresses.get(0), mongoCredentials, builder.build());
        }
        return new MongoClientWrapper(serverAddresses, mongoCredentials, builder.build());
    }

    public MongoTemplate getGlobalMongoTemplate() {
        if (globalMongoTemplate == null) {
            String userName = (String)PropertySet.getPropertyValue("mongo_username");
            String password = (String)PropertySet.getPropertyValue("mongo_pass");
            String dbName = (String)PropertySet.getPropertyValue("mongo_dbname");
            globalMongoTemplate =
                    new MongoTemplate(getGlobalMongo(), dbName, new UserCredentials(userName, password));
            globalMongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        }
        return globalMongoTemplate;
    }


}
