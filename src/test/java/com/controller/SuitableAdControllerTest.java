package com.controller;

import com.config.ConfigAppTest;
import com.constant.NumberConstant;
import com.dto.AuthorDto;
import com.dto.SuitableAdDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.exception.handler.CustomExceptionHandler;
import com.service.impl.SuitableAdServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.Validator;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This is class for testing {@link SuitableAdController}
 * class and its controllers.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigAppTest.class)
@WebAppConfiguration
public class SuitableAdControllerTest {


    /**
     * This is mock field {@link SuitableAdServiceImpl}.
     */
    @Mock
    private SuitableAdServiceImpl suitableAdService;


    /**
     * This is field {@link SuitableAdController}.
     */
    @InjectMocks
    private SuitableAdController suitableAdController;


    /**
     * This is field {@link MockMvc}.
     */
    private MockMvc mockMvc;


    /**
     * This is field {@link Validator} it is used for validation data
     * when test some {@link SuitableAdController} and inject it thought
     * {@link SuitableAdController#setValidator(Validator)}.
     */
    @Autowired
    private Validator validator;


    /**
     * This is method which configure and build {@link MockMvc} object.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(suitableAdController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
        suitableAdController.setValidator(validator);
    }


    /**
     * This is test method for testing
     * {@link SuitableAdController#save(SuitableAdDto)} method.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldSaveSuitableAd() throws Exception {

        Mockito.doNothing().when(suitableAdService)
                .save(ArgumentMatchers.any(SuitableAdDto.class));

        AuthorDto authorDto = new AuthorDto();

        String json = new ObjectMapper()
                .writeValueAsString(new SuitableAdDto(0, 0,
                        "CarMercedes", "I wont to sell",
                        new BigDecimal(NumberConstant.THREE_HUNDRED),
                        new BigDecimal(NumberConstant.
                                ONE_THOUSAND_AND_FIVE_HUNDRED),
                        authorDto.getId()));

        mockMvc.perform(post("/suitableAd/suitable-ads")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }


    /**
     * This is test method for testing
     * {@link SuitableAdController#save(SuitableAdDto)}
     * with throwing exception.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldSaveSuitableAdWithThrowingException()
            throws Exception {
        Mockito.doThrow(IllegalArgumentException.class).when(suitableAdService)
                .save(ArgumentMatchers.any(SuitableAdDto.class));

        AuthorDto authorDto = new AuthorDto();

        String json = new ObjectMapper()
                .writeValueAsString(new SuitableAdDto(0, 0,
                        "T", "i",
                        new BigDecimal(NumberConstant.THREE_HUNDRED),
                        new BigDecimal(NumberConstant.
                                ONE_THOUSAND_AND_FIVE_HUNDRED),
                        authorDto.getId()));

        MvcResult result = mockMvc.perform(post("/suitableAd/suitable-ads")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        Assert.assertTrue(content.
                contains("A category must have min 2 or max 100 letters"));
    }


    /**
     * This is test method for testing {@link SuitableAdController#get(int)}.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldFindSuitableAd() throws Exception {

        AuthorDto authorDto = new AuthorDto();

        SuitableAdDto suitableAdDto = new SuitableAdDto(0, 0,
                "sendSomething", "I wont to buy",
                new BigDecimal(NumberConstant.THREE_HUNDRED),
                new BigDecimal(NumberConstant.
                        ONE_THOUSAND_AND_FIVE_HUNDRED),
                authorDto.getId());

        Mockito.doReturn(suitableAdDto).when(suitableAdService)
                .find(ArgumentMatchers.anyInt());

        mockMvc.perform(get("/suitableAd/suitable-ads/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(0))
                .andExpect(jsonPath("version").value(0))
                .andExpect(jsonPath("category").value("sendSomething"))
                .andExpect(jsonPath("title").value("I wont to buy"))
                .andExpect(jsonPath("priceFrom")
                        .value(NumberConstant.THREE_HUNDRED))
                .andExpect(jsonPath("priceTo")
                        .value(NumberConstant.ONE_THOUSAND_AND_FIVE_HUNDRED))
                .andExpect(jsonPath("authorFkId").value(0))
                .andReturn();
    }


    /**
     * This is test method for testing {@link SuitableAdController#get(int)}
     * with throwing exception.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldFindSuitableAdWithThrowingException()
            throws Exception {

        Mockito.doReturn(null).when(suitableAdService)
                .find(ArgumentMatchers.anyInt());

        mockMvc.perform(get("/suitableAd/suitable-ads/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }


    /**
     * This is test method for testing
     * {@link SuitableAdController#update(SuitableAdDto)}.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldUpdateSuitableAd() throws Exception {

        Mockito.doNothing().when(suitableAdService)
                .update(ArgumentMatchers.any(SuitableAdDto.class));

        AuthorDto author = new AuthorDto();

        String json = new ObjectMapper().writeValueAsString(
                new SuitableAdDto(0, 0,
                        "I wont", "I wont to buy",
                        new BigDecimal(NumberConstant.THREE_HUNDRED),
                        new BigDecimal(NumberConstant
                                .ONE_THOUSAND_AND_FIVE_HUNDRED),
                        author.getId()));

        mockMvc.perform(put("/suitableAd/suitable-ads")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }


    /**
     * This is test method for testing
     * {@link SuitableAdController#update(SuitableAdDto)}
     * with throwing exception.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldUpdateSuitableAdWithThrowingException()
            throws Exception {

        Mockito.doThrow(IllegalArgumentException.class).when(suitableAdService)
                .update(ArgumentMatchers.any(SuitableAdDto.class));

        AuthorDto authorDto = new AuthorDto();

        String json = new ObjectMapper().writeValueAsString(
                new SuitableAdDto(0, 0, "I", "I",
                        new BigDecimal(NumberConstant.THREE_HUNDRED),
                        new BigDecimal(NumberConstant
                                .ONE_THOUSAND_AND_FIVE_HUNDRED),
                        authorDto.getId()));

        MvcResult result = mockMvc.perform(put("/suitableAd/suitable-ads")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        Assert.assertTrue(content.
                contains("A category must have min 2 or max 100 letters"));

        Assert.assertTrue(content.
                contains("Title must have min 2 or max 100 letters"));
    }


    /**
     * This is test method for testing {@link SuitableAdController#delete(int)}.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldDeleteSuitableAd() throws Exception {

        Mockito.doNothing().when(suitableAdService).
                delete(ArgumentMatchers.anyInt());

        mockMvc.perform(delete("/suitableAd/suitable-ads/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
