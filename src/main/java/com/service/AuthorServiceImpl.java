package com.service;

import com.dao.AuthorDao;
import com.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@link AuthorServiceImpl} class binds realization part with user and binds {@link AuthorDao} layer
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

@Service
public class AuthorServiceImpl implements AuthorService {

    /**
     * Field {@link AuthorDao} is object instance of {@link AuthorDao} interface,
     * it helps us to perform database manipulations
     */
    private AuthorDao authorDao;

    /**
     * This is a constructor with parameter {@link AuthorServiceImpl} that injects object gain of the {@link AuthorDao}
     *
     * @param authorDao {@link AuthorDao}
     */
    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }


    /**
     * This method takes the {@link Author} and transmits him to the {@link AuthorDao#save(Object)}
     * to save the author
     *
     * @param author {@link Author}
     */
    @Override
    public void save(Author author) {
        authorDao.save(author);
    }


    /**
     * This is method for searching for an author by id and pass {@link Author#id}
     * to the {@link AuthorDao#find(int)} to find the author in data base
     *
     * @param id int
     * @return author {@link Author}
     */
    @Override
    public Author find(int id) {
        return authorDao.find(id);
    }


    /**
     * This is method accepts the {@link Author} object with the new data and pass
     * it to the {@link AuthorDao#update(Object)} to update the author
     *
     * @param author {@link Author}
     */
    @Override
    public void update(Author author) {
        authorDao.update(author);
    }


    /**
     * This is method pass author id to the {@link AuthorDao#delete(int)} to delete the author
     *
     * @param id int
     */
    @Override
    public void delete(int id) {
        authorDao.delete(id);
    }


    /**
     * This is method pass author id to the {@link AuthorDao#deleteAnnouncementsByAuthorId(int)}
     * to remove all announcements by a specific author id
     *
     * @param id int
     */
    @Override
    public void deleteAnnouncementsByAuthorId(int id) {
        authorDao.deleteAnnouncementsByAuthorId(id);
    }
}
