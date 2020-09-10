package com.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Class {@link Address} with parameters id,version,address,{@link Author} define address of {@link Author}
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

@Entity
@Table(name = "addresses")
public class Address {

    /**
     * This is field address id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int id;

    /**
     * Field version is version number of transaction in data base
     */
    @Version
    private int version;

    /**
     * This is field address {@link String}
     */
    @NotNull
    @Length(min = 5, max = 26, message = "Address must have min 5 or max 26 letters\n")
    @Column(name = "address", nullable = false)
    private String address;

    /**
     * This is field author {@link Author}
     */
    @ManyToOne
    @JoinColumn(name = "author_fk_id")
    @Valid
    private Author author;


    /**
     * This is constructor for creates object {@link Address} by default without parameters
     */
    public Address() {
    }


    /**
     * This is constructor creates object {@link Address} with parameters
     *
     * @param version int
     * @param address {@link String}
     * @param author  {@link Author}
     */
    public Address(int version, @NotNull @Length(min = 5, max = 26,
            message = "Address must have min 5 or max 26 letters\n")
            String address, @Valid Author author) {
        this.version = version;
        this.address = address;
        this.author = author;
    }


    /**
     * <p>This is a simple description of the method getId in {@link Address} class<p/>
     * This method doesn't accept any parameters
     *
     * @return address id
     */
    public int getId() {
        return id;
    }


    /**
     * <p>This is a simple description of the method setId in {@link Address} class(<p/>
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * <p>This is a simple description of the method getVersion in {@link Address} class<p/>
     * This method doesn't accept any parameters
     *
     * @return address version
     */
    public int getVersion() {
        return version;
    }


    /**
     * <p>This is a simple description of the method setVersion in {@link Address} class<p/>
     *
     * @param version int
     */
    public void setVersion(int version) {
        this.version = version;
    }


    /**
     * <p>This is a simple description of the method getAddress in {@link Address} class<p/>
     * This method doesn't accept any parameters
     *
     * @return address of {@link Address}
     */
    public String getAddress() {
        return address;
    }


    /**
     * <p>This is a simple description of the method setAddress in {@link Address} class<p/>
     *
     * @param address {@link String}
     */
    public void setAddress(String address) {
        this.address = address;
    }


    /**
     * <p>This is a simple description of the method getAuthor in {@link Address} class<p/>
     * This method doesn't accept parameters
     *
     * @return author fk_key_id of Address
     */
    public Author getAuthor() {
        return author;
    }


    /**
     * <p>This is a simple description of the method setAuthor in {@link Address} class<p/>
     *
     * @param author {@link Author}
     */
    public void setAuthor(Author author) {
        this.author = author;
    }


    /**
     * <p>This is a simple description of the method equals(Object o)<p/>
     * This method checks for equality between two Objects and this method takes an Object object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return id == address1.id &&
                Objects.equals(address, address1.address) &&
                Objects.equals(author, address1.author);
    }


    /**
     * <p>This is a simple description of the hashcode method<p/>
     *
     * @return hashCode of {@link Address}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, address);
    }
}
