package com.service;

import com.config.ConfigAppTest;
import com.domain.Address;
import com.domain.Author;
import com.domain.Email;
import com.domain.Phone;
import com.domain.Role;
import com.domain.enums.ROLE;
import com.dto.AuthorDto;
import com.repository.AuthorRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.stream.Collectors;

/**
 * This is a class for testing the class of the
 * {@link AuthorService} and its methods.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(ConfigAppTest.class)
@WebAppConfiguration
@Sql(scripts = {"classpath:scripts/truncate_tables/truncate_table_author.sql/",
        "classpath:scripts/truncate_tables/truncate_table_address.sql/",
        "classpath:scripts/truncate_tables/truncate_table_phone.sql/",
        "classpath:scripts/truncate_tables/truncate_table_email.sql/",
        "classpath:scripts/truncate_tables/truncate_table_role.sql",
        "classpath:scripts/truncate_tables/truncate_table_user_role.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AuthorServiceTest {


    /**
     * This is a field for injection {@link AuthorService} in this class.
     */
    @Autowired
    private AuthorService authorService;


    /**
     * This is a field for injection {@link AuthorRepository} in this class.
     */
    @Autowired
    private AuthorRepository authorRepository;


    /**
     * This is a field {@link Author} we use it for testing too.
     */
    private Author author;


    /**
     * This is field {@link ModelMapper} for mapping objects.
     */
    @Autowired
    private ModelMapper modelMapper;


    /**
     * This is field {@link RoleService} for working with database.
     */
    @Autowired
    private RoleService roleService;


    /**
     * This is a method which runs before each test and which storage of
     * {@link AuthorService#save(Object)} in the database.
     */
    @Before
    public void saveAuthorBeforeEach() {

        author = new Author();

        author.setVersion(0);

        author.setName("Author");

        author.setPassword("1111111");

        author.setLastName("Kerry");

        author.setActive(true);

        Address address = new Address(0, 0,
                "lvivL", author);

        Address addressTwo = new Address(0, 0,
                "kuivK", author);

        author.addAddress(address);

        author.addAddress(addressTwo);

        Phone phone = new Phone(0, 0,
                "05034343431", author);

        Phone phoneTwo = new Phone(0, 0,
                "050334414", author);

        author.addPhone(phone);

        author.addPhone(phoneTwo);

        Email email = new Email(0, 0,
                "yur990@gmai45.com", author);

        Email emailTwo = new Email(0, 0,
                "ysja1432@gmai5l.com", author);

        author.addEmail(email);

        author.addEmail(emailTwo);

        Role role = new Role(0, ROLE.ROLE_USER);

        Role roleTwo = new Role(0, ROLE.ROLE_ADMIN);

        author.addRole(role);

        author.addRole(roleTwo);

        roleService.saveRole(role);

        roleService.saveRole(roleTwo);

        final AuthorDto authorDto = modelMapper.
                map(author, AuthorDto.class);

        authorService.save(authorDto);

        authorDto.setId(1);

        comparingAuthors(authorDto, authorService.find(1));
    }


    /**
     * This is a test method that tests for finding of
     * {@link AuthorService#find(int)} in the database.
     */
    @Test
    public void shouldFindAuthor() {
        author.setId(1);

        comparingAuthors(modelMapper.map(author, AuthorDto.class),
                authorService.find(1));
    }


    /**
     * This is a test method that tests for updating of
     * {@link AuthorService#update(Object)} in the database.
     */
    @Test
    public void shouldUpdateAuthor() {

        author.setName("UpdateAuthor");

        author.setLastName("Tommi");

        final AuthorDto authorDto = modelMapper.map(author, AuthorDto.class);

        authorDto.setName("UpdateAuthor");

        authorDto.setId(1);

        authorService.update(authorDto);

        authorDto.setVersion(1);

        comparingAuthors(authorDto, authorService.find(1));
    }


    /**
     * This is a test method that tests for deleting of
     * {@link AuthorService#delete(int)} in the database.
     */
    @Test
    public void shouldDeleteAuthor() {

        authorService.delete(1);

        Assert.assertEquals(0, authorRepository.count());
    }


    /**
     * This method compares two authorsDto for equality and tests their.
     *
     * @param target {@link AuthorDto}.
     * @param source {@link AuthorDto}.
     */
    private void comparingAuthors(final AuthorDto target,
                                  final AuthorDto source) {

        Assert.assertEquals(target.getId(), source.getId());

        Assert.assertEquals(target.getVersion(), source.getVersion());

        Assert.assertEquals(target.getName(), source.getName());

        target.getEmails()
                .stream()
                .map(Email::getEmailAuthor)
                .forEach(em -> Assert.assertTrue(
                        source.getEmails()
                                .stream()
                                .map(Email::getEmailAuthor)
                                .collect(Collectors.toList())
                                .contains(em)));


        target.getAddresses()
                .stream()
                .map(Address::getAddressAuthor)
                .forEach(adr -> Assert.assertTrue(
                        source.getAddresses()
                                .stream()
                                .map(Address::getAddressAuthor)
                                .collect(Collectors.toList())
                                .contains(adr)));


        target.getPhones()
                .stream()
                .map(Phone::getNumber)
                .forEach(ph -> Assert.assertTrue(
                        source.getPhones()
                                .stream()
                                .map(Phone::getNumber)
                                .collect(Collectors.toList())
                                .contains(ph)));
    }
}
