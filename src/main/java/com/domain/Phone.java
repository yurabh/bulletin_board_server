package com.domain;

import com.constant.ValidationConstants;
import lombok.AllArgsConstructor;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Class {@link Phone} with parameters id,version,number,
 * {@link Author} define author phone.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "phones")
public class Phone {


    /**
     * Field phone id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id")
    private int id;


    /**
     * Field version is number version of transaction in data base.
     */
    @Version
    private int version;


    /**
     * Field phone number {@link String}.
     */
    @Column(name = "number", nullable = false, unique = true)
    @NotNull
    @Size(min = ValidationConstants.PHONE_NUMBER_MIN,
            max = ValidationConstants.PHONE_NUMBER_MAX,
            message = ValidationConstants.PHONE_NUMBER)
    private String number;


    /**
     * Field author {@link Author}.
     */
    @ManyToOne
    @JoinColumn(name = "author_fk_id")
    @Valid
    private Author author;
}
