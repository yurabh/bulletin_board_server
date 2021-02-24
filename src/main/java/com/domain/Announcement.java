package com.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.Valid;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Class {@link Announcement} with parameters:
 * id,version,name,publicationDate,revelationText,active,serviceCost,
 * {@link Heading},{@link Author}.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "announcements")
@Builder
public class Announcement {


    /**
     * This is field announcement id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private int id;


    /**
     * Field version is number version of transaction in data base.
     */
    @Version
    private int version;


    /**
     * Field name is name of announcement.
     */
    @Column(nullable = false)
    private String name;


    /**
     * Field publicationDate is date when announcement is creates.
     */
    @Column(name = "publication_date")
    private LocalDate publicationDate;


    /**
     * Field revelationText is text(describe) of announcement.
     */
    @Column(nullable = false, name = "revelation_text")
    private String revelationText;


    /**
     * Field active of announcement is showing
     * if announcement active or inactive.
     */
    @Column(nullable = false)
    private boolean active;


    /**
     * Field serviceCost is price of announcement.
     */
    @Column(name = "service_cost")
    private BigDecimal serviceCost;


    /**
     * Field heading {@link Heading}.
     */
    @ManyToOne
    @JoinColumn(name = "heading_fk_id")
    @Valid
    private Heading heading;


    /**
     * Field author {@link Author}.
     */
    @ManyToOne
    @JoinColumn(name = "author_fk_id", updatable = false)
    @Valid
    private Author author;


    /**
     * <p>This is a simple description of the constructor
     * {@link Announcement} with parameters<p></>.
     *
     * @param announcementName {@link String}.
     * @param pubDate          {@link LocalDate}.
     * @param revelText        {@link String}.
     * @param serCost          {@link BigDecimal}.
     */
    public Announcement(
            final String announcementName,
            final LocalDate pubDate,
            final String revelText,
            final BigDecimal serCost) {
        this.name = announcementName;
        this.publicationDate = pubDate;
        this.revelationText = revelText;
        this.serviceCost = serCost;
    }
}
