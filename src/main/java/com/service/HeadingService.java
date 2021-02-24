package com.service;

import com.domain.Announcement;
import com.dto.HeadingDto;

import java.util.List;

/**
 * {@link HeadingService} interface binds realization part with user and binds
 * {@link com.dao.HeadingDao} layer.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

public interface HeadingService extends CRUDService<HeadingDto> {

    /**
     * This is an interface method pass heading id to the
     * {@link com.dao.HeadingDao#deleteHeading(int)} for deleting heading.
     *
     * @param id int.
     */
    void deleteHeading(int id);

    /**
     * This is an interface method takes a list of ids to find announcements
     * from some headings and passes them to the {@link com.dao.HeadingDao
     * #getAnnouncementsFromSomeHearings(List)}
     * for getAnnouncementsFromSomeHeadings.
     *
     * @param ids {@link List<Integer>}.
     * @return {@link List<Announcement>}.
     */
    List<Announcement> getAnnouncementsFromSomeHeadings(List<Integer> ids);
}
