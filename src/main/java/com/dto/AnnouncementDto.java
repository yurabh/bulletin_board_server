package com.dto;

import com.constant.ValidationConstants;
import com.domain.Heading;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * This is class {@link AnnouncementDto} is used to convert to
 * {@link com.domain.Announcement} object.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class AnnouncementDto {

    /**
     * This is field announcementDto id.
     */
    private int id;


    /**
     * This is field version is number version of transaction in data base.
     */
    private int version;


    /**
     * This is field name is name of announcementDto.
     */
    @NotNull
    @Size(min = ValidationConstants.ANNOUNCEMENT_DTO_NAME_MIN,
            max = ValidationConstants.ANNOUNCEMENT_DTO_NAME_MAX,
            message = ValidationConstants.ANNOUNCEMENT_DTO_NAME)
    private String name;


    /**
     * Field active of announcementDto is showing if announcementDto
     * active or inactive.
     */
    @NotNull
    private boolean active;


    /**
     * Field publicationDate is date when announcementDto is creates.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;


    /**
     * Field revelationText is text(describe) of announcementDto.
     */
    @NotNull
    @Size(min = ValidationConstants.ANNOUNCEMENT_DTO_REVELATION_TEXT_MIN,
            max = ValidationConstants.ANNOUNCEMENT_DTO_REVELATION_TEXT_MAX,
            message = ValidationConstants.ANNOUNCEMENT_DTO_REVELATION_TEXT)
    private String revelationText;


    /**
     * Field serviceCost is serviceCost of announcementDto.
     */
    @NotNull
    @Min(value = ValidationConstants.ANNOUNCEMENT_DTO_SERVICE_COST_MIN,
            message = ValidationConstants.ANNOUNCEMENT_DTO_SERVICE_COST)
    @Max(value = ValidationConstants.ANNOUNCEMENT_DTO_SERVICE_COST_MAX)
    private BigDecimal serviceCost;


    /**
     * This field is authorId in AnnouncementDto.
     */
    @NotNull
    private int author;


    /**
     * Field heading references to {@link Heading}.
     */
    @Valid
    private Heading heading;
}
