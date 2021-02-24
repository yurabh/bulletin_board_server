package com.service.impl;

import com.constant.LoggerConstants;
import com.domain.Announcement;
import com.repository.MailRepository;
import com.service.EmailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * {@link EmailServiceImpl} class serves for mailing suitableAd process,
 * binds realization part with user.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Service
public class EmailServiceImpl implements EmailService {

    /**
     * This is field of class {@link Logger} returns a logger
     * for this class.
     */
    private static final Logger LOGGER = Logger.
            getLogger(EmailServiceImpl.class);


    /**
     * Field {@link MailRepository} is object instance of
     * {@link MailRepository} interface,
     * it helps us to perform database manipulations.
     */
    private final MailRepository mailRepository;


    /**
     * Field mailSender is {@link JavaMailSender} object that provides basic
     * functionality for sending simple mails.
     */
    private final JavaMailSender mailSender;


    /**
     * This is a constructor {@link EmailServiceImpl} with two parameters that
     * injects two objects gain of the {@link JavaMailSender}
     * and {@link MailRepository}.
     *
     * @param javaMailSender {@link JavaMailSender}.
     * @param repositoryMail {@link MailRepository}.
     */
    @Autowired
    public EmailServiceImpl(
            final JavaMailSender javaMailSender,
            final MailRepository repositoryMail) {
        this.mailRepository = repositoryMail;
        this.mailSender = javaMailSender;
    }


    /**
     * The method of this class performs a search for authors who are
     * subscribed to send messages with the appropriate characteristics such as:
     * {@link MailRepository
     * #searchAuthorsEmails(String, java.math.BigDecimal, String)}and also
     * passes the found emails to the method {@link EmailServiceImpl
     * #sendEmailsSomeAuthors(List, Announcement)} to send messages to the
     * appropriate authors.
     *
     * @param announcement {@link Announcement}.
     */
    public void searchEmailsForSendingEmail(final Announcement announcement) {
        List<String> resultList = mailRepository.
                searchAuthorsEmails(
                        announcement.getHeading().getName(),
                        announcement.getServiceCost(),
                        announcement.getRevelationText());
        sendEmailsSomeAuthors(resultList, announcement);
    }


    /**
     * The method send emails to all author who has inquiry for suitableAd
     * and also accepts the list of authors to whom it is necessary to send
     * the message and the announcement which it is necessary
     * to send to each author.
     *
     * @param emails       {@link List<String>}.
     * @param announcement {@link Announcement}.
     */
    private void sendEmailsSomeAuthors(final List<String> emails,
                                       final Announcement announcement) {
        MimeMessage message = mailSender.createMimeMessage();

        emails.forEach(em -> {
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(em);
                helper.setSubject("Announcement for you:");
                helper.setText("<html>\n" + "<body>\n" + "<p>"
                        + "\n" + "This is an announcement name: "
                        + announcement.getName() + "\n"
                        + "\n" + "This is an announcement revelationText: "
                        + announcement.getRevelationText() + "\n"
                        + "\n" + "This is an announcement serviceCost: "
                        + announcement.getServiceCost()
                        + "</p>\n" + "</body>\n" + "</html>", true);
                LOGGER.info(LoggerConstants.SEND_EMAIL_SOME_AUTHORS);
            } catch (MessagingException e) {
                LOGGER.trace(e);
            }
            mailSender.send(message);
        });
    }
}
