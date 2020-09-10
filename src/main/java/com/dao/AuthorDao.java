package com.dao;

import com.domain.Author;

import java.util.List;

/**
 * {@link AuthorDao} interface serves the data access process for {@link Author} in database for crud operations
 * and other operations
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

public interface AuthorDao extends CRUDDao<Author> {

    /**
     * This is an interface method removes all announcements from the database by a specific {@link Author} id
     *
     * @param id int
     */
    void deleteAnnouncementsByAuthorId(int id);

    /**
     * This is an interface method removes all suitableAds from the database by a specific {@link Author} id
     *
     * @param id int
     */
    void deleteAllSuitableAdsByAuthorId(int id);

    /**
     * This is an interface method finds and returns all authors from the database
     *
     * @return {@link List<Author>}
     */
    List<Author> getAuthors();
}
