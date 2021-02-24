package com.repository;

import com.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link AnnouncementRepository} interface serves to simplify the data access
 * process for {@link Announcement} in database,binds database to implementation
 * part,has extends CRUD methods from {@link JpaRepository} and queries for
 * getting need additional data from database.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Repository
public interface AnnouncementRepository
        extends JpaRepository<Announcement, Integer> {

    /**
     * This method filter announcements by revelationText from
     * the usage database using Spring Data Jpa.
     *
     * @param revelationText {@link String}.
     * @return {@link List<Announcement>}.
     */
    List<Announcement> findAllByRevelationText(String revelationText);

    /**
     * This method filter announcements by the date the announcement
     * was created from a database using Spring Data Jpa.
     *
     * @param date {@link LocalDate}.
     * @return {@link List<Announcement>}.
     */
    List<Announcement> findAllByPublicationDate(LocalDate date);

    /**
     * This method returns all announcements of a particular heading
     * from the database by the heading id using Spring Data Jpa.
     *
     * @param id int.
     * @return {@link List<Announcement>}.
     */
    List<Announcement> getAllByHeadingId(int id);
}
