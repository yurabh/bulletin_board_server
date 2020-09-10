package com.service;

import com.config.ConfigAppTest;
import com.domain.*;
import com.repository.SuitableRepository;
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

/**
 * This is a class for testing the class of the {@link SuitableAdServiceImpl} and its methods
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(ConfigAppTest.class)
@WebAppConfiguration
@Sql(scripts = {"classpath:scripts/truncate_tables/truncate_table_suitableAd.sql",
        "classpath:scripts/truncate_tables/truncate_table_author.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class SuitableAdServiceTest {


    /**
     * This is a field for injection {@link SuitableAdServiceImpl} in this class
     */
    @Autowired
    private SuitableAdServiceImpl suitableAdService;


    /**
     * This is a field for injection {@link AuthorService} in this class
     */
    @Autowired
    private AuthorService authorService;


    /**
     * This is a field for injection {@link SuitableRepository} in this class
     */
    @Autowired
    private SuitableRepository suitableRepository;


    /**
     * This is a field {@link SuitableAd} we use it for testing too
     */
    private SuitableAd suitableAd;


    /**
     * This is a method which runs before each test and which storage of
     * {@link SuitableAdServiceImpl#save(Object)} in the database
     */
    @Before
    public void saveSuitableAdBeforeEach() {

        Author author = new Author("Author Suitab");

        author.setVersion(0);

        authorService.save(author);

        BigDecimal priceFrom = new BigDecimal(300.223).setScale(2, RoundingMode.UP);

        BigDecimal priceTo = new BigDecimal(1300.445).setScale(2, RoundingMode.UP);

        suitableAd = new SuitableAd(0, "SuitableAd Create", "I wont to buy",
                priceFrom, priceTo, author);

        suitableAdService.save(suitableAd);

        comparingSuitableAd(suitableAd, suitableAdService.find(1));
    }


    /**
     * This is a test method that tests for finding of {@link SuitableAdServiceImpl#find(int)} in the database
     */
    @Test
    public void shouldFindSuitableAd() {
        comparingSuitableAd(suitableAd, suitableAdService.find(1));
    }


    /**
     * This is a test method that tests for updating of {@link SuitableAdServiceImpl#update(Object)} in the database
     */
    @Test
    public void shouldUpdateSuitableAd() {

        suitableAd.setCategory("Update SuitableAd");

        BigDecimal priceTo = new BigDecimal(200.123).setScale(2, RoundingMode.UP);

        suitableAd.setPriceTo(priceTo);

        BigDecimal priceFrom = new BigDecimal(20.0001).setScale(2, RoundingMode.UP);

        suitableAd.setPriceFrom(priceFrom);

        suitableAd.setTitle("SuitableAd update");

        suitableAdService.update(suitableAd);

        suitableAd.setVersion(1);

        comparingSuitableAd(suitableAd, suitableAdService.find(1));
    }


    /**
     * This is a test method that tests for deleting of {@link SuitableAdServiceImpl#delete(int)} in the database
     */
    @Test
    public void shouldDeleteSuitableAd() {

        suitableAdService.delete(1);

        Assert.assertEquals(0, suitableRepository.count());
    }


    /**
     * This private method which's compare two SuitableAd
     *
     * @param target {@link SuitableAd}
     * @param source {@link SuitableAd}
     */
    private void comparingSuitableAd(SuitableAd target, SuitableAd source) {

        Assert.assertEquals(target.getId(), source.getId());

        Assert.assertEquals(target.getVersion(), source.getVersion());

        Assert.assertEquals(target.getCategory(), source.getCategory());

        Assert.assertEquals(target.getTitle(), source.getTitle());

        BigDecimal sourcePriceFrom = source.getPriceFrom().setScale(2, RoundingMode.UP);

        Assert.assertEquals(target.getPriceFrom(), sourcePriceFrom);

        BigDecimal sourcePriceTo = source.getPriceTo().setScale(2, RoundingMode.UP);

        Assert.assertEquals(target.getPriceTo(), sourcePriceTo);
    }
}
