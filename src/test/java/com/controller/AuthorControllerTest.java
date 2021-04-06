package com.controller;

import com.config.ConfigAppTest;
import com.domain.Address;
import com.domain.Author;
import com.domain.Email;
import com.domain.Phone;
import com.domain.Role;
import com.domain.enums.ROLE;
import com.dto.AuthorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.exception.handler.CustomExceptionHandler;
import com.service.AuthorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.Validator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This is class for testing {@link AuthorController} class and its controllers.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ConfigAppTest.class})
@WebAppConfiguration
public class AuthorControllerTest {


    /**
     * This is mock field {@link AuthorService}.
     */
    @Mock
    private AuthorService authorService;


    /**
     * This is field {@link AuthorController}.
     */
    @InjectMocks
    private AuthorController authorController;


    /**
     * This is field {@link MockMvc}.
     */
    private MockMvc mockMvc;


    /**
     * This is field {@link Validator} it is used for validation data
     * when test some {@link AuthorController} and inject it thought
     * {@link AuthorController#setValidator(Validator)}.
     */
    @Autowired
    private Validator validator;


    /**
     * This is method which configure and build {@link MockMvc} object.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(authorController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
        authorController.setValidator(validator);
    }


    /**
     * This is test method for testing
     * {@link AuthorController#save(com.dto.AuthorDto)} method.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldSaveAuthor() throws Exception {

        Mockito.doNothing().when(authorService)
                .save(ArgumentMatchers.any(AuthorDto.class));

        AuthorDto correctAuthorDto = createAndReturnAuthorDtoWithCorrectData();

        String json = new ObjectMapper().
                writeValueAsString(correctAuthorDto);

        mockMvc.perform(post("/author/authors")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }


    /**
     * This is test method for testing
     * {@link AuthorController#save(AuthorDto)} with throwing exception.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldSaveAuthorWithThrowingException() throws Exception {

        Mockito.doThrow(IllegalArgumentException.class).when(authorService)
                .save(ArgumentMatchers.any(AuthorDto.class));

        AuthorDto incorrectAuthorDto =
                createAndReturnAuthorDtoWithInCorrectData();

        String json = new ObjectMapper()
                .writeValueAsString(incorrectAuthorDto);

        MvcResult result = mockMvc.perform(post("/author/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        Assert.assertTrue(content.
                contains("The author name must have min 5 or max 15 letters"));
    }


    /**
     * This is test method for testing
     * {@link AuthorController#find(int)}.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldFindAuthor() throws Exception {

        AuthorDto authorDtoCorrect =
                createAndReturnAuthorDtoWithCorrectData();

        Mockito.doReturn(authorDtoCorrect).when(authorService)
                .find(ArgumentMatchers.anyInt());

        mockMvc.perform(get("/author/authors/{id}", 1))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("id").value(0))
                .andExpect(jsonPath("version").value(0))
                .andExpect(jsonPath("name").value("Tomas"))
                .andExpect(jsonPath("lastName").value("Oreonea"))
                .andExpect(jsonPath("password").value("11111"))
                .andExpect(jsonPath("active").value(true))
                .andExpect(jsonPath("phones[0].id").value(0))
                .andExpect(jsonPath("phones[0].version").value(0))
                .andExpect(jsonPath("phones[0].number").value("0633577098"))
                .andExpect(jsonPath("emails[0].id").value(0))
                .andExpect(jsonPath("emails[0].emailAuthor")
                        .value("katia@gmail.com"))
                .andExpect(jsonPath("emails[0].version").value(0))
                .andExpect(jsonPath("addresses[0].id").value(0))
                .andExpect(jsonPath("addresses[0].version").value(0))
                .andExpect(jsonPath("addresses[0].addressAuthor")
                        .value("antonuca"))
                .andExpect(jsonPath("roles[0].id").value(1))
                .andExpect(jsonPath("roles[0].roleAccount")
                        .value("ROLE_USER"))
                .andReturn();
    }


    /**
     * This is test method for testing
     * {@link AuthorController#find(int)} with throwing exception.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldFindAuthorWithThrowingException() throws Exception {

        Mockito.doReturn(null).when(authorService)
                .find(ArgumentMatchers.anyInt());

        mockMvc.perform(get("/author/authors/{id}", 1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    /**
     * This is test method for testing
     * {@link AuthorController#update(AuthorDto)}.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldUpdateAuthor() throws Exception {

        Mockito.doNothing().when(authorService)
                .update(ArgumentMatchers.any(AuthorDto.class));

        AuthorDto authorDtoCorrect =
                createAndReturnAuthorDtoWithCorrectData();

        String json = new ObjectMapper()
                .writeValueAsString(authorDtoCorrect);

        mockMvc.perform(put("/author/authors")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }


    /**
     * This is test method for testing
     * {@link AuthorController#update(AuthorDto)} with throwing exception.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldUpdateAuthorThrowingException() throws Exception {

        Mockito.doThrow(IllegalArgumentException.class).when(authorService)
                .update(ArgumentMatchers.any(AuthorDto.class));

        AuthorDto authorDtoIncorrect =
                createAndReturnAuthorDtoWithInCorrectData();

        String json = new ObjectMapper().writeValueAsString(authorDtoIncorrect);

        mockMvc.perform(put("/author/authors")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }


    /**
     * This is test method for testing {@link AuthorController#delete(int)}.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldDeleteAuthor() throws Exception {

        Mockito.doNothing().when(authorService)
                .delete(ArgumentMatchers.anyInt());

        mockMvc.perform(delete("/author/authors/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk());
    }


    /**
     * This is test method for testing
     * {@link AuthorController#deleteAnnouncementsByAuthorId(int)}.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldDeleteAnnouncementsByAuthorId() throws Exception {

        Mockito.doNothing().when(authorService)
                .deleteAnnouncementsByAuthorId(ArgumentMatchers.anyInt());

        mockMvc.perform(delete(
                "/author/authors/{id}/delete-announcements-by-author-id", 1))
                .andDo(print())
                .andExpect(status().isOk());
    }


    /**
     * This is private method which's create {@link AuthorDto}
     * with correct data and it uses in other test methods.
     *
     * @return {@link AuthorDto}.
     */
    private AuthorDto createAndReturnAuthorDtoWithCorrectData() {
        Author tomas = new Author();

        tomas.setId(0);

        tomas.setVersion(0);

        tomas.setName("Tomas");

        tomas.setActive(true);

        tomas.setLastName("Oreonea");

        tomas.setPassword("11111");

        Email email = new Email(0, 0,
                "katia@gmail.com", tomas);

        Address address = new Address(0, 0,
                "antonuca", tomas);

        Phone phone = new Phone(0, 0,
                "0633577098", tomas);

        Role role = new Role(1, ROLE.ROLE_USER);

        tomas.addEmail(email);

        tomas.addPhone(phone);

        tomas.addAddress(address);

        tomas.addRole(role);

        return new ModelMapper().map(tomas, AuthorDto.class);
    }


    /**
     * This is private method which's create {@link AuthorDto}
     * with incorrect data and it uses in other test methods.
     *
     * @return {@link AuthorDto}.
     */
    private AuthorDto createAndReturnAuthorDtoWithInCorrectData() {
        Author tomas = new Author();

        tomas.setId(0);

        tomas.setVersion(0);

        tomas.setName("T");

        tomas.setActive(true);

        tomas.setLastName("O");

        tomas.setPassword("1");

        Email email = new Email(0, 0,
                "k@.", tomas);

        Address address = new Address(1, 0,
                "a", tomas);

        Phone phone = new Phone(0, 0,
                "0", tomas);

        final Role role = new Role(1, ROLE.ROLE_MODERATOR);

        tomas.addEmail(email);

        tomas.addPhone(phone);

        tomas.addAddress(address);

        tomas.addRole(role);

        return new ModelMapper().map(tomas, AuthorDto.class);
    }
}
