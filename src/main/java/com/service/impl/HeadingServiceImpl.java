package com.service.impl;

import com.dao.HeadingDao;
import com.domain.Announcement;
import com.domain.Heading;
import com.dto.HeadingDto;
import com.service.HeadingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@link HeadingServiceImpl} class binds realization part with user and binds
 * {@link HeadingDao} layer.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Service
public class HeadingServiceImpl implements HeadingService {

    /**
     * Field {@link HeadingDao} is object instance of {@link HeadingDao}
     * interface,it helps us to perform database manipulations.
     */
    private final HeadingDao headingDao;

    /**
     * This is {@link ModelMapper} which's help to convert objects.
     */
    private final ModelMapper modelMapper;

    /**
     * This is a constructor {@link HeadingServiceImpl} with parameters that
     * injects objects gain of the
     * {@link HeadingDao}
     * {@link ModelMapper}.
     *
     * @param daoHeading  {@link HeadingDao}.
     * @param mapperModel {@link ModelMapper}.
     */
    @Autowired
    public HeadingServiceImpl(final HeadingDao daoHeading,
                              final ModelMapper mapperModel) {
        this.headingDao = daoHeading;
        this.modelMapper = mapperModel;
    }

    /**
     * This method takes the {@link com.dto.HeadingDto} and transmits it to the
     * {@link HeadingDao#save(Object)} for saving the heading.
     *
     * @param headingDto {@link HeadingDto}.
     */
    @Override
    public void save(final HeadingDto headingDto) {
        final Heading heading = modelMapper.map(headingDto, Heading.class);
        headingDao.save(heading);
    }

    /**
     * This is method for searching for a heading id and pass id to the
     * {@link HeadingDao#find(int)} for finding the heading.
     *
     * @param id int.
     * @return headingDto {@link HeadingDto}.
     */
    @Override
    public HeadingDto find(final int id) {
        final Heading heading = headingDao.find(id);
        if (heading == null) {
            return null;
        }
        return modelMapper.map(heading, HeadingDto.class);
    }

    /**
     * This is method accepts the {@link HeadingDto} object with the new data
     * and pass it to the {@link HeadingDao#update(Object)}
     * for updating the heading.
     *
     * @param headingDto {@link HeadingDto}.
     */
    @Override
    public void update(final HeadingDto headingDto) {
        final Heading heading = modelMapper.map(headingDto, Heading.class);
        headingDao.update(heading);
    }

    /**
     * This is method pass heading id to the {@link HeadingDao#delete(int)}
     * for deleting the heading.
     *
     * @param id int.
     */
    @Override
    public void delete(final int id) {
        headingDao.delete(id);
    }

    /**
     * This is method pass heading id to the {@link HeadingDao
     * #deleteHeading(int)} for deleting the heading.
     *
     * @param id int.
     */
    @Override
    public void deleteHeading(final int id) {
        headingDao.deleteHeading(id);
    }

    /**
     * This is method takes a list of ids to find announcements of some headings
     * and passes list of ids to the {@link HeadingDao
     * #getAnnouncementsFromSomeHearings(List)}
     * for getAnnouncementsFromSomeHeadings.
     *
     * @param ids {@link List<Integer>}.
     * @return {@link List<Announcement>}.
     */
    @Override
    public List<Announcement> getAnnouncementsFromSomeHeadings(
            final List<Integer> ids) {
        return headingDao.getAnnouncementsFromSomeHearings(ids);
    }
}
