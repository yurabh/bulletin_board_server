package com.domain;

import com.constant.ValidationConstants;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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

/**
 * Class {@link Address} with parameters id,version,address,
 * {@link Author} define address of {@link Author}.
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
@Table(name = "addresses")
public class Address {

    /**
     * This is field address id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int id;

    /**
     * Field version is version number of transaction in data base.
     */
    @Version
    private int version;

    /**
     * This is field addressAuthor {@link String}.
     */
    @NotNull
    @Length(min = ValidationConstants.ADDRESS_MIN,
            max = ValidationConstants.ADDRESS_MAX,
            message = ValidationConstants.LENGTH_ADDRESS)
    @Column(name = "address", nullable = false, unique = true)
    private String addressAuthor;

    /**
     * This is field author {@link Author}.
     */
    @ManyToOne
    @JoinColumn(name = "author_fk_id")
    @Valid
    private Author author;
}
