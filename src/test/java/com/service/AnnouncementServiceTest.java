package com.service;

import com.config.ConfigAppTest;
import com.constant.NumberConstant;
import com.domain.Announcement;
import com.domain.Author;
import com.domain.Heading;
import com.dto.AnnouncementDto;
import com.dto.HeadingDto;
import com.repository.AnnouncementRepository;
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

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a class for testing the class of the
 * {@link AnnouncementService} and its methods.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(ConfigAppTest.class)
@WebAppConfiguration
@Sql(scripts =
        {"classpath:scripts/truncate_tables/truncate_table_announcement.sql",
                "classpath:scripts/truncate_tables/truncate_table_author.sql",
                "classpath:scripts/truncate_tables/truncate_table_heading.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AnnouncementServiceTest {


    /**
     * This is a field for injection
     * {@link AnnouncementService} in this class.
     */
    @Autowired
    private AnnouncementService announcementService;


    /**
     * This is a field for injection {@link HeadingService} in this class.
     */
    @Autowired
    private HeadingService headingService;


    /**
     * This is a field for injection {@link AuthorRepository} in this class.
     */
    @Autowired
    private AuthorRepository authorRepository;


    /**
     * This is a field for injection {@link AuthorService} in this class.
     */
    @Autowired
    private AuthorService authorService;


    /**
     * This is a field for injection {@link AnnouncementRepository}
     * in this class.
     */
    @Autowired
    private AnnouncementRepository announcementRepository;


    /**
     * This is {@link org.modelmapper.ModelMapper} for mapping
     * object.
     */
    @Autowired
    private ModelMapper modelMapper;


    /**
     * This is a field {@link Heading} we use it for testing too.
     */
    private Heading heading;


    /**
     * This is a field {@link Author} we use it for testing too.
     */
    private Author author;


    /**
     * This is field {@link AnnouncementDto} for testing.
     */
    private AnnouncementDto announcementDto;


    /**
     * This is a test method that tests the storage of
     * {@link AnnouncementService#save(Object)}
     * in the database before each test.
     */
    @Before
    public void saveAnnouncementBeforeEach() {

        BigDecimal serviceCost = new BigDecimal(
                NumberConstant.THREE_HUNDRED_AND_ELEVEN_NUMBER)
                .setScale(2, RoundingMode.UP);

        Announcement announcement = new Announcement("Announcement",
                LocalDate.now(), "I wonna go", serviceCost);

        announcement.setActive(true);

        announcement.setVersion(0);

        author = new Author("Announcement");

        author.setActive(true);

        author.setVersion(0);

        author.setLastName("Bahlay");

        author.setPassword("11111");

        authorRepository.save(author);

        announcement.setAuthor(author);

        heading = new Heading();

        heading.setVersion(0);

        heading.setName("Announcement");

        final HeadingDto headingDto = modelMapper
                .map(heading, HeadingDto.class);

        try {
            headingService.save(headingDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        announcement.setHeading(heading);

        announcementDto = modelMapper.map(announcement, AnnouncementDto.class);

        announcementDto.setAuthor(1);

        heading.setId(1);

        announcementDto.setHeading(heading);

        try {
            announcementService.save(announcementDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        announcementDto.setId(1);

        compareTwoAnnouncementDto(announcementDto, announcementService.find(1));
    }


    /**
     * This is a test method that tests for finding of
     * {@link AnnouncementService#find(int)} in the database.
     */
    @Test
    public void shouldFindAnnouncement() {
        compareTwoAnnouncementDto(announcementDto, announcementService.find(1));
    }


    /**
     * This is a test method that tests for updating of
     * {@link AnnouncementService#update(Object)} in the database.
     */
    @Test
    public void shouldUpdateAnnouncement() {

        announcementDto.setActive(false);

        BigDecimal serviceCost = new BigDecimal(NumberConstant.ONE_HUNDRED)
                .setScale(2, RoundingMode.UP);

        announcementDto.setServiceCost(serviceCost);

        announcementDto.setRevelationText("I want to sell");

        announcementDto.setPublicationDate(LocalDate.of(NumberConstant.YEAR,
                NumberConstant.MONTH, NumberConstant.DAY_OF_MONTH));

        announcementDto.setName("Update");

        try {
            announcementService.update(announcementDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        announcementDto.setVersion(1);

        compareTwoAnnouncementDto(announcementDto, announcementService.find(1));
    }


    /**
     * This is a test method that tests for deleting of
     * {@link AnnouncementService#delete(int)} in the database.
     */
    @Test
    public void shouldDeleteAnnouncement() {

        announcementService.delete(1);

        Assert.assertEquals(0, announcementRepository.count());
    }


    /**
     * This is a test method that tests for deleting of
     * {@link AnnouncementService#deleteAnnouncementById(int)}
     * in the database.
     */
    @Test
    public void shouldDeleteAnnouncementById() {

        announcementService.deleteAnnouncementById(1);

        Assert.assertEquals(0, announcementRepository.count());
    }


    /**
     * This is a test method that tests for deleting of
     * {@link AuthorService#deleteAnnouncementsByAuthorId (int)}
     * in the database.
     */
    @Test
    public void shouldDeleteAnnouncementsByAuthorId() {

        BigDecimal serviceCost = new BigDecimal(
                NumberConstant.THREE_HUNDRED_AND_ELEVEN_NUMBER).
                setScale(2, RoundingMode.UP);

        Announcement announcementOne = new Announcement(0, 0,
                "Tomas", LocalDate.now(), "I wonna go",
                true, serviceCost, heading, author);

        authorService.deleteAnnouncementsByAuthorId(1);

        Assert.assertNull(announcementService.find(1));
    }


    /**
     * This is a test method that tests for finding all announcements
     * from {@link Heading#id} of
     * {@link AnnouncementService#getAllByHeadingId(int)} in the database.
     */
    @Test
    public void shouldGetAllAnnouncementsByHeadingId() {

        List<AnnouncementDto> listOfAnnouncements = getListOfAnnouncementsDto();

        List<AnnouncementDto> allByHeadingId = announcementService.
                getAllByHeadingId(1);

        for (int i = 0; i < NumberConstant.THREE_NUMBER; i++) {
            compareTwoAnnouncementDto(listOfAnnouncements
                    .get(i), allByHeadingId.get(i));
        }
    }


    /**
     * This is a test method that tests for finding all announcements of
     * {@link AnnouncementService#filterAllByDate(LocalDate)} in the database.
     */
    @Test
    public void shouldGetAllAnnouncementsByDate() {

        List<AnnouncementDto> listOfAnnouncements = getListOfAnnouncementsDto();

        List<AnnouncementDto> filteredAllByDate = announcementService.
                filterAllByDate(LocalDate.now());

        for (int i = 0; i < NumberConstant.THREE_NUMBER; i++) {
            compareTwoAnnouncementDto(listOfAnnouncements.
                    get(i), filteredAllByDate.get(i));
        }
    }


    /**
     * This is a test method that tests for finding all announcements of
     * {@link AnnouncementService#filterAllByRevelationText(String)}
     * in the database.
     */
    @Test
    public void shouldGetAllAnnouncementsByRevelationText() {

        List<AnnouncementDto> listOfAnnouncements = getListOfAnnouncementsDto();

        List<AnnouncementDto> filteredByRevelation = announcementService
                .filterAllByRevelationText("I wonna go");

        for (int i = 0; i < NumberConstant.THREE_NUMBER; i++) {
            compareTwoAnnouncementDto(listOfAnnouncements
                    .get(i), filteredByRevelation.get(i));
        }
    }


    /**
     * This is a method for testing announcements pagination of
     * {@link AnnouncementService#showSomeAnnouncementsPagination(int, int)}
     * in the database.
     */
    @Test
    public void shouldGetSomeAnnouncementsPagination() {

        List<AnnouncementDto> listOfAnnouncements = getListOfAnnouncementsDto();

        List<AnnouncementDto> listSomePagination = announcementService
                .showSomeAnnouncementsPagination(
                        0, NumberConstant.THREE_NUMBER);

        for (int i = 0; i < NumberConstant.THREE_NUMBER; i++) {
            compareTwoAnnouncementDto(listOfAnnouncements
                    .get(i), listSomePagination.get(i));
        }
    }


    /**
     * This is a test method which test method of
     * {@link HeadingService#getAnnouncementsFromSomeHeadings(List)}
     * in the database for getting all announcements from some headings by
     * {@link List<Integer>} ids.
     */
    @Test
    public void shouldGetAllAnnouncementsFromSomeHeadings() {

        List<AnnouncementDto> listOfAnnouncements = getListOfAnnouncementsDto();

        Heading headingOne = new Heading(0, 0, "Anastasok");

        final HeadingDto headingDto = modelMapper
                .map(headingOne, HeadingDto.class);

        try {
            headingService.save(headingDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BigDecimal serviceCost = new BigDecimal(
                NumberConstant.THREE_HUNDRED_AND_ELEVEN_NUMBER)
                .setScale(2, RoundingMode.UP);

        headingOne.setId(1);

        Announcement announcementOne = new Announcement(0, 0,
                "Tomasadafefd", LocalDate.now(), "I wonna go",
                true, serviceCost, headingOne, author);


        Announcement announcementTwo = new Announcement(0, 0,
                "Tomasadafefd", LocalDate.now(), "I wonna go",
                true, serviceCost, headingOne, author);

        final AnnouncementDto announcementDtoOne = modelMapper
                .map(announcementOne, AnnouncementDto.class);

        final AnnouncementDto announcementDtoTwo = modelMapper
                .map(announcementTwo, AnnouncementDto.class);

        try {
            announcementService.save(announcementDtoOne);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            announcementService.save(announcementDtoTwo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        announcementDtoOne.setId(NumberConstant.FOUR);

        announcementDtoTwo.setId(NumberConstant.FIVE_NUMBER);

        listOfAnnouncements.add(announcementDtoOne);

        listOfAnnouncements.add(announcementDtoTwo);

        List<Announcement> announcementsFromSomeHeadings = headingService
                .getAnnouncementsFromSomeHeadings(Arrays.asList(1, 2));

        for (int i = 0; i < NumberConstant.FIVE_NUMBER; i++) {
            final Announcement announcementThree =
                    announcementsFromSomeHeadings.get(i);
            final AnnouncementDto announcementDtoThree = modelMapper
                    .map(announcementThree, AnnouncementDto.class);
            compareTwoAnnouncementDto(listOfAnnouncements
                    .get(i), announcementDtoThree);
        }
    }


    /**
     * This method creates two announcementDto objects
     * for other test methods.
     *
     * @return List<AnnouncementDto>.
     */
    private List<AnnouncementDto> getListOfAnnouncementsDto() {

        BigDecimal serviceCost = new BigDecimal(
                NumberConstant.THREE_HUNDRED_AND_ELEVEN_NUMBER).
                setScale(2, RoundingMode.UP);

        AnnouncementDto announcementOne = new AnnouncementDto(0, 0,
                "Tomasadafefd", true, LocalDate.now(),
                "I wonna go", serviceCost, author.getId(), heading);

        AnnouncementDto announcementTwo = new AnnouncementDto(0, 0,
                "kolonaTwo", false, LocalDate.now(),
                "I wonna go", serviceCost, author.getId(), heading);

        try {
            announcementService.save(announcementOne);

            announcementService.save(announcementTwo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<AnnouncementDto> announcementsDto = new ArrayList<>();

        announcementOne.setId(2);

        announcementTwo.setId(NumberConstant.THREE_NUMBER);

        announcementsDto.add(announcementDto);

        announcementsDto.add(announcementOne);

        announcementsDto.add(announcementTwo);

        return announcementsDto;
    }


    /**
     * This private method which's compare two AnnouncementDto.
     *
     * @param target {@link AnnouncementDto}.
     * @param source {@link AnnouncementDto}.
     */
    private void compareTwoAnnouncementDto(
            final AnnouncementDto target, final AnnouncementDto source) {

        Assert.assertEquals(target.getId(), source.getId());

        Assert.assertEquals(target.getVersion(), source.getVersion());

        Assert.assertEquals(
                target.getRevelationText(), source.getRevelationText());

        BigDecimal expected = target.getServiceCost()
                .setScale(2, RoundingMode.UP);

        BigDecimal actual = source.getServiceCost()
                .setScale(2, RoundingMode.UP);

        Assert.assertEquals(expected, actual);

        Assert.assertEquals(target.getName(), source.getName());

        Assert.assertEquals(target.isActive(), source.isActive());

        Assert.assertEquals(
                target.getPublicationDate(), source.getPublicationDate());
    }
}
