package com.repository;

import com.domain.Email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * {@link MailRepository} interface serves to simplify the data
 * access process for Email in database.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Repository
public interface MailRepository extends JpaRepository<Email, Integer> {

    /**
     * Method gets {@link com.domain.Author#emails} from database
     * for mailing suitable,
     * {@link com.domain.Announcement} when parameters new created
     * {@link com.domain.Announcement} match to parameters of
     * {@link com.domain.SuitableAd}.
     *
     * @param category       {@link String}.
     * @param price          {@link BigDecimal}.
     * @param revelationText {@link String}.
     * @return {@link List<String>} list of found emails or empty list.
     */
    @Query("SELECT e.emailAuthor FROM Author a "
            + " JOIN a.emails e "
            + " JOIN SuitableAd sui "
            + " ON sui.author = a "
            + " WHERE sui.category = :heading "
            + " AND :price BETWEEN sui.priceFrom AND sui.priceTo "
            + " AND sui.title = :revelationText")
    List<String> searchAuthorsEmails(
            @Param("heading") String category,
            @Param("price") BigDecimal price,
            @Param("revelationText") String revelationText);
}
