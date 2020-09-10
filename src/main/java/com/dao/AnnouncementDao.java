package com.dao;

import com.domain.Announcement;
import com.domain.Heading;

import java.util.List;

/**
 * {@link AnnouncementDao} interface serves for the data access process for {@link Announcement} in database
 * has extend {@link CRUDDao} methods and other methods for getting need additional data from database
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

public interface AnnouncementDao extends CRUDDao<Announcement> {

    /**
     * This interface method removes the {@link Announcement} from the database and accepts the ID for deleting
     *
     * @param id int
     */
    void deleteAnnouncementById(int id);

    /**
     * This interface method removes all announcements from the database for a specific {@link Heading#id}
     *
     * @param id int
     */
    void deleteByHeading(int id);

    /**
     * This interface method removes all announcements from the database for a specific {@link Heading#id}
     *
     * @param id int
     */
    void deleteAllFromHeading(int id);

    /**
     * This interface method gets adjusted amount of announcements on a pages
     *
     * @param page int
     * @param size int
     * @return {@link List<Announcement>}
     */
    List<Announcement> getSomePagination(int page, int size);

    /**
     * This interface method deletes announcements from database when they marked as inactive
     */
    void deleteNoActiveAnnouncements();
}
