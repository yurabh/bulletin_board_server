package com.controller;

import com.constant.ExceptionConstant;
import com.constant.LoggerConstants;
import com.dto.SuitableAdDto;
import com.exception.custom_exception.SuitableAdException;

import com.service.CRUDService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * Class {@link SuitableAdController} using to perform suitableAd methods
 * for user implementation.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@RestController
@RequestMapping(value = "suitableAd")
public class SuitableAdController {


    /**
     * This is field of class {@link Logger} returns
     * a logger for this controller.
     */
    private static final Logger LOGGER = Logger
            .getLogger(SuitableAdController.class);


    /**
     * Field {@link CRUDService} is object instance of {@link CRUDService}
     * interface with {@link SuitableAdDto} type it connects realization part
     * with user application.
     */
    private final CRUDService<SuitableAdDto> suitableAdService;


    /**
     * This field use for validation bean instances.
     * This is object instance of {@link Validator}.
     */
    private Validator validator;


    /**
     * This is a constructor that injects object gain of the {@link CRUDService}
     * and injects it into the {@link SuitableAdController} class.
     *
     * @param serviceSuitableAdDto {@link CRUDService}.
     */
    @Autowired
    public SuitableAdController(final CRUDService<SuitableAdDto>
                                        serviceSuitableAdDto) {
        this.suitableAdService = serviceSuitableAdDto;
    }


    /**
     * This is method for injecting {@link Validator}.
     * It'll injects class {@link Validator}
     * in controllers such as:
     * {@link SuitableAdController}
     * and SuitableAdControllerTest for test cases.
     *
     * @param validatorSuitableAd {@link Validator}.
     */
    @Autowired
    public void setValidator(final Validator validatorSuitableAd) {
        this.validator = validatorSuitableAd;
    }


    /**
     * This is a method that takes a suitableAdDto object from client and passes
     * it to the {@link CRUDService#save(Object)} method save.
     *
     * @param suitableAdDto {@link SuitableAdDto}.
     * @throws ConstraintViolationException if suitableAd could'nt save.
     */
    @PostMapping(value = "/suitable-ads")
    public void save(@RequestBody final SuitableAdDto suitableAdDto) {
        Set<ConstraintViolation<SuitableAdDto>> violations = validator.
                validate(suitableAdDto);
        if (!violations.isEmpty()) {
            LOGGER.error(violations.toString());
            throw new ConstraintViolationException(violations);
        }
        suitableAdService.save(suitableAdDto);
        LOGGER.info(LoggerConstants.SUITABLE_AD_SAVED);
    }


    /**
     * This method returns to the client a certain suitableAdDto on a certain
     * identifier suitableAdDto and passes the id to the
     * {@link CRUDService#find(int)} for finding.
     *
     * @param id int suitableAdDto id.
     * @return suitableAdDto {@link SuitableAdDto}.
     * @throws SuitableAdException if suitableAd could'nt find.
     */
    @GetMapping(value = "/suitable-ads/{id}")
    public SuitableAdDto get(
            @PathVariable("id") final int id) throws SuitableAdException {
        SuitableAdDto suitableAdDto = suitableAdService.find(id);
        if (suitableAdDto == null) {
            LOGGER.error(LoggerConstants.SUITABLE_AD_NOT_FIND + id);
            throw new SuitableAdException(
                    ExceptionConstant.SUITABLE_AD_GET_EXCEPTION + id);
        }
        LOGGER.info(LoggerConstants.SUITABLE_AD_FOUND);
        return suitableAdDto;
    }


    /**
     * This method takes a suitableAdDto object with some id
     * from the client and passes it in {@link CRUDService#update(Object)}
     * for updating.
     *
     * @param suitableAdDto {@link SuitableAdDto} with new parameters
     *                      for setting up.
     * @throws ConstraintViolationException if suitableAd could'nt update.
     */
    @PutMapping(value = "/suitable-ads")
    public void update(@RequestBody final SuitableAdDto suitableAdDto) {
        Set<ConstraintViolation<SuitableAdDto>> violations = validator
                .validate(suitableAdDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        suitableAdService.update(suitableAdDto);
        LOGGER.info(LoggerConstants.SUITABLE_AD_UPDATE);
    }


    /**
     * This method takes an ID from the client to delete a specific suitableAd
     * and passes it to {@link CRUDService#delete(int)} for deleting.
     *
     * @param id int.
     */
    @DeleteMapping(value = "/suitable-ads/{id}")
    public void delete(@PathVariable("id") final int id) {
        suitableAdService.delete(id);
    }
}
