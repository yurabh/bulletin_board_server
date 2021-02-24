package com.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Class {@link Heading} with parameters: id,version,name.
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
@JsonIdentityInfo(generator = ObjectIdGenerators.
        PropertyGenerator.class, property = "id")
@Table(name = "headings")
public class Heading {

    /**
     * Field heading id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heading_id")
    private int id;


    /**
     * Field version is number version of transaction in database.
     */
    @Version
    private int version;


    /**
     * Field name of the Heading.
     */
    @Column(name = "name")
    private String name;
}
