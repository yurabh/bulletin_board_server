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

/**
 * Class {@link SuitableAd} defines specific {@link com.domain.Announcement}
 * with certain parameters that authors would want to be informed when
 * new {@link Announcement} with appropriate parameters are appeared
 * category,title,priceFrom ,priceTo.
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
@Builder
@Table(name = "suitableAds")
public class SuitableAd {


    /**
     * Field suitableAd id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suitable_id")
    private int id;


    /**
     * Field version is number version of transaction in data base.
     */
    @Version
    private int version;


    /**
     * Field category is name of category of announcement we want to send
     * some Authors.
     */
    @Column
    private String category;


    /**
     * Field title is title we want announcement revelationText
     * {@link com.domain.Announcement#revelationText} would contain.
     */
    @Column
    private String title;


    /**
     * Field priceFrom is announcement serviceCost
     * {@link com.domain.Announcement#serviceCost}
     * cannot be lover then priceFrom.
     */
    @Column(name = "price_from")
    private BigDecimal priceFrom;


    /**
     * Field priceTo is announcement serviceCost
     * {@link com.domain.Announcement#serviceCost}
     * cannot be higher then priceTo.
     */
    @Column(name = "price_to")
    private BigDecimal priceTo;


    /**
     * Field author {@link Author}.
     */
    @ManyToOne
    @JoinColumn(name = "author_fk_id", updatable = false)
    @Valid
    private Author author;
}
