package com.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.HashSet;
import java.util.Set;

/**
 * This is class {@link Author} with parameters id,version,name,
 * {@link Phone},
 * {@link Email},
 * {@link Address}.
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
@Table(name = "authors")
public class Author {


    /**
     * Field author id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private int id;


    /**
     * Field version is number version of transaction in data base.
     */
    @Version
    private int version;


    /**
     * Field name of author.
     */
    @Column(nullable = false, unique = true)
    private String name;


    /**
     * Field lastName of author.
     */
    @Column(nullable = false, unique = true)
    private String lastName;


    /**
     * Field password of author.
     */
    @Column(nullable = false)
    private String password;


    /**
     * Field active of author.
     */
    @Column(nullable = false)
    private boolean active;


    /**
     * Field set roles related to appropriate author {@link Role}.
     */
    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @EqualsAndHashCode.Exclude
    private Set<Role> roles = new HashSet<>();


    /**
     * Field set phone is phone related to appropriate author {@link Phone}.
     */
    @OneToMany(cascade = {CascadeType.ALL},
            orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "author")
    @EqualsAndHashCode.Exclude
    private Set<Phone> phones = new HashSet<>();


    /**
     * Field set address is address related to appropriate
     * author {@link Address}.
     */
    @OneToMany(cascade = {CascadeType.ALL},
            orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "author")
    @EqualsAndHashCode.Exclude
    private Set<Address> addresses = new HashSet<>();


    /**
     * Field set email is email related to appropriate author {@link Email}.
     */
    @OneToMany(cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "author")
    @EqualsAndHashCode.Exclude
    private Set<Email> emails = new HashSet<>();


    /**
     * This is a constructor which creates {@link Author}
     * object with parameters.
     *
     * @param nameAuthor String.
     */
    public Author(final String nameAuthor) {
        this.name = nameAuthor;
    }


    /**
     * This is a method for adding a {@link Phone} to a collection
     * of phones from the author.
     *
     * @param phone {@link Phone}.
     */
    public void addPhone(final Phone phone) {
        phones.add(phone);
    }


    /**
     * This is a method for adding a {@link Address} to a collection
     * of address from the author.
     *
     * @param address {@link Address}.
     */
    public void addAddress(final Address address) {
        addresses.add(address);
    }


    /**
     * This is a method for adding a {@link Email} to a collection
     * of emails from the author.
     *
     * @param email {@link Email}.
     */
    public void addEmail(final Email email) {
        emails.add(email);
    }


    /**
     * This is a method for adding a {@link Role} to a collection
     * of roles from the author.
     *
     * @param role {@link Role}.
     */
    public void addRole(final Role role) {
        roles.add(role);
    }
}
