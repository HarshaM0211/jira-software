package com.mandark.jira.spi.app.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mandark.jira.spi.app.EntityBean;
import com.mandark.jira.spi.app.EntityDTO;
import com.mandark.jira.spi.app.persistence.IDao;
import com.mandark.jira.spi.app.persistence.IEntity;
import com.mandark.jira.spi.lang.NotImplementedException;



/**
 * Abstract implementation of {@link EntityService} with additional CRUD Methods
 * 
 * @param <E> {@link IEntity} type
 * @param <EB> {@link EntityBean} type of the Entity
 * @param <ED> {@link EntityDTO} type of the Entity
 */
public abstract class AbstractJpaEntityService<K, E extends IEntity<K>, EB extends EntityBean<E>, ED extends EntityDTO<K, E>> //
        extends AbstractEntityService<K, E, ED> //
        implements EntityService<K, E, ED> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJpaEntityService.class);


    // Constructor
    // ------------------------------------------------------------------------

    protected AbstractJpaEntityService(IDao<K> dao) {
        super(dao);
    }


    // Abstract Methods
    // ------------------------------------------------------------------------

    protected abstract E createFromBean(EB bean);

    protected abstract E copyFromBean(E exEntity, EB entityBean);



    // Protected Methods
    // ------------------------------------------------------------------------

    // Create

    protected K save(final EB entityBean) {
        // Sanity checks
        if (Objects.isNull(entityBean)) {
            throw new IllegalArgumentException("#save :: Entity Bean Object is NULL");
        }

        // toEntity
        final E entity = this.createFromBean(entityBean);

        final K id = this.dao.save(entity);
        LOGGER.debug("Successfully saved entity object : {}", entity);

        return id;
    }

    protected List<K> save(final Collection<EB> entityBeans) {
        // Sanity checks
        if (Objects.isNull(entityBeans) || entityBeans.isEmpty()) {
            return new ArrayList<>();
        }

        // Entities
        final List<E> entityList = new ArrayList<>();

        // Iterate and save
        for (final EB eb : entityBeans) {
            if (Objects.isNull(eb)) {
                continue;
            }

            // toEntity
            final E entity = this.createFromBean(eb);
            entityList.add(entity);
        }

        final List<K> idList = this.dao.save(entityList);
        LOGGER.debug("Successfully completed creating entity objects : {}", entityBeans.size());

        return idList;
    }


    // Update

    protected void update(final K id, final EB entityBean) {
        // Sanity checks
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("#update :: Entity ID is NULL");
        }

        if (Objects.isNull(entityBean)) {
            throw new IllegalArgumentException("#update :: Entity Bean Object is NULL");
        }

        // existing entity
        final E exEntity = dao.read(this.getEntityClass(), id, false);

        // copy toEntity
        final E entity = this.copyFromBean(exEntity, entityBean);

        this.dao.update(id, entity);
        LOGGER.debug("Successfully updated entity object : {} - {}", id, entity);
    }


    // Purge

    protected void purge(final K id) {
        // Sanity checks
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("#purge :: Entity ID is NULL");
        }

        // Purge
        this.dao.purge(this.getEntityClass(), id);
        LOGGER.debug("Successfully purged entity object[id] : {}", id);
    }


    protected void purge(Collection<K> idsList) {
        // Sanity checks
        if (Objects.isNull(idsList) || idsList.isEmpty()) {
            return;
        }

        // Purge
        this.dao.purge(this.getEntityClass(), idsList);
        LOGGER.debug("Successfully completed purging entity objects : {}", idsList.size());
    }


    // Other

}