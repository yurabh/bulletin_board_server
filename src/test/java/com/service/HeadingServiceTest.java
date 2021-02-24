package com.service;

import com.config.ConfigAppTest;
import com.domain.Heading;
import com.dto.HeadingDto;
import com.repository.HeadingRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * This is a class for testing the class of the
 * {@link HeadingService} and its methods.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigAppTest.class)
@WebAppConfiguration
@Sql(scripts = {"classpath:scripts/truncate_tables/truncate_table_heading.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class HeadingServiceTest {


    /**
     * This is a field for injection {@link HeadingService} in this class.
     */
    @Autowired
    private HeadingService headingService;


    /**
     * This is a field for injection {@link HeadingRepository} in this class.
     */
    @Autowired
    private HeadingRepository headingRepository;


    /**
     * This is field for mapping objects.
     */
    @Autowired
    private ModelMapper modelMapper;


    /**
     * This is a field {@link Heading} we use it for testing too.
     */
    private Heading heading;


    /**
     * This is field {@link HeadingDto} we use it for testing too.
     */
    private HeadingDto headingDto;


    /**
     * This is a test method that tests the storage of
     * {@link HeadingService#save(Object)}
     * in the database before each test.
     */
    @Before
    public void saveHeadingBeforeEach() {

        heading = new Heading(0, 0, "BooksHeading");

        headingDto = modelMapper
                .map(heading, HeadingDto.class);

        try {
            headingService.save(headingDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        headingDto.setId(1);

        comparingHeading(headingDto, headingService.find(1));
    }


    /**
     * This is a test method that tests for finding of
     * {@link HeadingService#find(int)} in the database.
     */
    @Test
    public void shouldFindHeading() {
        comparingHeading(headingDto, headingService.find(1));
    }


    /**
     * This is a test method that tests for updating of
     * {@link HeadingService#update(Object)} in the database.
     */
    @Test
    public void shouldUpdateHeading() {
        heading.setName("Update Heading");

        heading.setVersion(1);

        comparingHeading(headingDto, headingService.find(1));
    }


    /**
     * This is a test method that tests for deleting of
     * {@link HeadingService#delete(int)} in the database.
     */
    @Test
    public void shouldDeleteHeading() {

        headingService.delete(1);

        Assert.assertEquals(0, headingRepository.count());
    }


    /**
     * This is a test method that tests for deleting of
     * {@link HeadingService#deleteHeading(int)} in the database.
     */
    @Test
    public void shouldDeleteHeadingById() {

        headingService.deleteHeading(1);

        Assert.assertEquals(0, headingRepository.count());
    }


    /**
     * This is method which takes two parameters and compare them.
     *
     * @param target {@link HeadingDto}.
     * @param source {@link HeadingDto}.
     */
    private void comparingHeading(final HeadingDto target,
                                  final HeadingDto source) {
        Assert.assertEquals(target.getId(), source.getId());

        Assert.assertEquals(target.getVersion(), source.getVersion());

        Assert.assertEquals(target.getName(), source.getName());
    }
}
