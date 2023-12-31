package com.mandark.jira.spi.app.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.mandark.jira.spi.app.EntityDTO;
import com.mandark.jira.spi.app.SearchQuery;
import com.mandark.jira.spi.app.persistence.IEntity;


/**
 * Basic Read service for entities.
 * 
 * @param <K> Entity Unique Identifier Type
 * @param <E> {@link IEntity} type of the Entity
 * @param <ED> {@link EntityDTO} type of the Entity
 */
public interface EntityService<K, E extends IEntity<K>, ED extends EntityDTO<K, E>> {


    // Read

    /**
     * Read an entity object by it's unique ID.
     * 
     * @param id Unique ID of the entity object.
     * 
     * @return {@link EntityDTO} object OR null if no object found with the passed entity.
     */
    ED read(K id);

    /**
     * Read an entity object by it's unique ID. If a object is not found with the passed ID, a {@link RuntimeException}
     * will be thrown.
     * 
     * @param id Unique ID of the entity object.
     * @param errorIfNotFound if set to true, a {@link RuntimeException} will be thrown if no object found with the
     *        passed id.
     * 
     * @return {@link EntityDTO} object OR null if no object found with the passed entity.
     */
    ED read(K id, boolean errorIfNotFound);

    /**
     * Read multiple entity objects by their unique IDs.
     * 
     * @param idCollection a {@link Collection} of unique IDs of the entity objects.
     * 
     * @return a {@link Map} of {@link EntityDTO}s mapped to their Unique IDs.
     */
    Map<K, ED> read(Collection<K> idCollection);



    // Read :: Paginated

    /**
     * Get the count of all entity objects.
     * 
     * @return total count of entity objects.
     */
    int count();

    /**
     * Read entity objects paginated.
     * 
     * @param pageNo pagination - page number
     * @param pageSize pagination - page size
     * 
     * @return A {@link List} of {@link EntityDTO} objects of entity.
     */
    List<ED> read(int pageNo, int pageSize);



    // Search

    /**
     * Get the count of all entity objects.
     * 
     * @return total count of entity objects.
     */
    int count(SearchQuery<? extends E> searchQuery);

    /**
     * Read entity objects paginated.
     * 
     * @param pageNo pagination - page number
     * @param pageSize pagination - page size
     * 
     * @return A {@link List} of {@link EntityDTO} objects of entity.
     */
    List<ED> search(SearchQuery<? extends E> searchQuery, int pageNo, int pageSize);


}