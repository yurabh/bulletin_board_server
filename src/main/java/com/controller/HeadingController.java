package com.controller;

import com.constant.ExceptionConstant;
import com.constant.LoggerConstants;
import com.domain.Announcement;

import com.dto.HeadingDto;
import com.exception.custom_exception.HeadingException;
import com.service.HeadingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * Class {@link HeadingController} using to perform heading methods
 * for user implementation.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@RestController
@RequestMapping("heading")
public class HeadingController {


    /**
     * This is field of class {@link Logger} returns a logger
     * for this controller.
     */
    private static final Logger LOGGER = Logger.
            getLogger(HeadingController.class);


    /**
     * Field {@link HeadingService} is object instance of {@link HeadingService}
     * interface with {@link HeadingDto} type.
     * It connects realization part with user application.
     */
    private final HeadingService headingService;


    /**
     * This field use for validation bean instances.
     * This is object instance of {@link Validator}.
     */
    private Validator validator;


    /**
     * This is a constructor that injects the object gain of the
     * {@link HeadingService} interface into the
     * {@link HeadingController} class.
     *
     * @param serviceHeading {@link HeadingService}.
     */
    @Autowired
    public HeadingController(final HeadingService serviceHeading) {
        this.headingService = serviceHeading;
    }


    /**
     * This is method for injecting {@link Validator}.
     * It'll injects class {@link Validator}
     * in controllers such as:
     * {@link HeadingController}
     * and HeadingControllerTest for test cases.
     *
     * @param validatorHeading {@link Validator}.
     */
    @Autowired
    public void setValidator(final Validator validatorHeading) {
        this.validator = validatorHeading;
    }


    /**
     * This is a method that takes a {@link com.dto.HeadingDto}
     * object and passes it to the
     * {@link HeadingService#save(Object)} method save.
     *
     * @param headingDto {@link HeadingDto}.
     * @throws ConstraintViolationException if heading couldn't save.
     */
    @PostMapping(value = "/headings")
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public void save(@RequestBody final HeadingDto headingDto) {
        Set<ConstraintViolation<HeadingDto>> violations = validator
                .validate(headingDto);
        if (!violations.isEmpty()) {
            LOGGER.error(violations.toString());
            throw new ConstraintViolationException(violations);
        }
        headingService.save(headingDto);
        LOGGER.info(LoggerConstants.HEADING_SAVED);
    }


    /**
     * This method returns to the client a certain {@link HeadingDto}
     * on a certain identifier.
     *
     * @param id int headingDto id.
     * @return headingDto {@link HeadingDto}.
     * @throws HeadingException if Heading could'nt find.
     */
    @GetMapping(value = "/headings/{id}")
    public ResponseEntity<HeadingDto> get(
            @PathVariable("id") final int id) throws HeadingException {
        HeadingDto headingDto = headingService.find(id);
        if (headingDto == null) {
            LOGGER.error(LoggerConstants.HEADING_NOT_FOUND + id);
            throw new HeadingException(ExceptionConstant.
                    HEADING_GET_EXCEPTION + id);
        }
        LOGGER.info(LoggerConstants.HEADING_FOUND + id);
        return new ResponseEntity<>(headingDto, HttpStatus.FOUND);
    }


    /**
     * This method takes a {@link HeadingDto} object with some id from
     * the client and passes it in {@link HeadingService#update(Object)}
     * for updating.
     *
     * @param headingDto {@link HeadingDto} with new parameters
     *                   {@link HeadingDto}.
     * @throws ConstraintViolationException if heading couldn't update.
     */
    @PutMapping(value = "/headings")
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public void update(@RequestBody final HeadingDto headingDto) {
        Set<ConstraintViolation<HeadingDto>> violations = validator.
                validate(headingDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        headingService.update(headingDto);
        LOGGER.info(LoggerConstants.HEADING_UPDATE);
    }


    /**
     * This method takes an ID from the client to delete a specific
     * {@link HeadingDto} and passes it to{@link HeadingService#delete(int)}
     * for deleting.
     *
     * @param id int headingDto id.
     */
    @DeleteMapping(value = "/headings/{id}")
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public void delete(@PathVariable("id") final int id) {
        headingService.delete(id);
    }


    /**
     * This method takes an ID from the client to delete a specific
     * {@link HeadingDto} and passes it to
     * {@link HeadingService#deleteHeading(int)} for deleting.
     *
     * @param id int headingDto id.
     */
    @DeleteMapping(value = "/headings/{id}/delete-by-id")
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public void deleteHeadingById(@PathVariable("id") final int id) {
        headingService.deleteHeading(id);
    }


    /**
     * This method takes a collection of {@link List<Integer>} Ids from the
     * client to return all announcements from certain headings and passes the
     * collection to
     * {@link HeadingService#getAnnouncementsFromSomeHeadings(List)}.
     *
     * @param ids List<Integer>.
     * @return {@link List<Announcement>}.
     * @throws HeadingException if list of announcements could'nt find.
     */
    @GetMapping(value = "/headings/get-announce-from-some-headings")
    public List<Announcement> getAnnouncementsFromHeadings(
            @RequestParam("param")
            @Valid @NotEmpty final List<@NotNull Integer> ids)
            throws HeadingException {
        List<Announcement> announcementsFromSomeHeadings = headingService.
                getAnnouncementsFromSomeHeadings(ids);
        if (announcementsFromSomeHeadings.isEmpty()) {
            LOGGER.error(LoggerConstants.
                    ALL_ANNOUNCEMENTS_FROM_SOME_HEADINGS_NOT_FIND);
            throw new HeadingException(ExceptionConstant.
                    HEADING_GET_ALL_ANNOUNCEMENTS_FROM_SOME_HEADINGS_EXCEPTION);
        }
        LOGGER.info(LoggerConstants.ALL_ANNOUNCEMENTS_FROM_SOME_HEADINGS_FOUND);
        return announcementsFromSomeHeadings;
    }
}
