package com.controller;

import com.domain.SuitableAd;
import com.exception.SuitableAdFindException;
import com.exception.SuitableAdSaveException;
import com.exception.SuitableAdUpdateException;
import com.service.CRUDService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.Set;

/**
 * Class {@link SuitableAdController} using to perform suitableAd methods for user implementation
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

@RestController
@RequestMapping(value = "suitableAd")
public class SuitableAdController {


    /**
     * This is field of class {@link Logger} returns a logger for this controller
     */
    private static final Logger LOGGER = Logger.getLogger(SuitableAdController.class);


    /**
     * Field {@link CRUDService} is object instance of {@link CRUDService}
     * interface with {@link SuitableAd} type
     * It connects realization part with user application
     */
    private CRUDService<SuitableAd> suitableAdService;


    /**
     * This static field use for validation bean instances.This is object instance of {@link Validator}
     */
    private static Validator validator;


    /**
     * This is a static block.It use for is the entry point for Jakarta Bean @Validation
     * and Builds and returns a {@link ValidatorFactory} instance based on the default
     * Jakarta Bean Validation provider and following the XML configuration
     */
    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    /**
     * This is a constructor that injects object gain of the {@link CRUDService} and
     * injects it into the {@link SuitableAdController} class
     *
     * @param suitableAdService {@link CRUDService}
     */
    @Autowired
    public SuitableAdController(CRUDService<SuitableAd> suitableAdService) {
        this.suitableAdService = suitableAdService;
    }


    /**
     * This is a method that takes a suitableAd object from client and passes it to the
     * {@link CRUDService#save(Object)} method save
     *
     * @param suitableAd {@link SuitableAd}
     */
    @PostMapping(value = "/suitable-ads")
    public void save(@RequestBody SuitableAd suitableAd) {
        Set<ConstraintViolation<SuitableAd>> violations = validator.validate(suitableAd);
        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
        suitableAdService.save(suitableAd);
        LOGGER.info("SuitableAd saved");
    }


    /**
     * This handler only intercepts the exception in the case of an exception in the method
     * {@link SuitableAdController#save(SuitableAd)}
     *
     * @param e exception {@link ConstraintViolationException}
     * @return {@link ResponseEntity} with error message and error status
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> throwExceptionSuitableAdSave(ConstraintViolationException e) {
        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }


    /**
     * This method returns to the client a certain suitableAd on a certain identifier suitableAd and passes the
     * id to the {@link CRUDService#find(int)} for finding
     *
     * @param id int suitableAd id
     * @return suitableAd {@link SuitableAd}
     * @throws SuitableAdFindException if suitableAd could'nt find
     */
    @GetMapping(value = "/suitable-ads/{id}")
    public SuitableAd get(@PathVariable("id") int id) throws SuitableAdFindException {
        SuitableAd suitableAd = suitableAdService.find(id);
        if (suitableAd == null) {
            throw new SuitableAdFindException("Cannot find SuitableAd by id: " + id);
        }
        LOGGER.info("SuitableAd found");
        return suitableAd;
    }


    /**
     * This handler only intercepts the exception in the case of an exception in the method
     * {@link SuitableAdController#get(int)}
     *
     * @param e exception {@link SuitableAdFindException}
     * @return {@link ResponseEntity} with error message and error status
     */
    @ExceptionHandler(SuitableAdFindException.class)
    public ResponseEntity<String> throwExceptionSuitableAdFind(SuitableAdFindException e) {
        LOGGER.error("Cannot find SuitableAd by id: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
    }


    /**
     * This method takes a suitableAd object with some id from the client and passes it in
     * {@link CRUDService#update(Object)} for updating
     *
     * @param suitableAd {@link SuitableAd} with new parameters for setting up
     */
    @PutMapping(value = "/suitable-ads")
    public void update(@RequestBody SuitableAd suitableAd) {
        Set<ConstraintViolation<SuitableAd>> violations = validator.validate(suitableAd);
        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
        suitableAdService.update(suitableAd);
        LOGGER.info("SuitableAd updated");
    }

    /**
     * This method takes an ID from the client to delete a specific suitableAd and passes it to
     * {@link CRUDService#delete(int)} for deleting
     *
     * @param id int
     */
    @DeleteMapping(value = "/suitable-ads/{id}")
    public void delete(@PathVariable("id") int id) {
        suitableAdService.delete(id);
    }
}
