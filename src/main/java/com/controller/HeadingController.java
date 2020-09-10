package com.controller;

import com.domain.Announcement;
import com.domain.Heading;
import com.exception.*;
import com.service.HeadingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * Class {@link HeadingController} using to perform heading methods for user implementation
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

@RestController
@RequestMapping("heading")
public class HeadingController {


    /**
     * This is field of class {@link Logger} returns a logger for this controller
     */
    private static final Logger LOGGER = Logger.getLogger(HeadingController.class);


    /**
     * Field {@link HeadingService} is object instance of {@link HeadingService} interface with
     * {@link Heading} type.It connects realization part with user application
     */
    private HeadingService headingService;


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
     * This is a constructor that injects the object gain of the {@link HeadingService} interface
     * into the {@link HeadingController} class
     *
     * @param headingService {@link HeadingService}
     */
    @Autowired
    public HeadingController(HeadingService headingService) {
        this.headingService = headingService;
    }


    /**
     * This is a method that takes a {@link Heading} object and passes it
     * to the {@link HeadingService#save(Object)} method save
     *
     * @param heading {@link Heading}
     */
    @PostMapping(value = "/headings")
    public void save(@RequestBody Heading heading) {
        Set<ConstraintViolation<Heading>> violations = validator.validate(heading);
        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
        headingService.save(heading);
        LOGGER.info("Heading saved");
    }


    /**
     * This handler only intercepts the exception in the case of an exception in the method
     * {@link HeadingController#save(Heading)}
     *
     * @param e exception {@link ConstraintViolationException}
     * @return {@link ResponseEntity} with error message and error status
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> throwExceptionHeadingSave(ConstraintViolationException e) {
        String message = e.getMessage();
        LOGGER.error(message);
        return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);

    }


    /**
     * This method returns to the client a certain {@link Heading} on a certain identifier
     *
     * @param id int heading id
     * @return heading {@link Heading}
     * @throws HeadingFindException if Heading could'nt find
     */
    @GetMapping(value = "/headings/{id}")
    public ResponseEntity<Heading> get(@PathVariable("id") int id) throws HeadingFindException {
        Heading heading = headingService.find(id);
        if (heading == null) {
            LOGGER.error("Cannot find heading by id: " + id);
            throw new HeadingFindException("Cannot find heading by id: " + id);
        }
        LOGGER.info("Heading found by id: " + id);
        return new ResponseEntity<>(heading, HttpStatus.FOUND);
    }


    /**
     * This handler only intercepts the exception in the case of an exception in the method
     * {@link HeadingController#get(int)}
     *
     * @param e exception {@link HeadingFindException}
     * @return {@link ResponseEntity} with error message and error status
     */
    @ExceptionHandler(HeadingFindException.class)
    public ResponseEntity<String> throwExceptionHeadingFind(HeadingFindException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
    }


    /**
     * This method takes a {@link Heading} object with some id from the client and passes it in
     * {@link HeadingService#update(Object)} for updating
     *
     * @param heading {@link Heading} with new parameters {@link Heading}
     */
    @PutMapping(value = "/headings")
    public void update(@RequestBody Heading heading) {
        Set<ConstraintViolation<Heading>> violations = validator.validate(heading);
        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
        headingService.update(heading);
        LOGGER.info("Heading updated");
    }


    /**
     * This method takes an ID from the client to delete a specific {@link Heading} and passes it to
     * {@link HeadingService#delete(int)} for deleting
     *
     * @param id int heading id
     */
    @DeleteMapping(value = "/headings/{id}")
    public void delete(@PathVariable("id") int id) {
        headingService.delete(id);
    }


    /**
     * This method takes an ID from the client to delete a specific {@link Heading} and passes it to
     * {@link HeadingService#deleteHeading(int)} for deleting
     *
     * @param id int heading id
     */
    @DeleteMapping(value = "/headings/{id}/delete-by-id")
    public void deleteHeadingById(@PathVariable("id") int id) {
        headingService.deleteHeading(id);
    }


    /**
     * This method takes a collection of {@link List<Integer>} IDs from the client to return all announcements from
     * certain headings and passes the collection to {@link HeadingService#getAnnouncementsFromSomeHeadings(List)}
     *
     * @param ids List<Integer>
     * @return {@link List<Announcement>}
     * @throws HeadingGetAllAnnouncementFromSomeHeadingException if list of announcements could'nt find
     */
    @GetMapping(value = "/headings/get-announce-from-some-headings")
    public List<Announcement> getAnnouncementsFromHeadings(
            @RequestParam("param") @Valid @NotEmpty List<@NotNull Integer> ids)
            throws HeadingGetAllAnnouncementFromSomeHeadingException {
        List<Announcement> announcementsFromSomeHeadings = headingService.getAnnouncementsFromSomeHeadings(ids);
        if (announcementsFromSomeHeadings.isEmpty()) {
            LOGGER.error("Cannot find Announcements from some Headings");
            throw new HeadingGetAllAnnouncementFromSomeHeadingException("Cannot find some announcement from some Heading");
        }
        LOGGER.info("Announcements found from some Headings");
        return announcementsFromSomeHeadings;
    }


    /**
     * This handler only intercepts the exception in the case of an exception in the method
     * {@link HeadingController#getAnnouncementsFromHeadings(List)}
     *
     * @param e exception {@link HeadingGetAllAnnouncementFromSomeHeadingException}
     * @return {@link ResponseEntity} with error message and error status
     */
    @ExceptionHandler(HeadingGetAllAnnouncementFromSomeHeadingException.class)
    public ResponseEntity<String> throwExceptionGetAllAnnouncementsFromSomeHeadings
    (HeadingGetAllAnnouncementFromSomeHeadingException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
    }
}
