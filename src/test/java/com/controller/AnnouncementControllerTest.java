package com.controller;

import com.config.ConfigAppTest;
import com.constant.NumberConstant;
import com.domain.Author;
import com.domain.Heading;
import com.dto.AnnouncementDto;
import com.dto.HeadingDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.exception.handler.CustomExceptionHandler;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.service.AnnouncementService;
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
import java.math.BigDecimal;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This is class for testing {@link AnnouncementController}.
 * class and its controllers.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ConfigAppTest.class})
@WebAppConfiguration
public class AnnouncementControllerTest {


    /**
     * This is mock field {@link AnnouncementService}.
     */
    @Mock
    private AnnouncementService announcementService;

    /**
     * This is field {@link AnnouncementController}.
     */
    @InjectMocks
    private AnnouncementController announcementController;

    /**
     * This is field {@link MockMvc}.
     */
    private static MockMvc mockMvc;

    /**
     * This is field {@link Validator} it is used for validation data
     * when test some {@link AnnouncementController} and inject it thought
     * {@link AnnouncementController#setValidator(Validator)}.
     */
    @Autowired
    private Validator validator;

    /**
     * This is method which configure and build {@link MockMvc} object.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(announcementController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
        announcementController.setValidator(validator);
    }


    /**
     * This is test method for testing
     * {@link AnnouncementController#save(AnnouncementDto)} method.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldSaveAnnouncement() throws Exception {

        Mockito.doNothing()
                .when(announcementService)
                .save(ArgumentMatchers.any(AnnouncementDto.class));

        AnnouncementDto correctAnnouncementDto =
                createAndReturnCorrectAnnouncementDto();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String json = mapper.writeValueAsString(correctAnnouncementDto);


        mockMvc.perform(post("/announcement/announcements")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }


    /**
     * This is test method for testing
     * {@link AnnouncementController#save(AnnouncementDto)}
     * method with throwing exception.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldSaveAnnouncementWithThrowingException()
            throws Exception {

        Mockito.doThrow(IllegalArgumentException.class).
                when(announcementService).
                save(ArgumentMatchers.any(AnnouncementDto.class));

        AnnouncementDto incorrectAnnouncementDto =
                createAndReturnIncorrectAnnouncementDto();


        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String json = mapper.writeValueAsString(incorrectAnnouncementDto);

        MvcResult result = mockMvc.perform(post("/announcement/announcements")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        Assert.assertTrue(content.
                contains("The name must have min 4 or max 15 letters"));
    }


    /**
     * This is test method for testing
     * {@link AnnouncementController#get(int)}.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldFindAnnouncement() throws Exception {

        AnnouncementDto correctAnnouncementDto =
                createAndReturnCorrectAnnouncementDto();

        Mockito.doReturn(correctAnnouncementDto).when(announcementService)
                .find(ArgumentMatchers.anyInt());


        mockMvc.perform(get("/announcement/announcements/{id}", 1))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("id").value(0))
                .andExpect(jsonPath("version").value(0))
                .andExpect(jsonPath("name").value("I wont buy"))
                .andExpect(jsonPath("active").value(true))
                .andExpect(jsonPath("publicationDate")
                        .value(LocalDate.now().toString()))
                .andExpect(jsonPath("serviceCost")
                        .value(NumberConstant.THREE_HUNDRED))
                .andExpect(jsonPath("revelationText").value("I wont buy a car"))
                .andExpect(jsonPath("heading.id").value(0))
                .andExpect(jsonPath("author").value(0))
                .andReturn();
    }


    /**
     * This is test method for testing
     * {@link AnnouncementController#get(int)}
     * with throwing exception.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldFindAnnouncementWithThrowingException()
            throws Exception {
        Mockito.doReturn(null).when(announcementService)
                .find(ArgumentMatchers.anyInt());

        mockMvc.perform(get("/announcement/announcements/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }


    /**
     * This is test method for testing
     * {@link AnnouncementController#update(AnnouncementDto)}.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldUpdateAnnouncements()
            throws Exception {

        Mockito.doNothing().when(announcementService)
                .update(ArgumentMatchers.any(AnnouncementDto.class));

        AnnouncementDto correctAnnouncementDto =
                createAndReturnCorrectAnnouncementDto();


        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String json = mapper.writeValueAsString(correctAnnouncementDto);

        mockMvc.perform(put("/announcement/announcements")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }


    /**
     * This is test method for testing
     * {@link AnnouncementController#update(AnnouncementDto)}
     * with throwing exception.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldUpdateAnnouncementsWithThrowingException()
            throws Exception {

        Mockito.doThrow(IllegalArgumentException.class)
                .when(announcementService)
                .update(ArgumentMatchers.any(AnnouncementDto.class));

        AnnouncementDto incorrectAnnouncementDto =
                createAndReturnIncorrectAnnouncementDto();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String json = mapper.writeValueAsString(incorrectAnnouncementDto);


        mockMvc.perform(put("/announcement/announcements")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }


    /**
     * This is test method for testing
     * {@link AnnouncementController#delete(int)}.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldDeleteAnnouncement() throws Exception {
        Mockito.doNothing().when(announcementService)
                .delete(ArgumentMatchers.anyInt());

        mockMvc.perform(delete("/announcement/announcements/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk());
    }


    /**
     * This is test method for testing
     * {@link AnnouncementController#deleteAnnouncementById(int)}.
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldDeleteAnnouncementById() throws Exception {

        Mockito.doNothing().when(announcementService)
                .deleteAnnouncementById(ArgumentMatchers.anyInt());

        mockMvc.perform(delete("/announcement/announcements/{id}/delete-by-id",
                1))
                .andDo(print())
                .andExpect(status().isOk());
    }


    /**
     * This is test method for testing
     * {@link AnnouncementController#getAllAnnouncementByHeadingId(int)}.
     *
     * @throws Exception can throw
     */
    @Test
    public void shouldGetAnnouncementsByHeadingId() throws Exception {

        List<AnnouncementDto> listOfAnnouncements = createListOfAnnouncements();

        Mockito.doReturn(listOfAnnouncements).when(announcementService)
                .getAllByHeadingId(ArgumentMatchers.anyInt());

        mockMvc.perform(get("/announcement/announcements/"
                        + "{id}/get-by-heading-id",
                1))
                .andDo(print()).andExpect(status().isFound())
                .andExpect(jsonPath("$[0].id").value(0))
                .andExpect(jsonPath("$[0].version").value(0))
                .andExpect(jsonPath("$[0].name").value("Sell All phones"))
                .andExpect(jsonPath("$[0].active").value(true))
                .andExpect(jsonPath("$[0].publicationDate")
                        .value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[0].serviceCost")
                        .value(NumberConstant.THREE_HUNDRED))
                .andExpect(jsonPath("$[0].revelationText")
                        .value("Try do something"))
                .andExpect(jsonPath("$[0].author").value(0))
                .andExpect(jsonPath("$[0].heading.id").value(0))
                .andExpect(jsonPath("$[1].id").value(0))
                .andExpect(jsonPath("$[1].version").value(0))
                .andExpect(jsonPath("$[1].name").value("Sell All phones"))
                .andExpect(jsonPath("$[1].active").value(true))
                .andExpect(jsonPath("$[1].publicationDate")
                        .value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[1].serviceCost")
                        .value(NumberConstant.THREE_HUNDRED))
                .andExpect(jsonPath("$[1].revelationText")
                        .value("Try do something"))
                .andExpect(jsonPath("$[1].author").value(0))
                .andExpect(jsonPath("$[1].heading").value(0))
                .andExpect(jsonPath("$[2].id").value(0))
                .andExpect(jsonPath("$[2].version").value(0))
                .andExpect(jsonPath("$[2].name").value("Sell All phones"))
                .andExpect(jsonPath("$[2].active").value(true))
                .andExpect(jsonPath("$[2].publicationDate")
                        .value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[2].serviceCost")
                        .value(NumberConstant.THREE_HUNDRED))
                .andExpect(jsonPath("$[2].revelationText")
                        .value("Try do something"))
                .andExpect(jsonPath("$[2].author").value(0))
                .andExpect(jsonPath("$[2].heading").value(0))
                .andReturn();
    }


    /**
     * This is test method for testing
     * {@link AnnouncementController#getAllAnnouncementByHeadingId(int)}
     * with throwing exception.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldGetAllAnnouncementsByHeadingIdWithThrowingException()
            throws Exception {

        List<AnnouncementDto> announcementsDto = new ArrayList<>();

        Mockito.doReturn(announcementsDto).when(announcementService)
                .getAllByHeadingId(ArgumentMatchers.anyInt());

        mockMvc.perform(get("/announcement/announcements/"
                        + "{id}/get-by-heading-id",
                1))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }


    /**
     * This is test method for testing
     * {@link AnnouncementController#filterAllAnnounceByDate(LocalDate)}.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldFilterAllAnnouncementsByDate()
            throws Exception {

        List<AnnouncementDto> listOfAnnouncements = createListOfAnnouncements();

        Mockito.doReturn(listOfAnnouncements).when(announcementService)
                .filterAllByDate(ArgumentMatchers.any(LocalDate.class));

        mockMvc.perform(get("/announcement/announcements/"
                        + "filter-by?date=2020-09-14",
                1)).andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].id").value(0))
                .andExpect(jsonPath("$[0].version").value(0))
                .andExpect(jsonPath("$[0].name").value("Sell All phones"))
                .andExpect(jsonPath("$[0].active").value(true))
                .andExpect(jsonPath("$[0].publicationDate")
                        .value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[0].serviceCost")
                        .value(NumberConstant.THREE_HUNDRED))
                .andExpect(jsonPath("$[0].revelationText")
                        .value("Try do something"))
                .andExpect(jsonPath("$[0].author").value(0))
                .andExpect(jsonPath("$[0].heading.id").value(0))
                .andExpect(jsonPath("$[1].id").value(0))
                .andExpect(jsonPath("$[1].version").value(0))
                .andExpect(jsonPath("$[1].name").value("Sell All phones"))
                .andExpect(jsonPath("$[1].active").value(true))
                .andExpect(jsonPath("$[1].publicationDate")
                        .value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[1].serviceCost")
                        .value(NumberConstant.THREE_HUNDRED))
                .andExpect(jsonPath("$[1].revelationText")
                        .value("Try do something"))
                .andExpect(jsonPath("$[1].author").value(0))
                .andExpect(jsonPath("$[1].heading").value(0))
                .andExpect(jsonPath("$[2].id").value(0))
                .andExpect(jsonPath("$[2].version").value(0))
                .andExpect(jsonPath("$[2].name").value("Sell All phones"))
                .andExpect(jsonPath("$[2].active").value(true))
                .andExpect(jsonPath("$[2].publicationDate")
                        .value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[2].serviceCost")
                        .value(NumberConstant.THREE_HUNDRED))
                .andExpect(jsonPath("$[2].revelationText")
                        .value("Try do something"))
                .andExpect(jsonPath("$[2].author").value(0))
                .andExpect(jsonPath("$[2].heading").value(0))
                .andReturn();
    }


    /**
     * This is test method for testing
     * {@link AnnouncementController#filterAllAnnounceByDate(LocalDate)}
     * with throwing exception.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldFilterAllAnnouncementsByDateWithThrowingException()
            throws Exception {

        List<AnnouncementDto> announcementsDto = new ArrayList<>();

        Mockito.doReturn(announcementsDto).when(announcementService)
                .filterAllByDate(ArgumentMatchers.any(LocalDate.class));

        mockMvc.perform(
                get("/announcement/announcements/filter-by?date=2020-07-25",
                        1))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }


    /**
     * This is test method for testing
     * {@link AnnouncementController
     * #filterAllAnnouncementByRevelationTest(String)}.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldFilterAllAnnouncementsByRevelationText()
            throws Exception {

        List<AnnouncementDto> listOfAnnouncements = createListOfAnnouncements();

        Mockito.doReturn(listOfAnnouncements).when(announcementService)
                .filterAllByRevelationText(ArgumentMatchers.anyString());

        mockMvc.perform(
                get("/announcement/announcements/"
                        + "filter-by-revelation?text=Try do something", 1))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].id").value(0))
                .andExpect(jsonPath("$[0].version").value(0))
                .andExpect(jsonPath("$[0].name").value("Sell All phones"))
                .andExpect(jsonPath("$[0].active").value(true))
                .andExpect(jsonPath("$[0].publicationDate")
                        .value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[0].serviceCost")
                        .value(NumberConstant.THREE_HUNDRED))
                .andExpect(jsonPath("$[0].revelationText")
                        .value("Try do something"))
                .andExpect(jsonPath("$[0].author").value(0))
                .andExpect(jsonPath("$[0].heading.id").value(0))
                .andExpect(jsonPath("$[1].id").value(0))
                .andExpect(jsonPath("$[1].version").value(0))
                .andExpect(jsonPath("$[1].name").value("Sell All phones"))
                .andExpect(jsonPath("$[1].active").value(true))
                .andExpect(jsonPath("$[1].publicationDate")
                        .value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[1].serviceCost")
                        .value(NumberConstant.THREE_HUNDRED))
                .andExpect(jsonPath("$[1].revelationText")
                        .value("Try do something"))
                .andExpect(jsonPath("$[1].author").value(0))
                .andExpect(jsonPath("$[1].heading").value(0))
                .andExpect(jsonPath("$[2].id").value(0))
                .andExpect(jsonPath("$[2].version").value(0))
                .andExpect(jsonPath("$[2].name").value("Sell All phones"))
                .andExpect(jsonPath("$[2].active").value(true))
                .andExpect(jsonPath("$[2].publicationDate")
                        .value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[2].serviceCost")
                        .value(NumberConstant.THREE_HUNDRED))
                .andExpect(jsonPath("$[2].revelationText")
                        .value("Try do something"))
                .andExpect(jsonPath("$[2].heading").value(0))
                .andExpect(jsonPath("$[2].author").value(0))
                .andReturn();
    }


    /**
     * This is test method for testing
     * {@link AnnouncementController
     * #filterAllAnnouncementByRevelationTest(String)}
     * with throwing exception.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldFilterAllAnnouncementsByRevelTextWithThrowingException()
            throws Exception {

        List<AnnouncementDto> announcementsDto = new ArrayList<>();

        Mockito.doReturn(announcementsDto).when(announcementService)
                .filterAllByRevelationText(ArgumentMatchers.anyString());

        mockMvc.perform(get("/announcement/announcements/"
                + "filter-by-revelation?text=Try do something", 1))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }


    /**
     * This is test method for testing
     * {@link AnnouncementController#getSomeAnnouncementsPagination(int, int)}.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldGetSomePagination() throws Exception {

        List<AnnouncementDto> listOfAnnouncements = createListOfAnnouncements();

        Mockito.doReturn(listOfAnnouncements).when(announcementService)
                .showSomeAnnouncementsPagination(ArgumentMatchers.anyInt(),
                        ArgumentMatchers.anyInt());

        mockMvc.perform(get("/announcement/announcements/"
                        + "pagination?page=0&size=3",
                1)).andDo(print()).andExpect(status().isFound())
                .andExpect(jsonPath("$[0].id").value(0))
                .andExpect(jsonPath("$[0].version").value(0))
                .andExpect(jsonPath("$[0].name").value("Sell All phones"))
                .andExpect(jsonPath("$[0].active").value(true))
                .andExpect(jsonPath("$[0].publicationDate")
                        .value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[0].serviceCost")
                        .value(NumberConstant.THREE_HUNDRED))
                .andExpect(jsonPath("$[0].revelationText")
                        .value("Try do something"))
                .andExpect(jsonPath("$[0].author").value(0))
                .andExpect(jsonPath("$[0].heading.id").value(0))

                .andExpect(jsonPath("$[1].id").value(0))
                .andExpect(jsonPath("$[1].version").value(0))
                .andExpect(jsonPath("$[1].name").value("Sell All phones"))
                .andExpect(jsonPath("$[1].active").value(true))
                .andExpect(jsonPath("$[1].publicationDate")
                        .value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[1].serviceCost")
                        .value(NumberConstant.THREE_HUNDRED))
                .andExpect(jsonPath("$[1].revelationText")
                        .value("Try do something"))
                .andExpect(jsonPath("$[1].author").value(0))
                .andExpect(jsonPath("$[1].heading").value(0))

                .andExpect(jsonPath("$[2].id").value(0))
                .andExpect(jsonPath("$[2].version").value(0))
                .andExpect(jsonPath("$[2].name").value("Sell All phones"))
                .andExpect(jsonPath("$[2].active").value(true))
                .andExpect(jsonPath("$[2].publicationDate")
                        .value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[2].serviceCost")
                        .value(NumberConstant.THREE_HUNDRED))
                .andExpect(jsonPath("$[2].revelationText")
                        .value("Try do something"))
                .andExpect(jsonPath("$[2].author").value(0))
                .andExpect(jsonPath("$[2].heading").value(0))
                .andReturn();
    }


    /**
     * This is test method for testing
     * {@link AnnouncementController#getSomeAnnouncementsPagination(int, int)}
     * with throwing exception.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldGetSomePaginationWithThrowingException()
            throws Exception {

        List<AnnouncementDto> announcementsDto = new ArrayList<>();

        Mockito.doReturn(announcementsDto).when(announcementService)
                .showSomeAnnouncementsPagination(ArgumentMatchers.anyInt(),
                        ArgumentMatchers.anyInt());

        mockMvc.perform(get("/announcement/announcements/"
                        + "pagination?page=0&size=3",
                1))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }


    /**
     * This is private method which's create List of announcementsDto which
     * will be use in the tests method.
     *
     * @return {@link List<AnnouncementDto>}.
     */
    private List<AnnouncementDto> createListOfAnnouncements() {

        List<AnnouncementDto> announcementsDto = new ArrayList<>();

        Author author = new Author("Andriy");

        Heading heading = new Heading(0, 0, "Buy something");

        AnnouncementDto announcementDto = new AnnouncementDto(0, 0,
                "Sell All phones", true, LocalDate.now(), "Try do something",
                new BigDecimal(NumberConstant.THREE_HUNDRED),
                author.getId(), heading);

        AnnouncementDto announcementTwo = new AnnouncementDto(0, 0,
                "Sell All phones", true, LocalDate.now(), "Try do something",
                new BigDecimal(NumberConstant.THREE_HUNDRED),
                author.getId(), heading);

        AnnouncementDto announcementThree = new AnnouncementDto(0, 0,
                "Sell All phones", true, LocalDate.now(), "Try do something",
                new BigDecimal(NumberConstant.THREE_HUNDRED),
                author.getId(), heading);

        announcementsDto.add(announcementDto);

        announcementsDto.add(announcementTwo);

        announcementsDto.add(announcementThree);

        return announcementsDto;
    }


    /**
     * This is private method which's create
     * {@link AnnouncementDto} with incorrect data and which will be use
     * in the tests method.
     *
     * @return {@link AnnouncementDto}.
     */
    private AnnouncementDto createAndReturnIncorrectAnnouncementDto() {

        AnnouncementDto announcementDtoIncorrect = new AnnouncementDto();

        announcementDtoIncorrect.setVersion(0);

        announcementDtoIncorrect.setName("I");

        announcementDtoIncorrect.setActive(true);

        announcementDtoIncorrect.setAuthor(0);

        announcementDtoIncorrect.setPublicationDate(LocalDate.now());

        announcementDtoIncorrect.setRevelationText("I wont buy a car");

        announcementDtoIncorrect.
                setServiceCost(new BigDecimal(NumberConstant.THREE_HUNDRED));

        announcementDtoIncorrect.
                setHeading(new Heading(0, 0, "Computer"));

        return announcementDtoIncorrect;
    }


    /**
     * This is private method which's create
     * {@link AnnouncementDto} with correct data and which will be use
     * in the tests method.
     *
     * @return {@link AnnouncementDto}.
     */
    private AnnouncementDto createAndReturnCorrectAnnouncementDto() {

        AnnouncementDto announcementDtoCorrect = new AnnouncementDto();

        announcementDtoCorrect.setId(0);

        announcementDtoCorrect.setVersion(0);

        announcementDtoCorrect.setName("I wont buy");

        announcementDtoCorrect.setActive(true);

        announcementDtoCorrect.setAuthor(0);

        announcementDtoCorrect.setPublicationDate(LocalDate.now());

        announcementDtoCorrect.setRevelationText("I wont buy a car");

        announcementDtoCorrect
                .setServiceCost(new BigDecimal(NumberConstant.THREE_HUNDRED));

        ModelMapper modelMapper = new ModelMapper();

        final Heading carVolvo = modelMapper
                .map(new HeadingDto(0, 0, "carVolvo"), Heading.class);
        announcementDtoCorrect
                .setHeading(carVolvo);

        return announcementDtoCorrect;
    }
}
