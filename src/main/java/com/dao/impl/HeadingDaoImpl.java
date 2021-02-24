package com.dao.impl;

import com.dao.AnnouncementDao;
import com.dao.HeadingDao;
import com.domain.Announcement;
import com.domain.Heading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * {@link HeadingDaoImpl} class serves for the data access process for
 * {@link Heading} in database has extends CRUD methods,and other methods
 * for getting need additional data from database.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Transactional
@Repository
public class HeadingDaoImpl implements HeadingDao {


    /**
     * This is object instance of {@link EntityManager} helps us persist data
     * into database.
     */
    @PersistenceContext
    private EntityManager entityManager;


    /**
     * {@link AnnouncementDao} is object instance of {@link AnnouncementDao}
     * interface,it helps us to perform database manipulations.
     */
    private AnnouncementDao announcementDao;


    /**
     * This is a constructor {@link HeadingDaoImpl} with parameter that injects
     * object gain of the {@link AnnouncementDao} class.
     *
     * @param daoAnnouncement {@link AnnouncementDao}.
     */
    @Autowired
    public HeadingDaoImpl(final AnnouncementDao daoAnnouncement) {
        this.announcementDao = daoAnnouncement;
    }


    /**
     * This is constructor for creates object {@link HeadingDaoImpl}
     * by default without parameters.
     */
    public HeadingDaoImpl() {
    }


    /**
     * This class method stores the {@link Heading} in a database.
     *
     * @param heading {@link Heading}.
     */
    @Override
    public void save(final Heading heading) {
        entityManager.persist(heading);
    }


    /**
     * This class method searches a {@link Heading} by Id in database
     * and takes the heading id to search.
     *
     * @param id int.
     * @return heading {@link Heading}.
     */
    @Override
    public Heading find(final int id) {
        return entityManager.find(Heading.class, id);
    }


    /**
     * This class method updates the data in the data base and accepts
     * the {@link Heading} object with the new data for updating.
     *
     * @param entity {@link Heading}.
     */
    @Override
    public void update(final Heading entity) {
        Heading merge = entityManager.merge(entity);
        entityManager.persist(merge);
    }


    /**
     * This class method removes the {@link Heading} from the database
     * and accepts the heading id for deleting.
     *
     * @param id int.
     */
    @Override
    public void delete(final int id) {

        announcementDao.deleteAllFromHeading(id);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaDelete<Heading> criteriaDelete = criteriaBuilder
                .createCriteriaDelete(Heading.class);

        Root<Heading> root = criteriaDelete.from(Heading.class);

        criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));

        entityManager.createQuery(criteriaDelete).executeUpdate();
    }


    /**
     * This class method removes the {@link Heading} from the database
     * and accepts the heading id for deleting.
     *
     * @param id int.
     */
    @Override
    public void deleteHeading(final int id) {
        announcementDao.deleteByHeading(id);

        Query query = entityManager.createQuery(
                "DELETE FROM Heading h WHERE h.id = :id");

        query.setParameter("id", id);

        query.executeUpdate();
    }


    /**
     * This method returns a list of announcements from database
     * where multiple headings whose Ids are passed in the list.
     *
     * @param ids {@link List<Integer>}.
     * @return {@link List<Announcement>}.
     */
    @Override
    public List<Announcement> getAnnouncementsFromSomeHearings(
            final List<Integer> ids) {
        TypedQuery<Announcement> query = entityManager.createQuery(
                " SELECT a FROM Announcement a "
                        + " WHERE a.heading.id IN (:ids) ", Announcement.class);

        query.setParameter("ids", ids);

        return query.getResultList();
    }
}
