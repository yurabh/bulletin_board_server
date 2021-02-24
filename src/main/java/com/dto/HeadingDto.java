package com.dto;

import com.constant.ValidationConstants;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Class {@link HeadingDto} with parameters: id,version,name
 * it converts to {@link com.domain.Heading}.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.
        PropertyGenerator.class, property = "id")
public class HeadingDto {

    /**
     * Field headingDto id.
     */
    private int id;

    /**
     * Field version is number version of transaction in database.
     */
    private int version;

    /**
     * Field name of the HeadingDto.
     */
    @NotNull
    @Length(min = ValidationConstants.HEADING_NAME_MIN,
            max = ValidationConstants.HEADING_NAME_MAX,
            message = ValidationConstants.HEADING_NAME)
    private String name;
}
