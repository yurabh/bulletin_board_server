package com.dao;

import com.domain.Announcement;
import com.domain.Heading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * {@link HeadingDaoImpl} class serves for the data access process for {@link Heading} in database
 * has extends CRUD methods,and other methods for getting need additional data from database
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

@Transactional
@Repository
public class HeadingDaoImpl implements HeadingDao {


    /**
     * This is object instance of {@link EntityManager} helps us persist data into data base
     */
    @PersistenceContext
    private EntityManager entityManager;


    /**
     * {@link AnnouncementDao} is object instance of {@link AnnouncementDao} interface,it helps us
     * to perform database manipulations
     */
    private AnnouncementDao announcementDao;


    /**
     * This is a constructor {@link HeadingDaoImpl} with parameter that injects object gain of the
     * {@link AnnouncementDao} class
     *
     * @param announcementDao {@link AnnouncementDao}
     */
    @Autowired
    public HeadingDaoImpl(AnnouncementDao announcementDao) {
        this.announcementDao = announcementDao;
    }


    /**
     * This is constructor for creates object {@link HeadingDaoImpl} by default without parameters.
     */
    public HeadingDaoImpl() {
    }


    /**
     * This class method stores the {@link Heading} in a database
     *
     * @param heading {@link Heading}
     */
    @Override
    public void save(Heading heading) {
        entityManager.persist(heading);
    }


    /**
     * This class method searches a {@link Heading} by ID in data base and takes the heading id to search
     *
     * @param id int
     * @return heading {@link Heading}
     */
    @Override
    public Heading find(int id) {
        return entityManager.find(Heading.class, id);
    }


    /**
     * This class method updates the data in the data base and accepts the {@link Heading}
     * object with the new data for updating
     *
     * @param entity {@link Heading}
     */
    @Override
    public void update(Heading entity) {
        Heading merge = entityManager.merge(entity);
        entityManager.persist(merge);
    }


    /**
     * This class method removes the {@link Heading} from the data base and accepts the heading id for deleting
     *
     * @param id int
     */
    @Override
    public void delete(int id) {

        announcementDao.deleteAllFromHeading(id);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaDelete<Heading> criteriaDelete = criteriaBuilder.createCriteriaDelete(Heading.class);

        Root<Heading> root = criteriaDelete.from(Heading.class);

        criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));

        entityManager.createQuery(criteriaDelete).executeUpdate();
    }


    /**
     * This class method removes the {@link Heading} from the data base and accepts the heading id for deleting
     *
     * @param id int
     */
    @Override
    public void deleteHeading(int id) {
        announcementDao.deleteByHeading(id);

        Query query = entityManager.createQuery("DELETE FROM Heading h WHERE h.id = :id");

        query.setParameter("id", id);

        query.executeUpdate();
    }


    /**
     * This method returns a list of announcements from data base where multiple headings whose IDs
     * are passed in the list
     *
     * @param ids {@link List<Integer>}
     * @return {@link List<Announcement>}
     */
    @Override
    public List<Announcement> getAnnouncementsFromSomeHearings(List<Integer> ids) {
        TypedQuery<Announcement> query = entityManager.createQuery(" SELECT a FROM Announcement a " +
                        "WHERE a.heading.id IN (:ids)",
                Announcement.class);

        query.setParameter("ids", ids);

        return query.getResultList();
    }
}
