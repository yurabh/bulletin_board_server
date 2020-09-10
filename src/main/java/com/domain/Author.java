package com.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Class {@link Author} with parameters id,version,name,
 * {@link Phone} ,
 * {@link Email},
 * {@link Address},
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

@Entity
@Table(name = "authors")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Author {


    /**
     * Field author id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private int id;


    /**
     * Field version is number version of transaction in data base
     */
    @Version
    private int version;


    /**
     * Field name of author
     */
    @Column(nullable = false)
    @Size(min = 5, max = 15, message = "The author name must have min 5 or max 15 letters\n")
    @NotNull
    private String name;


    /**
     * Field set phone is phone related to appropriate author {@link Phone}
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE},
            orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "author")
    @Valid
    private Set<Phone> phones = new HashSet<>();


    /**
     * Field set address is address related to appropriate author {@link Address}
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE},
            orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "author")
    @Valid
    private Set<Address> addresses = new HashSet<>();


    /**
     * Field set email is email related to appropriate author {@link Email}
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE},
            fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "author")
    @Valid
    private Set<Email> emails = new HashSet<>();


    /**
     * This is default constructor without parameters which creates object {@link Author}
     */
    public Author() {
    }


    /**
     * This is a constructor which creates {@link Author} object with parameters
     *
     * @param name String
     */
    public Author(String name) {
        this.name = name;
    }


    /**
     * <p>This is a simple description of the method getId in the {@link Author} class<p/>
     * This method doesn't accept any parameters
     *
     * @return author id
     */
    public int getId() {
        return id;
    }


    /**
     * <p>This is a simple description of the method setId in the {@link Author} class<p/>
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * <p>This is a simple description of the method getVersion in the {@link Author} class<p/>
     * This method doesn't accept any parameters
     *
     * @return author version
     */
    public int getVersion() {
        return version;
    }


    /**
     * <p>This is a simple description of the method setVersion in the {@link Author} class<p/>
     *
     * @param version int
     */
    public void setVersion(int version) {
        this.version = version;
    }


    /**
     * <p>This is a simple description of the method getName in the {@link Author} class<p/>
     * This method doesn't accept any parameters
     *
     * @return author name
     */
    public String getName() {
        return name;
    }


    /**
     * <p>This is a simple description of the method setName in the {@link Author} class<p/>
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * <p>This is a simple description of the method getPhones related with {@link Author} class<p/>
     * This method doesn't accept any parameters
     *
     * @return phones phone related to author
     */
    public Set<Phone> getPhones() {
        return phones;
    }


    /**
     * <p>This is a simple description of the method setPhones in the {@link Author} class<p/>
     *
     * @param phones phone related to author
     */
    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }


    /**
     * <p>This is a simple description of the method getAddresses related with {@link Author} class<p/>
     * This method doesn't accept any parameters
     *
     * @return addresses address related to author
     */
    public Set<Address> getAddresses() {
        return addresses;
    }


    /**
     * <p>This is a simple description of the method setAddresses in the {@link Author} class<p/>
     *
     * @param addresses address related to author
     */
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }


    /**
     * <p>This is a simple description of the method getEmails related with {@link Author} class<p/>
     * This method doesn't accept any parameters
     *
     * @return emails email related to author
     */
    public Set<Email> getEmails() {
        return emails;
    }


    /**
     * <p>This is a simple description of the method setEmails in the {@link Author} class<p/>
     *
     * @param emails email related to author
     */
    public void setEmails(Set<Email> emails) {
        this.emails = emails;
    }


    /**
     * <p>This is a simple description of the method equals(Object o)<p/>
     * This method checks for equality between two Objects and this method sets an Object object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id &&
                version == author.version &&
                Objects.equals(name, author.name) &&
                Objects.equals(phones, author.phones) &&
                Objects.equals(addresses, author.addresses) &&
                Objects.equals(emails, author.emails);
    }


    /**
     * <p>This is a simple description of the hashcode method in this class<p/>
     *
     * @return hashCode of {@link Author}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, version, name, phones, addresses, emails);
    }


    /**
     * This is a method for adding a {@link Phone} to a collection of phones from the author
     *
     * @param phone {@link Phone}
     */
    public void addPhone(Phone phone) {
        phones.add(phone);
    }


    /**
     * This is a method for adding a {@link Address} to a collection of address from the author
     *
     * @param address {@link Address}
     */
    public void addAddress(Address address) {
        addresses.add(address);
    }


    /**
     * This is a method for adding a {@link Email} to a collection of emails from the author
     *
     * @param email {@link Email}
     */
    public void addEmail(Email email) {
        emails.add(email);
    }
}
