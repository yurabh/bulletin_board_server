package com.dao.impl;

import com.dao.AuthorDao;
import com.domain.Author;
import com.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * {@link AuthorDaoImpl} class serves for the data access
 * process for {@link Author} in database has extends CRUD methods
 * and other methods for getting need additional data from database.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Transactional
@Repository
public class AuthorDaoImpl implements AuthorDao {


    /**
     * This is object instance of {@link EntityManager}
     * helps us persist data into data base.
     */
    @PersistenceContext
    private EntityManager entityManager;


    /**
     * This is object instance of {@link AuthorRepository}
     * helps us persist data into data base with Spring Data Jpa.
     */
    private final AuthorRepository authorRepository;


    /**
     * This is a constructor {@link AuthorDaoImpl} with parameter that
     * injects object gain of the {@link AuthorRepository} class.
     *
     * @param repositoryAuthor {@link AuthorRepository}
     */
    @Autowired
    public AuthorDaoImpl(final AuthorRepository repositoryAuthor) {
        this.authorRepository = repositoryAuthor;
    }


    /**
     * This class method stores the {@link Author} in a database.
     *
     * @param author {@link Author}.
     */
    @Override
    public void save(final Author author) {
        author.getEmails().forEach(a -> a.setAuthor(author));
        author.getPhones().forEach(a -> a.setAuthor(author));
        author.getAddresses().forEach(a -> a.setAuthor(author));
        entityManager.persist(author);
    }


    /**
     * This class method searches the {@link Author} for the author id
     * in a database.
     *
     * @param id int.
     * @return author {@link Author}.
     */
    @Transactional(readOnly = true)
    @Override
    public Author find(final int id) {

        final TypedQuery<Author> query = entityManager.createQuery(
                " SELECT a FROM Author AS a "
                        + " JOIN FETCH a.emails e "
                        + " JOIN FETCH a.phones p "
                        + " JOIN FETCH a.roles r "
                        + " JOIN FETCH a.addresses ad "
                        + " WHERE a.id = :id ", Author.class);

        query.setParameter("id", id);

        return query.getResultStream().findFirst().orElse(null);
    }


    /**
     * This class method updates the {@link Author} in the database
     * and accepts the {@link Author} object with the new data for updating.
     *
     * @param entity {@link Author}.
     */
    @Override
    public void update(final Author entity) {
        Author author = entityManager.merge(entity);
        entityManager.persist(author);
    }


    /**
     * This class method removes the {@link Author} from the database
     * and accepts the id for deleting.
     *
     * @param id int.
     */
    @Override
    public void delete(final int id) {
        deleteAnnouncementsByAuthorId(id);
        deleteAllSuitableAdsByAuthorId(id);
        authorRepository.deleteById(id);
    }


    /**
     * This class method removes all announcements from the database
     * by author ID.
     *
     * @param id int.
     */
    @Override
    public void deleteAnnouncementsByAuthorId(final int id) {
        Query query = entityManager.createQuery(
                "DELETE FROM Announcement as a WHERE a.author.id = :id");

        query.setParameter("id", id);

        query.executeUpdate();
    }


    /**
     * This class method removes all suitableAds from the database
     * by author Id.
     *
     * @param id int.
     */
    @Override
    public void deleteAllSuitableAdsByAuthorId(final int id) {
        Query query = entityManager.createQuery(
                "DELETE FROM SuitableAd as s WHERE s.author.id = :id");

        query.setParameter("id", id);

        query.executeUpdate();
    }


    /**
     * This class method finds and returns all authors from the database.
     *
     * @return {@link List<Author>}.
     */
    public List<Author> getAuthors() {
        Query query = entityManager.createQuery(
                "SELECT a FROM Author a");
        return (List<Author>) query.getResultList().stream().collect(toList());
    }
}
