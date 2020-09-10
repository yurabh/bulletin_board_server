package com.controller;

import com.config.ConfigAppTest;
import com.domain.Author;
import com.domain.SuitableAd;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.SuitableAdServiceImpl;
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

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This is class for testing {@link SuitableAdController} class and its controllers
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigAppTest.class)
@WebAppConfiguration
public class SuitableAdControllerTest {


    /**
     * This is mock field {@link SuitableAdServiceImpl}
     */
    @Mock
    private SuitableAdServiceImpl suitableAdService;


    /**
     * This is field {@link SuitableAdController}
     */
    @InjectMocks
    private SuitableAdController suitableAdController;


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

        mockMvc = MockMvcBuilders.standaloneSetup(suitableAdController).build();
    }


    /**
     * This is test method for testing {@link SuitableAdController#save(SuitableAd)} method
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldSaveSuitableAd() throws Exception {

        Mockito.doNothing().when(suitableAdService).save(ArgumentMatchers.any(SuitableAd.class));

        Author author = new Author("Marija");

        String json = new ObjectMapper().writeValueAsString
                (new SuitableAd(0, "CarMercedes", "I wont to sell", new BigDecimal(300.00)
                        , new BigDecimal(1500.00), author));

        mockMvc.perform(post("/suitableAd/suitable-ads")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }


    /**
     * This is test method for testing {@link SuitableAdController#save(SuitableAd)} with throwing exception
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldSaveSuitableAdWithThrowingException() throws Exception {
        Mockito.doThrow(IllegalArgumentException.class).when(suitableAdService)
                .save(ArgumentMatchers.any(SuitableAd.class));

        Author author = new Author("Kolasa");

        String json = new ObjectMapper().writeValueAsString(
                new SuitableAd(0, "T", "i", new BigDecimal(100.00)
                        , new BigDecimal(1500), author));

        MvcResult result = mockMvc.perform(post("/suitableAd/suitable-ads")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().is(406))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        Assert.assertTrue(content.contains("A category must have min 2 or max 100 letters"));
    }


    /**
     * This is test method for testing {@link SuitableAdController#get(int)}
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldFindSuitableAd() throws Exception {

        Author author = new Author("Kolasa");

        SuitableAd suitableAd = new SuitableAd(0, "sendSomething", "I wont to buy",
                new BigDecimal(300.00), new BigDecimal(1300.00), author);

        Mockito.doReturn(suitableAd).when(suitableAdService).find(ArgumentMatchers.anyInt());

        mockMvc.perform(get("/suitableAd/suitable-ads/{id}", 1))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("id").value(0))
                .andExpect(jsonPath("version").value(0))
                .andExpect(jsonPath("category").value("sendSomething"))
                .andExpect(jsonPath("title").value("I wont to buy"))
                .andExpect(jsonPath("priceFrom").value(300.00))
                .andExpect(jsonPath("priceTo").value(1300.00))
                .andExpect(jsonPath("author").value(0))
                .andReturn();
    }


    /**
     * This is test method for testing {@link SuitableAdController#get(int)} with throwing exception
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldFindSuitableAdWithThrowingException() throws Exception {

        Mockito.doReturn(null).when(suitableAdService).find(ArgumentMatchers.anyInt());

        mockMvc.perform(get("/suitableAd/suitable-ads/{id}", 1))
                .andDo(print())
                .andExpect(status().is(204))
                .andReturn();
    }


    /**
     * This is test method for testing {@link SuitableAdController#update(SuitableAd)}
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldUpdateSuitableAd() throws Exception {

        Mockito.doNothing().when(suitableAdService).update(ArgumentMatchers.any(SuitableAd.class));

        Author author = new Author("Kolasa");

        String json = new ObjectMapper().writeValueAsString(
                new SuitableAd(0, "I wont", "I wont to buy", new BigDecimal(300.00)
                        , new BigDecimal(1500), author));

        mockMvc.perform(put("/suitableAd/suitable-ads")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }


    /**
     * This is test method for testing {@link SuitableAdController#update(SuitableAd)} with throwing exception
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldUpdateSuitableAdWithThrowingException() throws Exception {

        Mockito.doThrow(IllegalArgumentException.class).when(suitableAdService)
                .update(ArgumentMatchers.any(SuitableAd.class));

        Author author = new Author("Kolasa");

        String json = new ObjectMapper().writeValueAsString(
                new SuitableAd(0, "I", "I", new BigDecimal(110.00)
                        , new BigDecimal(1400), author));

        MvcResult result = mockMvc.perform(put("/suitableAd/suitable-ads")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().is(406))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        Assert.assertTrue(content.contains("A category must have min 2 or max 100 letters"));

        Assert.assertTrue(content.contains("Title must have min 2 or max 100 letters"));
    }


    /**
     * This is test method for testing {@link SuitableAdController#delete(int)}
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldDeleteSuitableAd() throws Exception {

        Mockito.doNothing().when(suitableAdService).delete(ArgumentMatchers.anyInt());

        mockMvc.perform(delete("/suitableAd/suitable-ads/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
