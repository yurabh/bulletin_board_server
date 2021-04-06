package com.service;

import com.config.ConfigAppTest;
import com.constant.NumberConstant;
import com.domain.Author;
import com.domain.SuitableAd;
import com.dto.AuthorDto;
import com.dto.SuitableAdDto;
import com.repository.SuitableRepository;
import com.service.impl.SuitableAdServiceImpl;
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

/**
 * This is a class for testing the class of the
 * {@link SuitableAdServiceImpl} and its methods.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(ConfigAppTest.class)
@WebAppConfiguration
@Sql(scripts = {
        "classpath:scripts/truncate_tables/truncate_table_suitableAd.sql",
        "classpath:scripts/truncate_tables/truncate_table_author.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class SuitableAdServiceTest {


    /**
     * This is a field for injection {@link SuitableAdServiceImpl}
     * in this class.
     */
    @Autowired
    private SuitableAdServiceImpl suitableAdService;


    /**
     * This is a field for injection {@link AuthorService}
     * in this class.
     */
    @Autowired
    private AuthorService authorService;


    /**
     * This is a field for injection {@link SuitableRepository}
     * in this class.
     */
    @Autowired
    private SuitableRepository suitableRepository;


    /**
     * This is class {@link ModelMapper} for convert objects.
     */
    @Autowired
    private ModelMapper modelMapper;


    /**
     * This is a field {@link SuitableAd} we use it for
     * testing too.
     */
    private SuitableAd suitableAd;


    /**
     * This is a field {@link com.dto.SuitableAdDto}
     * we use it for testing too.
     */
    private SuitableAdDto suitableAdDto;


    /**
     * This is a method which runs before each test and which storage of
     * {@link SuitableAdServiceImpl#save(Object)}
     * in the database.
     */
    @Before
    public void saveSuitableAdBeforeEach() {

        Author author = new Author("Author");

        author.setId(0);

        author.setVersion(0);

        author.setActive(true);

        author.setPassword("1111111");

        author.setLastName("Kerry");

        author.setActive(true);

        final AuthorDto authorDto = modelMapper
                .map(author, AuthorDto.class);

        authorService.save(authorDto);

        BigDecimal priceFrom = new BigDecimal(NumberConstant.
                THREE_HUNDRED_AND_ELEVEN_NUMBER).setScale(2, RoundingMode.UP);

        BigDecimal priceTo = new BigDecimal(NumberConstant.THREE_HUNDRED)
                .setScale(2, RoundingMode.UP);

        suitableAd = new SuitableAd(0, 0,
                "SuitableAd Create", "I wont to buy",
                priceFrom, priceTo, author);

        suitableAdDto = modelMapper.map(suitableAd, SuitableAdDto.class);

        suitableAdDto.setAuthorFkId(1);

        suitableAdService.save(suitableAdDto);

        suitableAdDto.setId(1);

        comparingSuitableAdDto(suitableAdDto, suitableAdService.find(1));
    }


    /**
     * This is a test method that tests for finding of
     * {@link SuitableAdServiceImpl#find(int)} in the database.
     */
    @Test
    public void shouldFindSuitableAd() {
        comparingSuitableAdDto(suitableAdDto, suitableAdService.find(1));
    }


    /**
     * This is a test method that tests for updating of
     * {@link SuitableAdServiceImpl#update(Object)} in the database.
     */
    @Test
    public void shouldUpdateSuitableAd() {

        suitableAd.setCategory("Update SuitableAd");

        BigDecimal priceTo = new BigDecimal(NumberConstant.TWO_HUNDRED)
                .setScale(2, RoundingMode.UP);

        suitableAd.setPriceTo(priceTo);

        BigDecimal priceFrom = new BigDecimal(NumberConstant.TWENTY)
                .setScale(2, RoundingMode.UP);

        suitableAd.setPriceFrom(priceFrom);

        suitableAd.setTitle("SuitableAd update");

        suitableAdService.update(suitableAdDto);

        suitableAd.setVersion(1);

        comparingSuitableAdDto(suitableAdDto, suitableAdService.find(1));
    }


    /**
     * This is a test method that tests for deleting of
     * {@link SuitableAdServiceImpl#delete(int)} in the database.
     */
    @Test
    public void shouldDeleteSuitableAd() {

        suitableAdService.delete(1);

        Assert.assertEquals(0, suitableRepository.count());
    }


    /**
     * This private method which's compare two SuitableAdDto.
     *
     * @param target {@link SuitableAdDto}.
     * @param source {@link SuitableAdDto}.
     */
    private void comparingSuitableAdDto(
            final SuitableAdDto target, final SuitableAdDto source) {

        Assert.assertEquals(target.getId(), source.getId());

        Assert.assertEquals(target.getVersion(), source.getVersion());

        Assert.assertEquals(target.getCategory(), source.getCategory());

        Assert.assertEquals(target.getTitle(), source.getTitle());

        BigDecimal sourcePriceFrom = source.getPriceFrom()
                .setScale(2, RoundingMode.UP);

        Assert.assertEquals(target.getPriceFrom(), sourcePriceFrom);

        BigDecimal sourcePriceTo = source.getPriceTo()
                .setScale(2, RoundingMode.UP);

        Assert.assertEquals(target.getPriceTo(), sourcePriceTo);
    }
}
