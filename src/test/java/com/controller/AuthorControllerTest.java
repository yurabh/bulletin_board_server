package com.controller;

import com.config.ConfigAppTest;
import com.domain.Address;
import com.domain.Author;
import com.domain.Email;
import com.domain.Phone;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.AuthorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This is class for testing {@link AuthorController} class and its controllers
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigAppTest.class)
@WebAppConfiguration
public class AuthorControllerTest {


    /**
     * This is mock field {@link AuthorService}
     */
    @Mock
    private AuthorService authorService;


    /**
     * This is field {@link AuthorController}
     */
    @InjectMocks
    private AuthorController authorController;


    /**
     * This is field {@link MockMvc}
     */
    private MockMvc mockMvc;


    /**
     * This is method which configure and build {@link MockMvc} object
     */
    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }


    /**
     * This is test method for testing {@link AuthorController#save(Author)} method
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldSaveAuthor() throws Exception {

        Mockito.doNothing().when(authorService).save(ArgumentMatchers.any(Author.class));

        Author correctAuthor = createAndReturnAuthorWithCorrectData();

        String json = new ObjectMapper().writeValueAsString(correctAuthor);

        mockMvc.perform(post("/author/authors")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }


    /**
     * This is test method for testing {@link AuthorController#save(Author)} with throwing exception
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldSaveAuthorWithThrowingException() throws Exception {

        Mockito.doThrow(IllegalArgumentException.class).when(authorService).save(ArgumentMatchers.any(Author.class));

        Author author = createAndReturnAuthorWithInCorrectData();

        String json = new ObjectMapper().writeValueAsString(author);

        MvcResult result = mockMvc.perform(post("/author/authors")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().is(406))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        Assert.assertTrue(content.contains("The author name must have min 5 or max 15 letters"));
    }


    /**
     * This is test method for testing {@link AuthorController#find(int)}
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldFindAuthor() throws Exception {

        Author authorCorrect = createAndReturnAuthorWithCorrectData();

        Mockito.doReturn(authorCorrect).when(authorService).find(ArgumentMatchers.anyInt());

        mockMvc.perform(get("/author/authors/{id}", 1))
                .andDo(print())
                .andExpect(status().is(302))
                .andExpect(jsonPath("id").value(0))
                .andExpect(jsonPath("version").value(0))
                .andExpect(jsonPath("name").value("Tomas"))
                .andExpect(jsonPath("phones[0].id").value(0))
                .andExpect(jsonPath("phones[0].version").value(0))
                .andExpect(jsonPath("phones[0].number").value("0633577098"))
                .andExpect(jsonPath("emails[0].id").value(0))
                .andExpect(jsonPath("emails[0].email").value("katia@gmail.com"))
                .andExpect(jsonPath("emails[0].version").value(0))
                .andExpect(jsonPath("addresses[0].id").value(0))
                .andExpect(jsonPath("addresses[0].version").value(0))
                .andExpect(jsonPath("addresses[0].address").value("antonuca"))
                .andReturn();
    }


    /**
     * This is test method for testing {@link AuthorController#find(int)} with throwing exception
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldFindAuthorWithThrowingException() throws Exception {

        Mockito.doReturn(null).when(authorService).find(ArgumentMatchers.anyInt());

        mockMvc.perform(get("/author/authors/{id}", 1))
                .andDo(print())
                .andExpect(status().is(404));
    }


    /**
     * This is test method for testing {@link AuthorController#update(Author)}
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldUpdateAuthor() throws Exception {

        Mockito.doNothing().when(authorService).update(ArgumentMatchers.any(Author.class));

        Author authorCorrect = createAndReturnAuthorWithCorrectData();

        String json = new ObjectMapper().writeValueAsString(authorCorrect);

        mockMvc.perform(put("/author/authors")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().is(200));
    }


    /**
     * This is test method for testing {@link AuthorController#update(Author)} with throwing exception
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldUpdateAuthorThrowingException() throws Exception {

        Mockito.doThrow(IllegalArgumentException.class).when(authorService).update(ArgumentMatchers.any(Author.class));

        Author authorIncorrect = createAndReturnAuthorWithInCorrectData();

        String json = new ObjectMapper().writeValueAsString(authorIncorrect);

        mockMvc.perform(put("/author/authors")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().is(406));
    }


    /**
     * This is test method for testing {@link AuthorController#delete(int)}
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldDeleteAuthor() throws Exception {

        Mockito.doNothing().when(authorService).delete(ArgumentMatchers.anyInt());

        mockMvc.perform(delete("/author/authors/{id}", 1))
                .andDo(print())
                .andExpect(status().is(200));
    }


    /**
     * This is test method for testing {@link AuthorController#deleteAnnouncementsByAuthorId(int)}
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldDeleteAnnouncementsByAuthorId() throws Exception {

        Mockito.doNothing().when(authorService).deleteAnnouncementsByAuthorId(ArgumentMatchers.anyInt());

        mockMvc.perform(delete("/author/authors/{id}/delete-announcements-by-author-id", 1))
                .andDo(print())
                .andExpect(status().is(200));
    }


    /**
     * This is private method which's create {@link Author} with correct data and it uses in other test methods
     *
     * @return {@link Author}
     */
    private Author createAndReturnAuthorWithCorrectData() {
        Author tomas = new Author("Tomas");

        Email email = new Email(0, "katia@gmail.com", tomas);

        Address address = new Address(0, "antonuca", tomas);

        Phone phone = new Phone(0, "0633577098", tomas);

        tomas.addEmail(email);

        tomas.addPhone(phone);

        tomas.addAddress(address);

        return tomas;
    }


    /**
     * This is private method which's create {@link Author} with Incorrect data and it uses in other test methods
     *
     * @return {@link Author}
     */
    private Author createAndReturnAuthorWithInCorrectData() {
        Author author = new Author("T");

        Email email = new Email(0, "katiagmail.com", author);

        Address address = new Address(0, "a", author);

        Phone phone = new Phone(0, "0", author);

        author.addEmail(email);

        author.addPhone(phone);

        author.addAddress(address);

        return author;
    }
}
