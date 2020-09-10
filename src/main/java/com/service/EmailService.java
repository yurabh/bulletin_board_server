package com.service;

import com.domain.Announcement;
import com.repository.MailRepository;

import java.math.BigDecimal;

/**
 * {@link EmailService} interface serves for mailing suitableAd process, binds realization part with user
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

public interface EmailService {

    /**
     * The method of this interface performs a search for authors who are subscribed to send messages
     * with the appropriate characteristics such as:
     * {@link MailRepository#searchAuthorsEmails(String, BigDecimal, String)}
     *
     * @param announcement {@link Announcement}
     */
    void searchEmailsForSendingEmail(Announcement announcement);
}
