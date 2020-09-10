package com.service;

/**
 * This is interface {@link CRUDService} serves the data access process for {@link T} in database for crud operations
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

public interface CRUDService<T> {

    /**
     * This method takes an {@link T} and transmits him to the data base for saving
     *
     * @param entity {@link T}
     */
    void save(T entity);

    /**
     * This is an interface method for searching for an {@link T} by id and pass it to the data base for finding
     *
     * @param id int
     * @return {@link T}
     */
    T find(int id);

    /**
     * This is an interface method accepts the {@link T} object with the new data and pass it to the data base
     * for updating
     *
     * @param entity {@link T}
     */
    void update(T entity);

    /**
     * This interface method pass {@link T} id to the data base for deleting
     *
     * @param id int
     */
    void delete(int id);
}
