package com.repository;

import com.domain.Author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * {@link AuthorRepository} interface serves to simplify the data
 * access process for {@link Author} in database, binds database to
 * implementation part,has extends CRUD methods from
 * {@link JpaRepository} and queries for getting need additional data
 * from database.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    /**
     * This is method for finding author in data base by authorName.
     *
     * @param name {@link String}.
     * @return author {@link Author} found by authorName.
     */
    Author findByName(String name);

    /**
     * This is method for deleting all user_role from database when
     * deletes author.
     *
     * @param id int.
     */
    @Query(value = "DELETE FROM user_role WHERE user_id = :id",
            nativeQuery = true)
    @Modifying
    void deleteFromUserRole(@Param(value = "id") int id);
}
