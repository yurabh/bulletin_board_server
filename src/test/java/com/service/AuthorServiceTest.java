//package com.service;
//
//import com.config.ConfigAppTest;
//import com.domain.Address;
//import com.domain.Author;
//import com.domain.Email;
//import com.domain.Phone;
//import com.domain.Role;
//import com.repository.AuthorRepository;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * This is a class for testing the class of the
// * {@link AuthorService} and its methods.
// */
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringJUnitConfig(ConfigAppTest.class)
//@WebAppConfiguration
//@Sql(scripts = {"classpath:scripts/truncate_tables/truncate_table_author.sql/",
//        "classpath:scripts/truncate_tables/truncate_table_address.sql/",
//        "classpath:scripts/truncate_tables/truncate_table_phone.sql/",
//        "classpath:scripts/truncate_tables/truncate_table_email.sql/",
//        "classpath:scripts/truncate_tables/truncate_table_role.sql",
//        "classpath:scripts/truncate_tables/truncate_table_user_role.sql"},
//        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//public class AuthorServiceTest {
//
//
//    /**
//     * This is a field for injection {@link AuthorService} in this class.
//     */
//    @Autowired
//    private AuthorService authorService;
//
//
//    /**
//     * This is a field for injection {@link AuthorRepository} in this class.
//     */
//    @Autowired
//    private AuthorRepository authorRepository;
//
//
//    /**
//     * This is a field {@link Author} we use it for testing too.
//     */
//    private Author author;
//
//
//    /**
//     * This is a method which runs before each test and which storage of
//     * {@link AuthorService#save(Object)} in the database.
//     */
//    @Before
//    public void saveAuthorBeforeEach() {
//
//        author = new Author("Author");
//
//        author.setPassword("1111111");
//
//        author.setLastName("Kerry");
//
//        author.setActive(true);
//
//        Address address = new Address(0, 0,
//                "lvivL", author);
//
//        Address addressTwo = new Address(0, 0,
//                "kuivK", author);
//
//        author.addAddress(address);
//
//        author.addAddress(addressTwo);
//
//        Phone phone = new Phone(0, 0,
//                "05034343431", author);
//
//        Phone phoneTwo = new Phone(0, 0,
//                "050334414", author);
//
//        author.addPhone(phone);
//
//        author.addPhone(phoneTwo);
//
//        Email email = new Email(0, 0,
//                "yur990@gmai45.com", author);
//
//        Email emailTwo = new Email(0, 0,
//                "ysja1432@gmai5l.com", author);
//
//        author.addEmail(emailTwo);
//
//        author.addEmail(email);
//
//        Role role = new Role(0, "User");
//
//        Role roleTwo = new Role(0, "User");
//
//        author.addRole(role);
//
//        author.addRole(roleTwo);
//
////        authorService.save(author);
////
////        comparingAuthors(author, authorService.find(1));
//    }
//
//
//    /**
//     * This is a test method that tests for finding of
//     * {@link AuthorService#find(int)} in the database.
//     */
//    @Test
//    public void shouldFindAuthor() {
////        comparingAuthors(author, authorService.find(1));
//    }
//
//
//    /**
//     * This is a test method that tests for updating of
//     * {@link AuthorService#update(Object)} in the database.
//     */
//    @Test
//    public void shouldUpdateAuthor() {
//
//        author.setName("UpdateAuthor");
//
//        Set<Address> authorAddresses = author.getAddresses();
//
//        authorAddresses.forEach(address -> authorAddresses
//                .forEach(address1 -> address1.setAddressAuthor("Kolomujska")));
//
//        Set<Email> authorEmails = author.getEmails();
//
//        authorEmails.forEach(email -> authorEmails
//                .forEach(email1 -> email1.setEmailAuthor("tabmbon@1990mukol")));
//
//
//        Set<Phone> authorPhones = author.getPhones();
//
//        authorPhones.forEach(phone -> authorPhones
//                .forEach(phone1 -> phone1.setNumber("2223344")));
//
////        authorService.update(author);
////
////        author.setVersion(1);
////
////        comparingAuthors(author, authorService.find(1));
//    }
//
//
//    /**
//     * This is a test method that tests for deleting of
//     * {@link AuthorService#delete(int)} in the database.
//     */
//    @Test
//    public void shouldDeleteAuthor() {
//
//        authorService.delete(1);
//
//        Assert.assertEquals(0, authorRepository.count());
//    }
//
//
//    /**
//     * This method compares two authors for equality and tests their.
//     *
//     * @param target {@link Author}.
//     * @param source {@link Author}.
//     */
//    private void comparingAuthors(final Author target, final Author source) {
//
//        Assert.assertEquals(target.getId(), source.getId());
//
//        Assert.assertEquals(target.getVersion(), source.getVersion());
//
//        Assert.assertEquals(target.getName(), source.getName());
//
//        target.getEmails()
//                .stream()
//                .map(Email::getEmailAuthor)
//                .forEach(em -> Assert.assertTrue(
//                        source.getEmails()
//                                .stream()
//                                .map(Email::getEmailAuthor)
//                                .collect(Collectors.toList())
//                                .contains(em)));
//
//
//        target.getAddresses()
//                .stream()
//                .map(Address::getAddressAuthor)
//                .forEach(adr -> Assert.assertTrue(
//                        source.getAddresses()
//                                .stream()
//                                .map(Address::getAddressAuthor)
//                                .collect(Collectors.toList())
//                                .contains(adr)));
//
//
//        target.getPhones()
//                .stream()
//                .map(Phone::getNumber)
//                .forEach(ph -> Assert.assertTrue(
//                        source.getPhones()
//                                .stream()
//                                .map(Phone::getNumber)
//                                .collect(Collectors.toList())
//                                .contains(ph)));
//    }
//}
