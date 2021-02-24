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
 * Class {@link Email} with parameters id,version,email,
 * {@link Author} define author email.
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
@Table(name = "emails")
public class Email {


    /**
     * This is a field email id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private int id;


    /**
     * Field version is number version of transaction in data base.
     */
    @Version
    private int version;


    /**
     * Field email is email of {@link Email}.
     */
    @javax.validation.constraints.Email
    @NotNull
    @Length(min = ValidationConstants.EMAIL_MIN,
            max = ValidationConstants.EMAIL_MAX,
            message = ValidationConstants.EMAIL_MESSAGE)
    @Column(nullable = false, unique = true)
    private String emailAuthor;


    /**
     * Field author {@link Author}.
     */
    @ManyToOne
    @JoinColumn(name = "author_fk_id")
    @Valid
    private Author author;
}
