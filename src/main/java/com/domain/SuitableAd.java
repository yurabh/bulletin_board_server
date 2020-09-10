package com.domain;

import com.deserializator.SuitableAdDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.serializator.SuitableAdSerializer;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Class {@link SuitableAd} defines specific {@link Announcement} with certain parameters that authors would want
 * to be informed when new {@link Announcement} with appropriate parameters are appeared category,title,priceFrom
 * ,priceTo
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

@Entity
@Table(name = "suitableAds")
@JsonSerialize(using = SuitableAdSerializer.class)
@JsonDeserialize(using = SuitableAdDeserializer.class)
public class SuitableAd {


    /**
     * Field suitableAd id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suitable_id")
    private int id;


    /**
     * Field version is number version of transaction in data base
     */
    @Version
    private int version;


    /**
     * Field category is name of category of announcement we want to see
     */
    @Column
    @NotNull
    @Length(min = 2, max = 100, message = "A category must have min 2 or max 100 letters\n")
    private String category;


    /**
     * Field title is title we want announcement revelationText {@link Announcement#revelationText} would contain
     */
    @Column
    @NotNull
    @Size(min = 2, max = 100, message = "Title must have min 2 or max 100 letters\n")
    private String title;


    /**
     * Field priceFrom is announcement serviceCost {@link Announcement#serviceCost} cannot be lover then priceFrom
     */
    @Column(name = "price_from")
    @NotNull
    @Min(1)
    @Max(1000000000)
    private BigDecimal priceFrom;


    /**
     * Field priceTo is announcement serviceCost {@link Announcement#serviceCost} cannot be higher then priceTo
     */
    @Column(name = "price_to")
    @NotNull
    @Min(1)
    @Max(1000000000)
    private BigDecimal priceTo;


    /**
     * Field author {@link Author}
     */
    @ManyToOne
    @JoinColumn(name = "author_fk_id", updatable = false)
    private Author author;


    /**
     * Default constructor creates {@link SuitableAd} object without parameters
     */
    public SuitableAd() {
    }


    /**
     * This is constructor {@link SuitableAd} with parameters
     *
     * @param version   int
     * @param category  {@link String}
     * @param title     {@link String}
     * @param priceFrom {@link BigDecimal}
     * @param priceTo   {@link BigDecimal}
     * @param author    {@link Author}
     */
    public SuitableAd(int version, @NotNull @Length(min = 2, max = 100,
            message = "A category must have min 2 or max 100 letters\n")
            String category, @NotNull @Size(min = 2, max = 100,
            message = "Title must have min 2 or max 100 letters\n")
                              String title,
                      @NotNull @Min(1) @Max(1000000000) BigDecimal priceFrom,
                      @NotNull @Min(1) @Max(1000000000) BigDecimal priceTo, @Valid Author author) {
        this.version = version;
        this.category = category;
        this.title = title;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.author = author;
    }


    /**
     * <p>This is a simple description of the method getId in the {@link SuitableAd} class<p/>
     * This method doesn't accept any parameters
     *
     * @return suitableAd id
     */
    public int getId() {
        return id;
    }


    /**
     * <p>This is a simple description of the method setId in the {@link SuitableAd} class<p/>
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * <p>This is a simple description of the method getVersion in the {@link SuitableAd} class<p/>
     * This method doesn't accept any parameters
     *
     * @return suitableAd version
     */
    public int getVersion() {
        return version;
    }


    /**
     * <p>This is a simple description of the method setVersion in the {@link SuitableAd} class<p/>
     *
     * @param version int
     */
    public void setVersion(int version) {
        this.version = version;
    }


    /**
     * <p>This is a simple description of the method getCategory in the {@link SuitableAd} class<p/>
     * This method doesn't accept any parameters
     *
     * @return suitableAd category
     */
    public String getCategory() {
        return category;
    }


    /**
     * <p>This is a simple description of the method setCategory in the {@link SuitableAd} class<p/>
     *
     * @param category String
     */
    public void setCategory(String category) {
        this.category = category;
    }


    /**
     * <p>This is a simple description of the method getTitle in the {@link SuitableAd} class<p/>
     * This method doesn't accept any parameters
     *
     * @return suitableAd title
     */
    public String getTitle() {
        return title;
    }


    /**
     * <p>This is a simple description of the method setTitle in the {@link SuitableAd} class<p/>
     *
     * @param title String
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * <p>This is a simple description of the method getPriceFrom in the {@link SuitableAd} class<p/>
     * This method doesn't accept any parameters
     *
     * @return suitableAd priceFrom
     */
    public BigDecimal getPriceFrom() {
        return priceFrom;
    }


    /**
     * <p>This is a simple description of the method setPriceFrom in the {@link SuitableAd} class<p/>
     *
     * @param priceFrom BigDecimal
     */
    public void setPriceFrom(BigDecimal priceFrom) {
        this.priceFrom = priceFrom;
    }


    /**
     * <p>This is a simple description of the method getPriceTo in the {@link SuitableAd} class<p/>
     * This method doesn't accept any parameters
     *
     * @return suitableAd priceTo
     */
    public BigDecimal getPriceTo() {
        return priceTo;
    }


    /**
     * <p>This is a simple description of the method setPriceTo in the {@link SuitableAd} class<p/>
     *
     * @param priceTo BigDecimal
     */
    public void setPriceTo(BigDecimal priceTo) {
        this.priceTo = priceTo;
    }


    /**
     * <p>This is a simple description of the method getAuthor in the {@link SuitableAd} class<p/>
     * This method doesn't accept any parameters
     *
     * @return author {@link Author}
     */
    public Author getAuthor() {
        return author;
    }


    /**
     * <p>This is a simple description of the method setAuthor in the {@link SuitableAd} class<p/>
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
        SuitableAd that = (SuitableAd) o;
        return id == that.id &&
                version == that.version &&
                Objects.equals(category, that.category) &&
                Objects.equals(title, that.title) &&
                Objects.equals(priceFrom, that.priceFrom) &&
                Objects.equals(priceTo, that.priceTo);
    }


    /**
     * <p>This is a simple description of the hashcode method in this class<p/>
     *
     * @return hashCode of suitableAd
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, version, category, title, priceFrom, priceTo);
    }
}
