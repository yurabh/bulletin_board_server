package com.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Class {@link Email} with parameters id,version,email,{@link Author} define author email
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

@Entity
@Table(name = "emails")
public class Email {

    /**
     * This is a field email id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private int id;


    /**
     * Field version is number version of transaction in data base
     */
    @Version
    private int version;


    /**
     * Field email is email of {@link Email}
     */
    @javax.validation.constraints.Email
    @NotNull
    @Length(min = 10, max = 25, message = "Email must have min 10 or max 25 letters\n")
    @Column(nullable = false)
    private String email;


    /**
     * Field author {@link Author}
     */
    @ManyToOne
    @JoinColumn(name = "author_fk_id")
    @Valid
    private Author author;


    /**
     * Email default constructor creates {@link Email} object without parameters
     */
    public Email() {
    }


    /**
     * <p>This is a simple description of the constructor {@link Email} with parameters<p></>
     *
     * @param email String
     */
    public Email(String email) {
        this.email = email;
    }


    /**
     * <p>This is a simple description of the constructor {@link Email} with parameters<p></>
     *
     * @param version int
     * @param email   {@link String}
     * @param author  {@link Author}
     */
    public Email(int version, @javax.validation.constraints.Email @NotNull @Length(min = 10, max = 25,
            message = "Email must have min 10 or max 25 letters\n") String email,
                 @Valid Author author) {
        this.version = version;
        this.email = email;
        this.author = author;
    }


    /**
     * <p>This is a simple description of the method getId in the {@link Email} class<p/>
     * This method doesn't accept any parameters
     *
     * @return int email id
     */
    public int getId() {
        return id;
    }

    /**
     * <p>This is a simple description of the method setId in the {@link Email} class<p/>
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * <p>This is a simple description of the method getVersion in the {@link Email} class<p/>
     * This method doesn't accept any parameters
     *
     * @return int email version
     */
    public int getVersion() {
        return version;
    }

    /**
     * <p>This is a simple description of the method setVersion in the {@link Email} class<p/>
     *
     * @param version int
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * <p>This is a simple description of the method getEmail in the {@link Email} class<p/>
     * This method doesn't accept any parameters
     *
     * @return String email
     */
    public String getEmail() {
        return email;
    }

    /**
     * <p>This is a simple description of the method setEmail in the {@link Email} class<p/>
     *
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * <p>This is a simple description of the method getAuthor in the {@link Email} class<p/>
     * This method doesn't accept any parameters
     *
     * @return {@link Author} author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * <p>This is a simple description of the method setAuthor in the {@link Email} class<p/>
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
        if (o == null) return false;
        Email email1 = (Email) o;
        return id == email1.id &&
                Objects.equals(email, email1.email);
    }

    /**
     * <p>This is a simple description of the hashcode method in this class<p/>
     *
     * @return hashCode of {@link Email}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
