package com.mapper;

import com.domain.Announcement;
import com.dto.AnnouncementDto;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

/**
 * This is {@link AnnouncementMapper} that helper for mapping
 * from {@link Announcement} to {@link AnnouncementDto} object.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Component
public class AnnouncementMapper extends
        AbstractConverter<Announcement, AnnouncementDto> {

    /**
     * This method convert Announcement to AnnouncementDto object by
     * modelMapper.
     *
     * @param announcement object.
     * @return {@link AnnouncementDto}.
     */
    @Override
    protected AnnouncementDto convert(final Announcement announcement) {
        final int authorId = announcement.getAuthor().getId();
        return AnnouncementDto.builder()
                .id(announcement.getId())
                .version(announcement.getVersion())
                .name(announcement.getName())
                .revelationText(announcement.getRevelationText())
                .publicationDate(announcement.getPublicationDate())
                .active(announcement.isActive())
                .serviceCost(announcement.getServiceCost())
                .heading(announcement.getHeading())
                .author(authorId)
                .build();
    }
}
