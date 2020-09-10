package com.dao;

/**
 * This is interface {@link CRUDDao} serves the data access process for {@link T}
 * in database for crud operations
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

public interface CRUDDao<T> {

    /**
     * This is an interface method for storing {@link T} in a database
     *
     * @param entity {@link T}
     */
    void save(T entity);

    /**
     * This is an interface method for searching for an {@link T} by ID in a database
     *
     * @param id int
     * @return t {@link T}
     */
    T find(int id);

    /**
     * This is an interface method updates the {@link T} in the data base and accepts
     * the {@link T} object with the new data
     *
     * @param entity {@link T}
     */
    void update(T entity);

    /**
     * This is an interface method removes the {@link T} from the database and accepts the ID to delete
     *
     * @param id int
     */
    void delete(int id);
}
