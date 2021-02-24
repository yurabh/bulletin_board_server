package com.dto;

import com.constant.ValidationConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * This is class {@link SuitableAdDto} which is required to receive data
 * on certain controllers from the client.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class SuitableAdDto {

    /**
     * Field suitableAdDto id.
     */
    private int id;

    /**
     * Field version is number version of transaction in database.
     */
    private int version;

    /**
     * Field category is name of category of announcement we want to send
     * some Authors.
     */
    @NotNull
    @Length(min = ValidationConstants.SUITABLE_AD_CATEGORY_MIN,
            max = ValidationConstants.SUITABLE_AD_CATEGORY_MAX,
            message = ValidationConstants.SUITABLE_AD_CATEGORY)
    private String category;


    /**
     * Field title is title we want announcement revelationText
     * {@link com.domain.Announcement#revelationText} would contain.
     */
    @NotNull
    @Size(min = ValidationConstants.SUITABLE_AD_TITLE_MIN,
            max = ValidationConstants.SUITABLE_AD_TITLE_MAX,
            message = ValidationConstants.SUITABLE_AD_TITLE)
    private String title;


    /**
     * Field priceFrom is announcement serviceCost
     * {@link com.domain.Announcement#serviceCost}
     * cannot be lover then priceFrom.
     */
    @NotNull
    @Min(ValidationConstants.SUITABLE_AD_PRICE_FROM_MIN)
    @Max(ValidationConstants.SUITABLE_AD_PRICE_FROM_MAX)
    private BigDecimal priceFrom;


    /**
     * Field priceTo is announcement serviceCost
     * {@link com.domain.Announcement#serviceCost}
     * cannot be higher then priceTo.
     */
    @NotNull
    @Min(ValidationConstants.SUITABLE_AD_PRICE_TO_MIN)
    @Max(ValidationConstants.SUITABLE_AD_PRICE_TO_MAX)
    private BigDecimal priceTo;

    /**
     * This is the field of AuthorFkId.
     */
    @NotNull
    private int authorFkId;
}
