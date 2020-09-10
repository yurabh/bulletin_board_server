package com.domain;

import com.serializator.AnnouncementSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Class {@link Announcement} with parameters: id,version,name,publicationDate,revelationText,active,serviceCost,
 * {@link Heading},{@link Author}
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

@Entity
@Table(name = "announcements")
@JsonSerialize(using = AnnouncementSerializer.class)
public class Announcement {


    /**
     * This is field announcement id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private int id;


    /**
     * Field version is number version of transaction in data base
     */
    @Version
    private int version;


    /**
     * Field name is name of announcement
     */
    @Column(nullable = false)
    @NotNull
    @Size(min = 4, max = 15, message = "The name must have min 4 or max 15 letters\n")
    private String name;


    /**
     * Field publicationDate is date when announcement is creates
     */
    @Column(name = "publication_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;


    /**
     * Field revelationText is text(describe) of announcement
     */
    @Column(nullable = false, name = "revelation_text")
    @NotNull
    @Size(min = 5, max = 100, message = "The revelationText must have min 5 or max 100 letters\n")
    private String revelationText;


    /**
     * Field active of announcement is showing if announcement active or inactive
     */
    @Column(nullable = false)
    private boolean active;


    /**
     * Field serviceCost is price of announcement
     */
    @Column(name = "service_cost")
    @NotNull
    @Min(value = 1, message = "ServiceCost must have min 1 \n")
    @Max(1000000000)
    private BigDecimal serviceCost;


    /**
     * Field heading {@link Heading}
     */
    @ManyToOne
    @JoinColumn(name = "heading_fk_id")
    @Valid
    private Heading heading;


    /**
     * Field author {@link Author}
     */
    @ManyToOne
    @JoinColumn(name = "author_fk_id", updatable = false)
    @Valid
    private Author author;


    /**
     * This is constructor for create {@link Announcement} without any parameters (Default constructor)
     */
    public Announcement() {
    }


    /**
     * <p>This is a simple description of the constructor {@link Announcement} with parameters<p></>
     *
     * @param name            {@link String}
     * @param publicationDate {@link LocalDate}
     * @param revelationText  {@link String}
     * @param serviceCost     {@link BigDecimal}
     */
    public Announcement(String name, LocalDate publicationDate, String revelationText, BigDecimal serviceCost) {
        this.name = name;
        this.publicationDate = publicationDate;
        this.revelationText = revelationText;
        this.serviceCost = serviceCost;
    }


    /**
     * <p>This is a simple description of the constructor {@link Announcement} with parameters<p></>
     *
     * @param version         int
     * @param name            {@link String}
     * @param publicationDate {@link LocalDate}
     * @param revelationText  {@link String}
     * @param active          boolean
     * @param serviceCost     {@link BigDecimal}
     * @param heading         {@link Heading}
     * @param author          {@link Author}
     */
    public Announcement(int version, @NotNull @Size(min = 4, max = 15,
            message = "The name must have min 4 or max 15 letters\n") String name,
                        LocalDate publicationDate, @NotNull
                        @Size(min = 5, max = 100, message = "The revelationText must have min 5 or max 100 letters\n")
                                String revelationText, boolean active,
                        @Min(value = 1, message = "ServiceCost must have min 1 \n")
                        @Max(1000000000) BigDecimal serviceCost,
                        @Valid Heading heading, @Valid Author author) {
        this.version = version;
        this.name = name;
        this.publicationDate = publicationDate;
        this.revelationText = revelationText;
        this.active = active;
        this.serviceCost = serviceCost;
        this.heading = heading;
        this.author = author;
    }


    /**
     * <p>This is a simple description of the method getId in this class<p/>
     * This method doesn't accept parameters
     *
     * @return announcement id
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
     * <p>This is a simple description of the method getVersion in this class<p/>
     * This method doesn't accept parameters
     *
     * @return announcement version
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
     * <p>This is a simple description of the method getName in this class<p/>
     * This method doesn't accept parameters
     *
     * @return announcement name
     */
    public String getName() {
        return name;
    }


    /**
     * <p>This is a simple description of the method setName in this class<p/>
     *
     * @param name of announcement
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * <p>This is a simple description of the method isActive in this class<p/>
     * This method doesn't accept parameters
     *
     * @return announcement isActive or inActive
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
     * @return announcement publicationDate
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
     * @return announcement revelationText
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
     * @return announcement serviceCost
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
     * <p>This is a simple description of the method getHeading in this class<p/>
     * This method doesn't accept parameters
     *
     * @return announcement {@link Heading}
     */
    public Heading getHeading() {
        return heading;
    }


    /**
     * <p>This is a simple description of the method setHeading in this class<p/>
     *
     * @param heading {@link Heading}
     */
    public void setHeading(Heading heading) {
        this.heading = heading;
    }


    /**
     * <p>This is a simple description of the method getAuthor in this class<p/>
     * This method doesn't accept parameters
     *
     * @return announcement {@link Author}
     */
    public Author getAuthor() {
        return author;
    }


    /**
     * <p>This is a simple description of the method setAuthor in this class<p/>
     *
     * @param author {@link Author}
     */
    public void setAuthor(Author author) {
        this.author = author;
    }


    /**
     * <p>This is a simple description of the method equals(Object o)<p/>
     * This method checks for equality between two Objects and this method sets an Object object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Announcement that = (Announcement) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(publicationDate, that.publicationDate) &&
                Objects.equals(revelationText, that.revelationText) &&
                Objects.equals(serviceCost, that.serviceCost) &&
                Objects.equals(heading, that.heading) &&
                Objects.equals(author, that.author);
    }


    /**
     * <p>This is a simple description of the hashcode method in this class<p/>
     *
     * @return hashCode of {@link Announcement}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, revelationText, serviceCost);
    }
}
