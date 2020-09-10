package com.service;

import com.config.ConfigAppTest;
import com.domain.Announcement;
import com.domain.Author;
import com.domain.Heading;
import com.repository.AnnouncementRepository;
import com.repository.AuthorRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.util.stream.IntStream;

/**
 * This is a class for testing the class of the {@link AnnouncementService} and its methods
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(ConfigAppTest.class)
@WebAppConfiguration
@Sql(scripts = {"classpath:scripts/truncate_tables/truncate_table_announcement.sql",
        "classpath:scripts/truncate_tables/truncate_table_author.sql",
        "classpath:scripts/truncate_tables/truncate_table_heading.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AnnouncementServiceTest {


    /**
     * This is a field for injection {@link AnnouncementService} in this class
     */
    @Autowired
    private AnnouncementService announcementService;


    /**
     * This is a field for injection {@link HeadingService} in this class
     */
    @Autowired
    private HeadingService headingService;


    /**
     * This is a field for injection {@link AuthorRepository} in this class
     */
    @Autowired
    private AuthorRepository authorRepository;


    /**
     * This is a field for injection {@link AuthorService} in this class
     */
    @Autowired
    private AuthorService authorService;


    /**
     * This is a field for injection {@link AnnouncementRepository} in this class
     */
    @Autowired
    private AnnouncementRepository announcementRepository;


    /**
     * This is a field {@link Announcement} we use it for testing too
     */
    private Announcement announcement;


    /**
     * This is a field {@link Heading} we use it for testing too
     */
    private Heading heading;


    /**
     * This is a field {@link Author} we use it for testing too
     */
    private Author author;


    /**
     * This is a test method that tests the storage of {@link AnnouncementService#save(Object)}
     * in the database before each test
     */
    @Before
    public void saveAnnouncementBeforeEach() {

        BigDecimal serviceCost = new BigDecimal(300.11).setScale(2, RoundingMode.UP);

        announcement = new Announcement("Announcement", LocalDate.now(), "I wonna go", serviceCost);

        announcement.setActive(true);

        author = new Author("Announcement");

        authorRepository.save(author);

        announcement.setAuthor(author);

        heading = new Heading("Announcement");

        headingService.save(heading);

        announcement.setHeading(heading);

        announcementService.save(announcement);

        compareTwoAnnouncement(announcement, announcementService.find(1));
    }


    /**
     * This is a test method that tests for finding of {@link AnnouncementService#find(int)} in the database
     */
    @Test
    public void shouldFindAnnouncement() {
        compareTwoAnnouncement(announcement, announcementService.find(1));
    }


    /**
     * This is a test method that tests for updating of {@link AnnouncementService#update(Object)} in the database
     */
    @Test
    public void shouldUpdateAnnouncement() {

        announcement.setActive(false);

        BigDecimal serviceCost = new BigDecimal(1000.00).setScale(2, RoundingMode.UP);

        announcement.setServiceCost(serviceCost);

        announcement.setRevelationText("I want to sell");

        announcement.setPublicationDate(LocalDate.of(2000, 7, 30));

        announcement.setName("Update");

        announcementService.update(announcement);

        announcement.setVersion(1);

        compareTwoAnnouncement(announcement, announcementService.find(1));
    }


    /**
     * This is a test method that tests for deleting of {@link AnnouncementService#delete(int)} in the database
     */
    @Test
    public void shouldDeleteAnnouncement() {

        announcementService.delete(1);

        Assert.assertEquals(0, announcementRepository.count());
    }


    /**
     * This is a test method that tests for deleting of {@link AnnouncementService#deleteAnnouncementById(int)}
     * in the database
     */
    @Test
    public void shouldDeleteAnnouncementById() {

        announcementService.deleteAnnouncementById(1);

        Assert.assertEquals(0, announcementRepository.count());
    }


    /**
     * This is a test method that tests for deleting of {@link AuthorService#deleteAnnouncementsByAuthorId (int)}
     * in the database
     */
    @Test
    public void shouldDeleteAnnouncementsByAuthorId() {

        BigDecimal serviceCost = new BigDecimal(300.11).setScale(2, RoundingMode.UP);

        Announcement announcementOne = new Announcement(0, "Tomasadafefd", LocalDate.now(),
                "I wonna go", true, serviceCost, heading, author);

        announcementService.save(announcementOne);

        authorService.deleteAnnouncementsByAuthorId(1);

        Assert.assertNull(announcementService.find(1));
    }


    /**
     * This is a test method that tests for finding all announcements from {@link Heading#id} of
     * {@link AnnouncementService#getAllByHeadingId(int)} in the database
     */
    @Test
    public void shouldGetAllAnnouncementsByHeadingId() {

        List<Announcement> listOfAnnouncements = getListOfAnnouncements();

        List<Announcement> allByHeadingId = announcementService.getAllByHeadingId(1);

        IntStream.range(0, 3).forEach(index -> {
            compareTwoAnnouncement(listOfAnnouncements.get(index), allByHeadingId.get(index));
        });
    }


    /**
     * This is a test method that tests for finding all announcements of
     * {@link AnnouncementService#filterAllByDate(LocalDate)} in the database
     */
    @Test
    public void shouldGetAllAnnouncementsByDate() {

        List<Announcement> listOfAnnouncements = getListOfAnnouncements();

        List<Announcement> filteredAllByDate = announcementService.filterAllByDate(LocalDate.now());

        IntStream.range(0, 3).forEach(index -> {
            compareTwoAnnouncement(listOfAnnouncements.get(index), filteredAllByDate.get(index));
        });
    }


    /**
     * This is a test method that tests for finding all announcements of
     * {@link AnnouncementService#filterAllByRevelationText(String)} in the database
     */
    @Test
    public void shouldGetAllAnnouncementsByRevelationText() {

        List<Announcement> listOfAnnouncements = getListOfAnnouncements();

        List<Announcement> filteredByRevelation = announcementService.filterAllByRevelationText("I wonna go");

        IntStream.range(0, 3).forEach(index -> {
            compareTwoAnnouncement(listOfAnnouncements.get(index), filteredByRevelation.get(index));
        });
    }


    /**
     * This is a method for testing announcements pagination of
     * {@link AnnouncementService#showSomeAnnouncementsPagination(int, int)} in the database
     */
    @Test
    public void shouldGetSomeAnnouncementsPagination() {

        List<Announcement> listOfAnnouncements = getListOfAnnouncements();

        List<Announcement> listSomePagination = announcementService.showSomeAnnouncementsPagination(0, 3);

        IntStream.range(0, 3).forEach(index -> {
            compareTwoAnnouncement(listOfAnnouncements.get(index), listSomePagination.get(index));
        });
    }


    /**
     * This is a test method which test method of
     * {@link HeadingService#getAnnouncementsFromSomeHeadings(List)} in the database for getting all announcements
     * from some headings by {@link List<Integer>} ids
     */
    @Test
    public void shouldGetAllAnnouncementsFromSomeHeadings() {

        List<Announcement> listOfAnnouncements = getListOfAnnouncements();

        Heading headingOne = new Heading("Anastasok");

        headingService.save(headingOne);

        BigDecimal serviceCost = new BigDecimal(300.11).setScale(2, RoundingMode.UP);

        Announcement announcementOne = new Announcement(0, "Tomasadafefd", LocalDate.now(),
                "I wonna go", true, serviceCost, headingOne, author);


        Announcement announcementTwo = new Announcement(0, "Tomasadafefd", LocalDate.now(),
                "I wonna go", true, serviceCost, headingOne, author);

        announcementService.save(announcementOne);

        announcementService.save(announcementTwo);

        listOfAnnouncements.add(announcementOne);

        listOfAnnouncements.add(announcementTwo);

        List<Announcement> announcementsFromSomeHeadings = headingService.getAnnouncementsFromSomeHeadings
                (Arrays.asList(1, 2));

        IntStream.range(0, 5).forEach(index -> {
            compareTwoAnnouncement(listOfAnnouncements.get(index), announcementsFromSomeHeadings.get(index));
        });
    }


    /**
     * This method creates two announcement objects for other test methods
     */
    private List<Announcement> getListOfAnnouncements() {

        BigDecimal serviceCost = new BigDecimal(300.11).setScale(2, RoundingMode.UP);

        Announcement announcementOne = new Announcement(0, "Tomasadafefd", LocalDate.now(),
                "I wonna go", true, serviceCost, heading, author);

        Announcement announcementTwo = new Announcement(0, "kolonaTwo", LocalDate.now(),
                "I wonna go", false, serviceCost, heading, author);

        announcementService.save(announcementOne);

        announcementService.save(announcementTwo);

        ArrayList<Announcement> announcements = new ArrayList<>();

        announcements.add(announcement);

        announcements.add(announcementOne);

        announcements.add(announcementTwo);

        return announcements;
    }


    /**
     * This private method which's compare two Announcement
     *
     * @param target {@link Announcement}
     * @param source {@link Announcement}
     */
    private void compareTwoAnnouncement(Announcement target, Announcement source) {

        Assert.assertEquals(target.getId(), source.getId());

        Assert.assertEquals(target.getVersion(), source.getVersion());

        Assert.assertEquals(target.getRevelationText(), source.getRevelationText());

        BigDecimal expected = target.getServiceCost().setScale(2, RoundingMode.UP);

        BigDecimal actual = source.getServiceCost().setScale(2, RoundingMode.UP);

        Assert.assertEquals(expected, actual);

        Assert.assertEquals(target.getName(), source.getName());

        Assert.assertEquals(target.isActive(), source.isActive());

        Assert.assertEquals(target.getPublicationDate(), source.getPublicationDate());
    }
}
