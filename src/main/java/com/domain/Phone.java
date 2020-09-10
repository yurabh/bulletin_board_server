package com.domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Class {@link Phone} with parameters id,version,number,{@link Author} define author phone
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

@Entity
@Table(name = "phones")
public class Phone {


    /**
     * Field phone id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id")
    private int id;


    /**
     * Field version is number version of transaction in data base
     */
    @Version
    private int version;


    /**
     * Field phone number {@link String}
     */
    @Column(name = "number", nullable = false)
    @NotNull
    @Size(min = 7, max = 20, message = "A number must have min 7 numbers and max 13 characters\n")
    private String number;


    /**
     * Field author {@link Author}
     */
    @ManyToOne
    @JoinColumn(name = "author_fk_id")
    @Valid
    private Author author;


    /**
     * Default {@link Phone} constructor creates phone object without parameters
     */
    public Phone() {
    }


    /**
     * Constructor with parameters which create object {@link Phone}
     *
     * @param number String
     */
    public Phone(String number) {
        this.number = number;
    }


    /**
     * Constructor with parameters which create object {@link Phone}
     *
     * @param version int
     * @param number  {@link String}
     * @param author  {@link Author}
     */
    public Phone(int version, @NotNull @Size(min = 7, max = 20,
            message = "A number must have min 7 numbers and max 13 characters\n")
            String number, @Valid Author author) {
        this.version = version;
        this.number = number;
        this.author = author;
    }


    /**
     * <p>This is a simple description of the method getVersion in the {@link Phone} class<p/>
     * This method doesn't accept any parameters
     *
     * @return phone version
     */
    public int getVersion() {
        return version;
    }


    /**
     * <p>This is a simple description of the method setVersion in the {@link Phone} class<p/>
     *
     * @param version int
     */
    public void setVersion(int version) {
        this.version = version;
    }


    /**
     * <p>This is a simple description of the method getId in the {@link Phone} class<p/>
     * This method doesn't accept any parameters
     *
     * @return phone id
     */
    public int getId() {
        return id;
    }


    /**
     * <p>This is a simple description of the method setId in the {@link Phone} class<p/>
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * <p>This is a simple description of the method getNumber in the {@link Phone} class<p/>
     * This method doesn't accept any parameters
     *
     * @return phone number
     */
    public String getNumber() {
        return number;
    }


    /**
     * <p>This is a simple description of the method setNumber in the {@link Phone} class<p/>
     *
     * @param number String
     */
    public void setNumber(String number) {
        this.number = number;
    }


    /**
     * <p>This is a simple description of the method getAuthor in the {@link Phone} class<p/>
     * This method doesn't accept any parameters
     *
     * @return author {@link Author}
     */
    public Author getAuthor() {
        return author;
    }


    /**
     * <p>This is a simple description of the method setAuthor in the {@link Phone} class<p/>
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
        Phone phone = (Phone) o;
        return id == phone.id &&
                Objects.equals(number, phone.number) &&
                Objects.equals(author, phone.author);
    }


    /**
     * <p>This is a simple description of the hashcode method in this class<p/>
     *
     * @return hashCode of {@link Phone}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }
}
