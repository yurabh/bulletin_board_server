package com.service.impl;

import com.dao.AnnouncementDao;
import com.domain.Announcement;
import com.dto.AnnouncementDto;
import com.repository.AnnouncementRepository;
import com.service.AnnouncementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@link AnnouncementServiceImpl} class binds realization part with user
 * and binds {@link AnnouncementDao} layer.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    /**
     * Field {@link AnnouncementDao} is object instance of
     * {@link AnnouncementDao} interface, it helps us
     * to perform database manipulations.
     */
    private final AnnouncementDao announcementDao;

    /**
     * Field {@link AnnouncementRepository} is object instance of
     * {@link AnnouncementRepository} interface,which simply us
     * to work with database.
     */
    private final AnnouncementRepository announcementRepository;

    /**
     * This field use for mapped {@link AnnouncementDto}
     * to {@link Announcement}.
     */
    private final ModelMapper modelMapper;

    /**
     * This is a constructor with parameters {@link AnnouncementServiceImpl}
     * and that injects three objects gain of the {@link AnnouncementDao}
     * and {@link AnnouncementRepository} and {@link ModelMapper}.
     *
     * @param daoAnnouncement        {@link AnnouncementDao}.
     * @param repositoryAnnouncement {@link AnnouncementRepository}.
     * @param mapperModel            {@link ModelMapper}.
     */
    @Autowired
    public AnnouncementServiceImpl(
            final AnnouncementDao daoAnnouncement,
            final AnnouncementRepository repositoryAnnouncement,
            final ModelMapper mapperModel) {
        this.announcementDao = daoAnnouncement;
        this.announcementRepository = repositoryAnnouncement;
        this.modelMapper = mapperModel;
    }


    /**
     * This method takes an {@link AnnouncementDto} and
     * transmits him to the {@link AnnouncementDao#save(Object)}
     * to save announcement.
     *
     * @param announcementDto {@link AnnouncementDto}.
     */
    @Override
    public void save(final AnnouncementDto announcementDto) {
        Announcement announcementMapped = modelMapper.
                map(announcementDto, Announcement.class);
        announcementDao.save(announcementMapped);
    }


    /**
     * This is method for searching for an {@link AnnouncementDto}
     * by announcementDto id and pass id
     * to the {@link AnnouncementDao#find(int)} to find the announcement.
     *
     * @param id int.
     * @return announcementDto {@link AnnouncementDto}.
     */
    @Override
    public AnnouncementDto find(final int id) {
        Announcement announcement = announcementDao.find(id);
        if (announcement == null) {
            return null;
        }
        return modelMapper.map(announcement, AnnouncementDto.class);
    }


    /**
     * This is method accepts the {@link AnnouncementDto} object with the new
     * data and pass it to the {@link AnnouncementDao#update(Object)}
     * to update the announcement.
     *
     * @param announcementDto {@link AnnouncementDto}.
     */
    @Override
    public void update(final AnnouncementDto announcementDto) {
        Announcement announcementMapped = modelMapper.
                map(announcementDto, Announcement.class);
        announcementDao.update(announcementMapped);
    }


    /**
     * This is method pass {@link AnnouncementDto#id} to the
     * {@link AnnouncementDao#delete(int)} to delete the announcement.
     *
     * @param id int.
     */
    @Override
    public void delete(final int id) {
        announcementDao.delete(id);
    }

    /**
     * This is method pass {@link com.dto.AnnouncementDto#id}
     * to the {@link AnnouncementDao#deleteAnnouncementById(int)}
     * to delete the announcement.
     *
     * @param id int.
     */
    @Override
    public void deleteAnnouncementById(final int id) {
        announcementDao.deleteAnnouncementById(id);
    }

    /**
     * This is method takes the heading id to search for announcements
     * in a particular {@link com.domain.Heading#id} and passes it
     * to the {@link AnnouncementRepository
     * #getAllByHeadingId(int)} for getting from data base.
     *
     * @param id int.
     * @return {@link List<AnnouncementDto>}.
     */
    @Override
    public List<AnnouncementDto> getAllByHeadingId(final int id) {
        final List<Announcement> allByHeadingId =
                announcementRepository.getAllByHeadingId(id);
        if (allByHeadingId.isEmpty()) {
            return Collections.emptyList();
        }
        List<AnnouncementDto> announcementDto = new ArrayList<>();
        for (final Announcement announcement : allByHeadingId) {
            announcementDto.add(modelMapper
                    .map(announcement, AnnouncementDto.class));
        }
        return announcementDto;
    }

    /**
     * This is method takes the date the {@link Announcement}
     * was created and passes it to the {@link AnnouncementRepository#
     * findAllByPublicationDate(LocalDate)}
     * to filterByDate the announcements.
     *
     * @param date {@link LocalDate}.
     * @return {@link List<AnnouncementDto>}.
     */
    @Override
    public List<AnnouncementDto> filterAllByDate(final LocalDate date) {
        final List<Announcement> allByPublicationDate =
                announcementRepository.findAllByPublicationDate(date);
        if (allByPublicationDate.isEmpty()) {
            return Collections.emptyList();
        }
        List<AnnouncementDto> announcementDto = new ArrayList<>();
        for (final Announcement announcement : allByPublicationDate) {
            announcementDto.add(modelMapper
                    .map(announcement, AnnouncementDto.class));
        }
        return announcementDto;
    }

    /**
     * This is method takes the {@link Announcement} revelationText
     * and passes it to the {@link AnnouncementRepository#
     * findAllByRevelationText(String)}to filterByRevelationText
     * the announcements.
     *
     * @param revelationText {@link String}.
     * @return {@link List<AnnouncementDto>}.
     */
    @Override
    public List<AnnouncementDto> filterAllByRevelationText(
            final String revelationText) {
        final List<Announcement> allByRevelationText =
                announcementRepository.findAllByRevelationText(revelationText);
        if (allByRevelationText.isEmpty()) {
            return Collections.emptyList();
        }
        List<AnnouncementDto> announcementDto = new ArrayList<>();
        for (final Announcement announcement : allByRevelationText) {
            announcementDto.add(modelMapper
                    .map(announcement, AnnouncementDto.class));
        }
        return announcementDto;
    }

    /**
     * This is method gets adjusted amount of announcements on
     * a pages and passes page and size into the
     * {@link AnnouncementDao#getSomePagination(int, int)}
     * for pagination in data base.
     *
     * @param page int.
     * @param size int.
     * @return {@link List<AnnouncementDto>}.
     */
    @Override
    public List<AnnouncementDto> showSomeAnnouncementsPagination(
            final int page, final int size) {
        final List<Announcement> somePagination =
                announcementDao.getSomePagination(page, size);
        if (somePagination.isEmpty()) {
            return Collections.emptyList();
        }
        List<AnnouncementDto> announcementDto = new ArrayList<>();
        for (final Announcement announcement : somePagination) {
            announcementDto.add(modelMapper
                    .map(announcement, AnnouncementDto.class));
        }
        return announcementDto;
    }

    /**
     * This is method deletes announcements from
     * database when they marked as inactive.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    public void deleteAnnouncementWhichIsNonActive() {
        announcementDao.deleteNoActiveAnnouncements();
    }
}
