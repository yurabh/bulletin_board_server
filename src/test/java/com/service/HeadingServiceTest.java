package com.service;

import com.config.ConfigAppTest;
import com.domain.Heading;
import com.repository.HeadingRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * This is a class for testing the class of the {@link HeadingService} and its methods
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigAppTest.class)
@WebAppConfiguration
@Sql(scripts = {"classpath:scripts/truncate_tables/truncate_table_heading.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class HeadingServiceTest {


    /**
     * This is a field for injection {@link HeadingService} in this class
     */
    @Autowired
    private HeadingService headingService;


    /**
     * This is a field for injection {@link HeadingRepository} in this class
     */
    @Autowired
    private HeadingRepository headingRepository;


    /**
     * This is a field {@link Heading} we use it for testing too
     */
    private Heading heading;


    /**
     * This is a test method that tests the storage of {@link HeadingService#save(Object)}
     * in the database before each test
     */
    @Before
    public void saveHeadingBeforeEach() {

        heading = new Heading("BooksHeading");

        headingService.save(heading);

        Assert.assertEquals(heading.getId(), headingService.find(1).getId());
    }


    /**
     * This is a test method that tests for finding of {@link HeadingService#find(int)} in the database
     */
    @Test
    public void shouldFindHeading() {
        Assert.assertEquals(heading.getId(), headingService.find(1).getId());
    }


    /**
     * This is a test method that tests for updating of {@link HeadingService#update(Object)} in the database
     */
    @Test
    public void shouldUpdateHeading() {

        heading.setName("Update Heading");

        headingService.update(heading);

        Assert.assertEquals(heading, headingService.find(1));
    }


    /**
     * This is a test method that tests for deleting of {@link HeadingService#delete(int)} in the database
     */
    @Test
    public void shouldDeleteHeading() {

        headingService.delete(1);

        Assert.assertEquals(0, headingRepository.count());
    }


    /**
     * This is a test method that tests for deleting of {@link HeadingService#deleteHeading(int)} in the database
     */
    @Test
    public void shouldDeleteHeadingById() {

        headingService.deleteHeading(1);

        Assert.assertEquals(0, headingRepository.count());
    }
}
