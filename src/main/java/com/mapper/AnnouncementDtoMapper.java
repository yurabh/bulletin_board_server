package com.mapper;

import com.domain.Announcement;
import com.domain.Author;
import com.dto.AnnouncementDto;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

/**
 * This is {@link AnnouncementDtoMapper} that helper for mapping
 * from {@link AnnouncementDto} to {@link Announcement} object.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Component
public class AnnouncementDtoMapper extends
        AbstractConverter<AnnouncementDto, Announcement> {

    /**
     * This method convert AnnouncementDto to Announcement object by
     * modelMapper.
     *
     * @param announcementDto object.
     * @return {@link Announcement}.
     */
    @Override
    protected Announcement convert(final AnnouncementDto announcementDto) {
        final int authorId = announcementDto.getAuthor();

        final Author author = new Author();
        author.setId(authorId);
        return Announcement.builder()
                .id(announcementDto.getId())
                .version(announcementDto.getVersion())
                .name(announcementDto.getName())
                .revelationText(announcementDto.getRevelationText())
                .publicationDate(announcementDto.getPublicationDate())
                .active(announcementDto.isActive())
                .serviceCost(announcementDto.getServiceCost())
                .heading(announcementDto.getHeading())
                .author(author)
                .build();
    }
}
