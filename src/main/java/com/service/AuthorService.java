package com.service;

import com.dto.AuthorDto;

/**
 * {@link AuthorService} interface binds realization part with user
 * and binds {@link com.dao.AuthorDao} layer.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

public interface AuthorService extends CRUDService<AuthorDto> {

    /**
     * This is an interface method pass author id to the
     * {@link com.dao.AuthorDao #deleteAnnouncementsByAuthorId(int)}
     * to remove all announcements by a specific author id.
     *
     * @param id int
     */
    void deleteAnnouncementsByAuthorId(int id);

    /**
     * This is method for authentication author and generate
     * token nd send it to the client.
     *
     * @param authorDto {@link AuthorDto}.
     * @return string {@link String}.
     */
    String authentication(AuthorDto authorDto);
}
