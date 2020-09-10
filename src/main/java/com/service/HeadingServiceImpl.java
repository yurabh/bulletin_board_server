package com.service;

import com.dao.HeadingDao;
import com.domain.Announcement;
import com.domain.Heading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@link HeadingServiceImpl} class binds realization part with user and binds {@link HeadingDao} layer
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

@Service
public class HeadingServiceImpl implements HeadingService {

    /**
     * Field {@link HeadingDao} is object instance of {@link HeadingDao} interface,it helps us
     * to perform database manipulations
     */
    private HeadingDao headingDao;

    /**
     * This is a constructor {@link HeadingServiceImpl} with parameter that injects object gain of the
     * {@link HeadingDao}
     *
     * @param headingDao {@link HeadingDao}
     */
    @Autowired
    public HeadingServiceImpl(HeadingDao headingDao) {
        this.headingDao = headingDao;
    }

    /**
     * This method takes the {@link Heading} and transmits him to the {@link HeadingDao#save(Object)}
     * for saving the heading
     *
     * @param heading {@link Heading}
     */
    @Override
    public void save(Heading heading) {
        headingDao.save(heading);
    }

    /**
     * This is method for searching for a heading id and pass id to the {@link HeadingDao#find(int)}
     * for finding the heading
     *
     * @param id int
     * @return heading {@link Heading}
     */
    @Override
    public Heading find(int id) {
        return headingDao.find(id);
    }

    /**
     * This is method accepts the {@link Heading} object with the new data and pass it
     * to the {@link HeadingDao#update(Object)} for updating the heading
     *
     * @param heading {@link Heading}
     */
    @Override
    public void update(Heading heading) {
        headingDao.update(heading);
    }

    /**
     * This is method pass heading id to the {@link HeadingDao#delete(int)} for deleting the heading
     *
     * @param id int
     */
    @Override
    public void delete(int id) {
        headingDao.delete(id);
    }

    /**
     * This is method pass heading id to the {@link HeadingDao#deleteHeading(int)} for deleting the heading
     *
     * @param id int
     */
    @Override
    public void deleteHeading(int id) {
        headingDao.deleteHeading(id);
    }

    /**
     * This is method takes a list of ids to find announcements of some headings and passes list of ids
     * to the {@link HeadingDao#getAnnouncementsFromSomeHearings(List)} for getAnnouncementsFromSomeHeadings
     *
     * @param ids {@link List<Integer>}
     * @return {@link List<Announcement>}
     */
    @Override
    public List<Announcement> getAnnouncementsFromSomeHeadings(List<Integer> ids) {
        return headingDao.getAnnouncementsFromSomeHearings(ids);
    }
}
