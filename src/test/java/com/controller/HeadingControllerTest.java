package com.controller;

import com.config.ConfigAppTest;
import com.domain.Heading;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.HeadingService;
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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This is class for testing {@link HeadingController} class and its controllers
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigAppTest.class)
@WebAppConfiguration
public class HeadingControllerTest {


    /**
     * This is mock field {@link HeadingService}
     */
    @Mock
    private HeadingService headingService;


    /**
     * This is field {@link HeadingController}
     */
    @InjectMocks
    private HeadingController headingController;


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

        mockMvc = MockMvcBuilders.standaloneSetup(headingController).build();
    }


    /**
     * This is test method for testing {@link HeadingController#save(Heading)} method
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldSaveHeadingWithoutThrowingException() throws Exception {
        Mockito.doNothing().when(headingService).save(ArgumentMatchers.any(Heading.class));

        String json = new ObjectMapper().writeValueAsString(new Heading("CarVolvo"));

        mockMvc.perform(post("/heading/headings")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().is(200));
    }


    /**
     * This is test method for testing {@link HeadingController#save(Heading)} method with throwing exception
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldSaveHeadingWithThrowingException() throws Exception {

        Mockito.doThrow(IllegalArgumentException.class).when(headingService)
                .save(ArgumentMatchers.any(Heading.class));

        String json = new ObjectMapper().writeValueAsString(new Heading("Car"));

        MvcResult result = mockMvc.perform(post("/heading/headings")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().is(406))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        Assert.assertTrue(content.contains("A heading name must have min 5 or max 100 letters"));
    }


    /**
     * This is test method for testing {@link HeadingController#get(int)} method
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldFindHeading() throws Exception {

        Heading car = new Heading("Car444");

        Mockito.doReturn(car).when(headingService).find(ArgumentMatchers.anyInt());

        mockMvc.perform(get("/heading/headings/{id}", 1))
                .andDo(print())
                .andExpect(status().is(302))
                .andExpect(jsonPath("id").value(0))
                .andExpect(jsonPath("version").value(0))
                .andExpect(jsonPath("name").value("Car444"))
                .andReturn();
    }


    /**
     * This is test method for testing {@link HeadingController#get(int)} method
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldFindHeadingThrowingException() throws Exception {

        Mockito.doReturn(null).when(headingService).find(ArgumentMatchers.anyInt());

        mockMvc.perform(get("/heading/headings/{id}", 1))
                .andDo(print())
                .andExpect(status().is(204))
                .andReturn();
    }


    /**
     * This is test method for testing {@link HeadingController#update(Heading)} method
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldUpdateHeading() throws Exception {

        Mockito.doNothing().when(headingService).update(ArgumentMatchers.any(Heading.class));

        String json = new ObjectMapper().writeValueAsString(new Heading("CarMercedes"));

        mockMvc.perform(put("/heading/headings")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().is(200));
    }


    /**
     * This is test method for testing {@link HeadingController#update(Heading)} with exception method
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldUpdateHeadingWithException() throws Exception {

        Mockito.doThrow(IllegalArgumentException.class).when(headingService)
                .update(ArgumentMatchers.any(Heading.class));

        String json = new ObjectMapper().writeValueAsString(new Heading("Car"));

        MvcResult result = mockMvc.perform(put("/heading/headings")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().is(406))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        Assert.assertTrue(content.contains("A heading name must have min 5 or max 100 letters"));
    }


    /**
     * This is test method for testing {@link HeadingController#delete(int)} method
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldDeleteHeading() throws Exception {

        Mockito.doNothing().when(headingService).delete(ArgumentMatchers.anyInt());

        mockMvc.perform(delete("/heading/headings/{id}", 1))
                .andDo(print())
                .andExpect(status().is(200));
    }


    /**
     * This is test method for testing {@link HeadingController#deleteHeadingById(int)} method
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldDeleteHeadingById() throws Exception {

        Mockito.doNothing().when(headingService).deleteHeading(ArgumentMatchers.anyInt());

        mockMvc.perform(delete("/heading/headings/{id}/delete-by-id", 1))
                .andDo(print())
                .andExpect(status().is(200));
    }
}
