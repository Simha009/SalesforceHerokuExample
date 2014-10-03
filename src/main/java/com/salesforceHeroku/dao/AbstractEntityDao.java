package com.salesforceHeroku.dao;

import com.salesforceHeroku.bean.MongoBaseBean;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by vivek on 4/8/14.
 */
public interface AbstractEntityDao<E extends MongoBaseBean> {
    List<E> find();

    E findOne(Query query);

    List<E> find(Query query);

    E insert(E entity);

    E insert(E entity, String collectionName);

    E update(E entity);

    E update(E entity, String collectionName);

    void updateFirst(Query query, Update update);

    void updateMulti(Query query, Update update);

    List<E> insertAll(List<E> entities);

    void remove(Query query);

    long count(Query query);

    boolean exists(Query query);
}
