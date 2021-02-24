package com.dto;

import com.constant.ValidationConstants;

import com.domain.Address;
import com.domain.Email;
import com.domain.Phone;
import com.domain.Role;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * This is class {@link AuthorDto} is used to convert to
 * {@link com.domain.Author} object.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthorDto {

    /**
     * This is field authorDto id.
     */
    private int id;


    /**
     * This is field version is number version of transaction in data base.
     */
    private int version;


    /**
     * This is field name is name of AuthorDto.
     */
    @Size(min = ValidationConstants.AUTHOR_NAME_MIN,
            max = ValidationConstants.AUTHOR_NAME_MAX,
            message = ValidationConstants.AUTHOR_NAME)
    @NotNull
    private String name;


    /**
     * This is field lastName is lastName of AuthorDto.
     */
    @Size(min = ValidationConstants.AUTHOR_LAST_NAME_MIN,
            max = ValidationConstants.AUTHOR_LAST_NAME_MAX,
            message = ValidationConstants.AUTHOR_LAST_NAME)
    @NotNull
    private String lastName;


    /**
     * This is field password is password of AuthorDto.
     */
    @NotNull
    @Length(min = ValidationConstants.AUTHOR_PASSWORD_MIN,
            max = ValidationConstants.AUTHOR_PASSWORD_MAX,
            message = ValidationConstants.AUTHOR_PASSWORD)
    private String password;


    /**
     * This is field active is showing of AuthorDto
     * active or not.
     */
    @NotNull
    private boolean active;


    /**
     * Field set roles related to appropriate authorDto {@link Role}.
     */
    @EqualsAndHashCode.Exclude
    @NotNull
    private List<@Valid Role> roles = new ArrayList<>();


    /**
     * Field set phone is phone related to appropriate authorDto {@link Phone}.
     */
    @EqualsAndHashCode.Exclude
    @NotNull
    private List<@Valid Phone> phones = new ArrayList<>();


    /**
     * Field set address is address related to appropriate
     * authorDto {@link Address}.
     */
    @EqualsAndHashCode.Exclude
    @NotNull
    private List<@Valid Address> addresses = new ArrayList<>();


    /**
     * Field set email is email related to appropriate authorDto {@link Email}.
     */
    @NotNull
    @EqualsAndHashCode.Exclude
    private List<@Valid Email> emails = new ArrayList<>();
}
