package com.service;

import com.dao.AnnouncementDao;
import com.domain.Announcement;
import com.domain.Heading;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link AnnouncementService} interface binds realization part with user and binds
 * {@link AnnouncementDao} layer
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

public interface AnnouncementService extends CRUDService<Announcement> {

    /**
     * This interface method pass announcement id to the
     * {@link AnnouncementDao#deleteAnnouncementById(int)} for deleting announcement
     *
     * @param id int
     */
    void deleteAnnouncementById(int id);

    /**
     * This interface method takes the {@link Heading#id} to search for announcements in a particular heading
     * and passes it to the Dao Layer.
     *
     * @param id int
     * @return {@link List<Announcement>}
     */
    List<Announcement> getAllByHeadingId(int id);

    /**
     * This interface method takes the date the {@link Announcement} was created and passes it to the Dao Layer
     * to filterByDate the announcements
     *
     * @param date {@link LocalDate}
     * @return {@link List<Announcement>}
     */
    List<Announcement> filterAllByDate(LocalDate date);

    /**
     * This interface method takes the {@link Announcement#revelationText} and passes it to the Dao Layer
     * to filterByRevelationText the announcements
     *
     * @param revelationText {@link String}
     * @return {@link List<Announcement>}
     */
    List<Announcement> filterAllByRevelationText(String revelationText);

    /**
     * This interface method gets adjusted amount of announcements on a pages and passes it to the Dao layer
     *
     * @param page int
     * @param size int
     * @return {@link List<Announcement>}
     */
    List<Announcement> showSomeAnnouncementsPagination(int page, int size);

    /**
     * This interface method deletes announcements from database when they marked as inactive
     */
    void deleteAnnouncementWhichIsNonActive();
}
