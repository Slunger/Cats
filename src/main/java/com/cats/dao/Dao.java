package com.cats.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by andrey on 07.02.17.
 */
@Repository
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Dao {

    private static final Logger LOG = LoggerFactory.getLogger(Dao.class);

    private final int batchSize = 20;

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Return the persistent instance of the given entity class with the given identifier,
     * or null if there is no such persistent instance.
     *
     * @param entityClass a persistent class
     * @param id          an identifier
     * @return a persistent instance or null
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> entityClass, Serializable id) {
        final T entity = currentSession().get(entityClass, id);
        LOG.info("Retrieved entity - {}", entity);
        return entity;
    }


    public <T> T getBy(DetachedCriteria criteria) {
        LOG.info("Going to find entity by criteria - {}", criteria);
        final Criteria executableCriteria = criteria.getExecutableCriteria(currentSession());
        final T entity = (T) executableCriteria.uniqueResult();
        LOG.info("Found entity: {}", entity);
        return entity;
    }

    /**
     * Return all persistent instances of the given entity class,
     * or null if there is no such persistent instance.
     *
     * @param entityClass a persistent class
     * @return List of persistent instances or null
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> all(Class<T> entityClass) {
        final List<T> entities = currentSession().createCriteria(entityClass).list();
        LOG.info("Retrieved entites - {}", entities);
        return entities;
    }

    /*
     * Persist the given transient instance.
     *
     * @param entity a transient instance of a persistent class
     * @return saved entity
     */
    public <T> T save(T entity) {
        LOG.info("Going to save entity - {}", entity);
        currentSession().saveOrUpdate(entity);
        return entity;
    }

    /**
     * Update the given transient instance.
     *
     * @param entity - a transient instance of a persistent class
     * @param <T>    - updated entity
     * @return
     */
    public <T> T update(T entity) {
        LOG.info("Going to update entity - {}", entity);
        currentSession().update(entity);
        return entity;
    }

    /**
     * Persists collection of entities wrapped in list
     *
     * @param entities - list entities to save
     */
    public <T> Collection<T> saveAll(Collection<T> entities) {
        LOG.info("Going to save entities - {}", entities);
        final Session session = currentSession();
        final List<T> entitiesList = new ArrayList<>(entities);
        for (int i = 0; i < entities.size(); i++) {
            session.save(entitiesList.get(i));
            if (i % batchSize == 0) {
                session.flush();
                session.clear();
            }
        }
        return entitiesList;
    }

    /**
     * Remove a persistent instance from the datastore.
     *
     * @param entity - the instance to be removed
     */
    public <T> void delete(T entity) {
        LOG.info("Going to delete entity - {}", entity);
        currentSession().delete(entity);
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
