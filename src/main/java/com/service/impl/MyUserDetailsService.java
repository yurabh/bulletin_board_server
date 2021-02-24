package com.service.impl;

import com.domain.Author;
import com.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link MyUserDetailsService} class which is use for making work with
 * Spring Security and get Roles of Author.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {


    /**
     * This is field {@link AuthorRepository} use it for
     * finding roles of {@link Author}.
     */
    private final AuthorRepository authorRepository;


    /**
     * This is a constructor {@link MyUserDetailsService} with parameter that
     * injects object gain of the {@link AuthorRepository}.
     *
     * @param repositoryAuthor {@link AuthorRepository}.
     */
    @Autowired
    public MyUserDetailsService(final AuthorRepository repositoryAuthor) {
        this.authorRepository = repositoryAuthor;
    }


    /**
     * This is method which is load User By Username.
     *
     * @param name {@link String}.
     * @return UserDetails.
     */
    @Override
    public UserDetails loadUserByUsername(
            final String name) {
        Author author = authorRepository.findByName(name);
        return buildUserForAuthentication(author);
    }


    /**
     * This is method which is get Author authority.
     *
     * @param author {@link Author}.
     * @return List<GrantedAuthority>.
     */
    private List<GrantedAuthority> getUserAuthority(
            final Author author) {
        return author
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(
                        role.getRoleAccount().toString()))
                .collect(Collectors.toList());
    }


    /**
     * This is method which is build User For Authentication.
     *
     * @param author {@link Author}.
     * @return UserDetails.
     */
    private UserDetails buildUserForAuthentication(final Author author) {
        List<GrantedAuthority> authorities = getUserAuthority(author);
        return new org.springframework.security.core.userdetails
                .User(author.getName(),
                author.getPassword(),
                author.isActive(), true, true, true,
                authorities);
    }
}
