package com.service;

import com.dao.AuthorDao;
import com.domain.Author;

/**
 * {@link AuthorService} interface binds realization part with user and binds {@link AuthorDao} layer
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

public interface AuthorService extends CRUDService<Author> {

    /**
     * This is an interface method pass author id to the {@link AuthorDao#deleteAnnouncementsByAuthorId(int)}
     * to remove all announcements by a specific author id
     *
     * @param id int
     */
    void deleteAnnouncementsByAuthorId(int id);
}
