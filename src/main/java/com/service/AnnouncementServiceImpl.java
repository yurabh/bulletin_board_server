package com.service;

import com.dao.AnnouncementDao;
import com.domain.Announcement;
import com.domain.Heading;
import com.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link AnnouncementServiceImpl} class binds realization part with user and binds {@link AnnouncementDao} layer
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    /**
     * Field {@link AnnouncementDao} is object instance of {@link AnnouncementDao} interface,
     * it helps us to perform database manipulations
     */
    private AnnouncementDao announcementDao;

    /**
     * Field {@link AnnouncementRepository} is object instance of {@link AnnouncementRepository} interface,
     * which simply us to work with database
     */
    private AnnouncementRepository announcementRepository;

    /**
     * This is a constructor with parameters {@link AnnouncementServiceImpl} and that injects two objects gain of the
     * {@link AnnouncementDao} and
     * {@link AnnouncementRepository}
     *
     * @param announcementDao        {@link AnnouncementDao}
     * @param announcementRepository {@link AnnouncementRepository}
     */
    @Autowired
    public AnnouncementServiceImpl(AnnouncementDao announcementDao,
                                   AnnouncementRepository announcementRepository) {
        this.announcementDao = announcementDao;
        this.announcementRepository = announcementRepository;
    }


    /**
     * This method takes an {@link Announcement} and transmits him to the {@link AnnouncementDao#save(Object)}
     * to save announcement
     *
     * @param announcement {@link Announcement}
     */
    @Override
    public void save(Announcement announcement) {
        announcementDao.save(announcement);
    }


    /**
     * This is method for searching for an {@link Announcement} by announcement id and pass id to the
     * {@link AnnouncementDao#find(int)} to find the announcement
     *
     * @param id int
     * @return announcement {@link Announcement}
     */
    @Override
    public Announcement find(int id) {
        return announcementDao.find(id);
    }


    /**
     * This is method accepts the {@link Announcement} object with the new data and pass it to the
     * {@link AnnouncementDao#update(Object)} to update the announcement
     *
     * @param announcement {@link Announcement}
     */
    @Override
    public void update(Announcement announcement) {
        announcementDao.update(announcement);
    }


    /**
     * This is method pass {@link Announcement} id to the {@link AnnouncementDao#delete(int)}
     * to delete the announcement
     *
     * @param id int
     */
    @Override
    public void delete(int id) {
        announcementDao.delete(id);
    }


    /**
     * This is method pass {@link Announcement} id to the {@link AnnouncementDao#deleteAnnouncementById(int)}
     * to delete the announcement
     *
     * @param id int
     */
    @Override
    public void deleteAnnouncementById(int id) {
        announcementDao.deleteAnnouncementById(id);
    }


    /**
     * This is method takes the heading id to search for announcements in a particular {@link Heading#id} and passes it
     * to the {@link AnnouncementRepository#getAllByHeadingId(int)} for getting from data base
     *
     * @param id int
     * @return {@link List<Announcement>}
     */
    @Override
    public List<Announcement> getAllByHeadingId(int id) {
        return announcementRepository.getAllByHeadingId(id);
    }


    /**
     * This is method takes the date the {@link Announcement} was created and passes it to the
     * {@link AnnouncementRepository#findAllByPublicationDate(LocalDate)} to filterByDate the announcements
     *
     * @param date {@link LocalDate}
     * @return {@link List<Announcement>}
     */
    @Override
    public List<Announcement> filterAllByDate(LocalDate date) {
        return announcementRepository.findAllByPublicationDate(date);
    }


    /**
     * This is method takes the {@link Announcement} revelationText and passes
     * it to the {@link AnnouncementRepository#findAllByRevelationText(String)} to
     * filterByRevelationText the announcements
     *
     * @param revelationText {@link String}
     * @return {@link List<Announcement>}
     */
    @Override
    public List<Announcement> filterAllByRevelationText(String revelationText) {
        return announcementRepository.findAllByRevelationText(revelationText);
    }


    /**
     * This is method gets adjusted amount of announcements on a pages and passes page and size into
     * the {@link AnnouncementDao#getSomePagination(int, int)} for pagination in data base
     *
     * @param page int
     * @param size int
     * @return {@link List<Announcement>}
     */
    @Override
    public List<Announcement> showSomeAnnouncementsPagination(int page, int size) {
        return announcementDao.getSomePagination(page, size);
    }


    /**
     * This is method deletes announcements from database when they marked as inactive
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    public void deleteAnnouncementWhichIsNonActive() {
        announcementDao.deleteNoActiveAnnouncements();
    }
}
