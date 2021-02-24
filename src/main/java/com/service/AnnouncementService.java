package com.service;

import com.dto.AnnouncementDto;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link AnnouncementService} interface binds realization part
 * with user and binds {@link com.dao.AnnouncementDao} layer.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

public interface AnnouncementService extends CRUDService<AnnouncementDto> {

    /**
     * This interface method pass announcementDto id to the
     * {@link com.dao.AnnouncementDao#deleteAnnouncementById(int)}
     * for deleting announcement.
     *
     * @param id int.
     */
    void deleteAnnouncementById(int id);

    /**
     * This interface method takes the {@link com.domain.Heading#id} to search
     * for announcements in a particular heading and passes
     * it to the Dao Layer.
     *
     * @param id int.
     * @return {@link List<AnnouncementDto>}.
     */
    List<AnnouncementDto> getAllByHeadingId(int id);

    /**
     * This interface method takes the date the {@link AnnouncementDto}
     * was created and passes it to the Dao Layer to filterByDate
     * the announcements.
     *
     * @param date {@link LocalDate}.
     * @return {@link List<AnnouncementDto>}.
     */
    List<AnnouncementDto> filterAllByDate(LocalDate date);

    /**
     * This interface method takes the {@link AnnouncementDto#revelationText}
     * and passes it to the Dao Layer to filterByRevelationText
     * the announcements.
     *
     * @param revelationText {@link String}.
     * @return {@link List<AnnouncementDto>}.
     */
    List<AnnouncementDto> filterAllByRevelationText(String revelationText);

    /**
     * This interface method gets adjusted amount of announcements on a pages
     * and passes it to the Dao layer.
     *
     * @param page int.
     * @param size int.
     * @return {@link List<AnnouncementDto>}.
     */
    List<AnnouncementDto> showSomeAnnouncementsPagination(int page, int size);

    /**
     * This interface method deletes announcements from database when they
     * marked as inactive.
     */
    void deleteAnnouncementWhichIsNonActive();
}
