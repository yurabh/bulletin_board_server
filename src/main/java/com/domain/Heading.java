package com.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Class {@link Heading} with parameters: id,version,name
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

@Entity
@Table(name = "headings")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Heading {


    /**
     * Field heading id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heading_id")
    private int id;


    /**
     * Field version is number version of transaction in data base
     */
    @Version
    private int version;


    /**
     * Field name of the Heading
     */
    @Column(name = "name")
    @NotNull
    @Length(min = 5, max = 100, message = "A heading name must have min 5 or max 100 letters \n")
    private String name;


    /**
     * This is default constructor without parameters which creates object {@link Heading}
     */
    public Heading() {
    }


    /**
     * <p>This is a simple description of the constructor with parameters which creates object {@link Heading}<p></>
     *
     * @param name String
     */
    public Heading(String name) {
        this.name = name;
    }


    /**
     * <p>This is a simple description of the method getId in the {@link Heading} class<p/>
     * This method doesn't accept any parameters
     *
     * @return heading id
     */
    public int getId() {
        return id;
    }


    /**
     * <p>This is a simple description of the method setId in the {@link Heading} class<p/>
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * <p>This is a simple description of the method getVersion in the {@link Heading} class<p/>
     * This method doesn't accept any parameters
     *
     * @return heading version
     */
    public int getVersion() {
        return version;
    }


    /**
     * <p>This is a simple description of the method setVersion in the {@link Heading} class<p/>
     *
     * @param version int
     */
    public void setVersion(int version) {
        this.version = version;
    }


    /**
     * <p>This is a simple description of the method getName in the {@link Heading} class<p/>
     * This method doesn't accept any parameters
     *
     * @return heading name
     */
    public String getName() {
        return name;
    }


    /**
     * <p>This is a simple description of the method setName in the {@link Heading} class<p/>
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * <p>This is a simple description of the method equals(Object o)<p/>
     * This method checks for equality between two Objects and this method sets an Object object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Heading heading = (Heading) o;
        return id == heading.id &&
                Objects.equals(name, heading.name);
    }


    /**
     * <p>This is a simple description of the hashcode method in this class<p/>
     *
     * @return hashCode of {@link Heading}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
