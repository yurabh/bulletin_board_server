package com.service;

import com.dao.HeadingDao;
import com.domain.Announcement;
import com.domain.Heading;

import java.util.List;

/**
 * {@link HeadingService} interface binds realization part with user and binds {@link HeadingDao} layer
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

public interface HeadingService extends CRUDService<Heading> {

    /**
     * This is an interface method pass heading id to the {@link HeadingDao#deleteHeading(int)}
     * for deleting heading
     *
     * @param id int
     */
    void deleteHeading(int id);

    /**
     * This is an interface method takes a list of ids to find announcements from some headings
     * and passes them to the {@link HeadingDao#getAnnouncementsFromSomeHearings(List)}
     * for getAnnouncementsFromSomeHeadings
     *
     * @param ids {@link List<Integer>}
     * @return {@link List<Announcement>}
     */
    List<Announcement> getAnnouncementsFromSomeHeadings(List<Integer> ids);
}
