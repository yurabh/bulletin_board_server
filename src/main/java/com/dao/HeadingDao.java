package com.dao;

import com.domain.Announcement;
import com.domain.Heading;

import java.util.List;

/**
 * {@link HeadingDao} interface serves the data access process for
 * {@link Heading} in database for crud operations and other operations.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

public interface HeadingDao extends CRUDDao<Heading> {

    /**
     * This method removes the {@link Heading} and all the announcements in it
     * by the heading id.
     *
     * @param id int.
     */
    void deleteHeading(int id);

    /**
     * This method return announcements from multiple headings.
     *
     * @param ids {@link List<Integer>}.
     * @return {@link List<Announcement>}.
     */
    List<Announcement> getAnnouncementsFromSomeHearings(List<Integer> ids);
}
