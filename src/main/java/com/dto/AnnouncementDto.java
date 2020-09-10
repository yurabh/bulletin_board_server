package com.dto;

import com.domain.Announcement;
import com.domain.Author;
import com.domain.Heading;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.serializator.AnnouncementDtoSerializer;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Class {@link AnnouncementDto} is used to convert from Json format to {@link Announcement} object format
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */
@JsonSerialize(using = AnnouncementDtoSerializer.class)
public class AnnouncementDto {


    /**
     * This is field announcementDto id
     */
    private int id;


    /**
     * Field name is name of announcementDto
     */
    @NotNull
    @Size(min = 4, max = 15, message = "The name must have min 4 or max 15 letters\n")
    private String name;


    /**
     * Field version is number version of transaction in data base
     */
    private int version;


    /**
     * Field active of announcementDto is showing if announcementDto active or inactive
     */

    private boolean active;


    /**
     * Field publicationDate is date when announcementDto is creates
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;


    /**
     * Field revelationText is text(describe) of announcementDto
     */
    @NotNull
    @Size(min = 5, max = 100, message = "The revelationText must have min 5 or max 100 letters\n")
    private String revelationText;


    /**
     * Field serviceCost is serviceCost of announcementDto
     */
    @NotNull
    @Min(value = 1, message = "ServiceCost must have min 1 \n")
    @Max(1000000000)
    private BigDecimal serviceCost;


    /**
     * This field is authorId in AnnouncementDto
     */
    private int author;


    /**
     * Field heading references to {@link Heading}
     */
    @Valid
    private Heading heading;


    /**
     * This is constructor for create {@link AnnouncementDto} without any parameters (Default constructor)
     */
    public AnnouncementDto() {
    }


    /**
     * This method of {@link AnnouncementDto} class.This method takes the {@link AnnouncementDto} object
     * and converts it to the {@link Announcement} object
     *
     * @param announcementDto {@link AnnouncementDto}
     * @return {@link Announcement}
     */
    public Announcement convertingToAnnouncement(AnnouncementDto announcementDto) {

        Announcement announcement = new Announcement();

        announcement.setId(announcementDto.getId());

        announcement.setName(announcementDto.getName());

        announcement.setVersion(announcementDto.getVersion());

        announcement.setActive(announcementDto.isActive());

        announcement.setPublicationDate(announcementDto.getPublicationDate());

        announcement.setRevelationText(announcementDto.getRevelationText());

        announcement.setServiceCost(announcementDto.getServiceCost());


        heading.setId(announcementDto.getHeading().getId());

        heading.setName(announcementDto.getHeading().getName());

        announcement.setHeading(heading);


        Author author = new Author();

        author.setId(announcementDto.getAuthor());

        announcement.setAuthor(author);

        return announcement;
    }


    /**
     * <p>This is a simple description of the method getId in this class<p/>
     * This method doesn't accept parameters
     *
     * @return announcementDto id
     */
    public int getId() {
        return id;
    }


    /**
     * <p>This is a simple description of the method setId in this class<p/>
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * <p>This is a simple description of the method getName in this class<p/>
     * This method doesn't accept parameters
     *
     * @return announcementDto name
     */
    public String getName() {
        return name;
    }


    /**
     * <p>This is a simple description of the method setName in this class<p/>
     *
     * @param name of announcementDto
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * <p>This is a simple description of the method getVersion in this class<p/>
     * This method doesn't accept parameters
     *
     * @return announcementDto version
     */
    public int getVersion() {
        return version;
    }


    /**
     * <p>This is a simple description of the method setVersion in this class<p/>
     *
     * @param version int
     */
    public void setVersion(int version) {
        this.version = version;
    }


    /**
     * <p>This is a simple description of the method isActive in this class<p/>
     * This method doesn't accept parameters
     *
     * @return announcementDto isActive or inActive
     */
    public boolean isActive() {
        return active;
    }


    /**
     * <p>This is a simple description of the method setActive in this class<p/>
     *
     * @param active boolean
     */
    public void setActive(boolean active) {
        this.active = active;
    }


    /**
     * <p>This is a simple description of the method getPublicationDate in this class<p/>
     * This method doesn't accept parameters
     *
     * @return announcementDto publicationDate
     */
    public LocalDate getPublicationDate() {
        return publicationDate;
    }


    /**
     * <p>This is a simple description of the method setPublicationDate in this class<p/>
     *
     * @param publicationDate LocalDate
     */
    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }


    /**
     * <p>This is a simple description of the method getRevelationText in this class<p/>
     * This method doesn't accept parameters
     *
     * @return announcementDto revelationText
     */
    public String getRevelationText() {
        return revelationText;
    }


    /**
     * <p>This is a simple description of the method setRevelationText in this class<p/>
     *
     * @param revelationText String
     */
    public void setRevelationText(String revelationText) {
        this.revelationText = revelationText;
    }


    /**
     * <p>This is a simple description of the method getServiceCost in this class<p/>
     * This method doesn't accept parameters
     *
     * @return announcementDto serviceCost
     */
    public BigDecimal getServiceCost() {
        return serviceCost;
    }


    /**
     * <p>This is a simple description of the method setServiceCost in this class<p/>
     *
     * @param serviceCost BigDecimal
     */
    public void setServiceCost(BigDecimal serviceCost) {
        this.serviceCost = serviceCost;
    }


    /**
     * <p>This is a simple description of the method getAuthorId in this class<p/>
     * This method doesn't accept parameters
     *
     * @return announcementDto authorId
     */
    public int getAuthor() {
        return author;
    }


    /**
     * <p>This is a simple description of the method setAuthorId in this class<p/>
     *
     * @param author int
     */
    public void setAuthor(int author) {
        this.author = author;
    }


    /**
     * <p>This is a simple description of the method getHeading in this class<p/>
     * This method doesn't accept parameters
     *
     * @return heading {@link Heading}
     */
    public Heading getHeading() {
        return heading;
    }


    /**
     * <p>This is a simple description of the method setHeading in this class<p/>
     *
     * @param heading Heading
     */
    public void setHeading(Heading heading) {
        this.heading = heading;
    }

}
