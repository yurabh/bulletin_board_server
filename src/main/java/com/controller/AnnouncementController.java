package com.controller;

import com.constant.ExceptionConstant;
import com.constant.LoggerConstants;
import com.dto.AnnouncementDto;
import com.exception.custom_exception.AnnouncementException;
import com.service.AnnouncementService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Class {@link AnnouncementController} using to perform announcement operations
 * (save,find,update,delete etc....)for user implementation.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@RestController
@RequestMapping(value = "announcement")
public class AnnouncementController {


    /**
     * This is field of class {@link Logger} returns
     * a logger for this controller.
     */
    private static final Logger LOGGER = Logger.
            getLogger(AnnouncementController.class);


    /**
     * Field {@link AnnouncementService} is object instance of
     * {@link AnnouncementService} interface with {@link AnnouncementDto} type.
     * It connects realization part with user application.
     */
    private final AnnouncementService announcementService;


    /**
     * This field use for validation bean instances.
     * This is object instance of {@link Validator}.
     */
    private Validator validator;


    /**
     * This is a constructor that injects the object gain of the
     * {@link AnnouncementService} class into the {@link AnnouncementController}
     * class.
     *
     * @param service {@link AnnouncementService}.
     */
    @Autowired
    public AnnouncementController(final AnnouncementService service) {
        this.announcementService = service;
    }


    /**
     * This is method for injecting {@link Validator}.
     * It'll injects class {@link Validator}
     * in controllers such as:
     * {@link AnnouncementController}
     * and AnnouncementControllerTest for test cases.
     *
     * @param validatorAnnouncement {@link Validator}.
     */
    @Autowired
    public void setValidator(final Validator validatorAnnouncement) {
        this.validator = validatorAnnouncement;
    }


    /**
     * This is a method that takes an {@link AnnouncementDto} object from
     * client and passes it to the {@link AnnouncementService#save(Object)}
     * method save.
     *
     * @param announcementDto {@link AnnouncementDto}.
     * @throws ConstraintViolationException if announcement couldn't save.
     */
    @PostMapping(value = "/announcements", consumes = "application/json")
    public void save(@RequestBody final AnnouncementDto announcementDto) {
        Set<ConstraintViolation<AnnouncementDto>> violations = validator
                .validate(announcementDto);
        if (!violations.isEmpty()) {
            LOGGER.error(violations.toString());
            throw new ConstraintViolationException(violations);
        }
        announcementService.save(announcementDto);
        LOGGER.info(LoggerConstants.ANNOUNCEMENT_SAVED);
    }


    /**
     * This method returns to the client a certain {@link AnnouncementDto}
     * on a certain identifier announcement and passes the identifier to the
     * {@link AnnouncementService#find(int)} method for finding.
     *
     * @param id int announcement id.
     * @return announcement {@link AnnouncementDto}.
     * @throws AnnouncementException if announcement could'nt find.
     */
    @GetMapping(value = "/announcements/{id}")
    public ResponseEntity<AnnouncementDto> get(
            @PathVariable("id") final int id) throws AnnouncementException {
        AnnouncementDto announcementDto = announcementService.find(id);
        if (announcementDto == null) {
            LOGGER.error(LoggerConstants.ANNOUNCEMENT_NOT_FOUND + id);
            throw new AnnouncementException(ExceptionConstant.
                    ANNOUNCEMENT_GET_EXCEPTION + id);
        }
        LOGGER.info(LoggerConstants.ANNOUNCEMENT_FOUND + id);
        return new ResponseEntity<>(announcementDto, HttpStatus.FOUND);
    }


    /**
     * This method takes a {@link AnnouncementDto} object with some id
     * from the client and passes it in
     * {@link AnnouncementService#update(Object)} for updating.
     *
     * @param announcementDto {@link AnnouncementDto}
     *                        with new parameters for setting up.
     * @throws ConstraintViolationException if announcement couldn't update.
     */
    @PutMapping(value = "/announcements")
    public void update(@RequestBody final AnnouncementDto announcementDto) {
        Set<ConstraintViolation<AnnouncementDto>> violations = validator.
                validate(announcementDto);
        if (!violations.isEmpty()) {
            LOGGER.error(violations.toString());
            throw new ConstraintViolationException(violations);
        }
        announcementService.update(announcementDto);
        LOGGER.info(LoggerConstants.ANNOUNCEMENT_UPDATED);
    }


    /**
     * This method takes an ID from the client to delete a specific
     * {@link AnnouncementDto} and passes it to
     * {@link AnnouncementService#delete(int)} for deleting.
     *
     * @param id int.
     */
    @DeleteMapping(value = "/announcements/{id}")
    public void delete(@PathVariable("id") final int id) {
        announcementService.delete(id);
    }


    /**
     * This method takes an ID from the client to deleteAnnouncementById
     * a specific {@link AnnouncementDto}and passes it to
     * {@link AnnouncementService#deleteAnnouncementById(int)} for deleting.
     *
     * @param id int.
     */
    @DeleteMapping(value = "/announcements/{id}/delete-by-id")
    public void deleteAnnouncementById(@PathVariable("id") final int id) {
        announcementService.deleteAnnouncementById(id);
    }


    /**
     * This method returns to the client all the announcements from
     * a particular {@link com.domain.Heading} by the identifier of
     * a particular {@link com.domain.Heading} and passes the heading ID to the
     * {@link AnnouncementService#getAllByHeadingId(int)} for finding.
     *
     * @param id int heading id.
     * @return {@link List<AnnouncementDto>}.
     * @throws AnnouncementException if List<announcementDto> could'nt find.
     */
    @GetMapping(value = "/announcements/{id}/get-by-heading-id")
    public ResponseEntity<List<AnnouncementDto>> getAllAnnouncementByHeadingId(
            @PathVariable("id") final int id)
            throws AnnouncementException {
        List<AnnouncementDto> allByHeadingId = announcementService
                .getAllByHeadingId(id);
        if (allByHeadingId.isEmpty()) {
            LOGGER.error(LoggerConstants.
                    ALL_ANNOUNCEMENTS_BY_HEADING_ID_NOT_FIND + id);
            throw new AnnouncementException(ExceptionConstant.
                    ANNOUNCEMENT_GET_ALL_BY_HEADING_ID_EXCEPTION + id);
        }
        LOGGER.info(LoggerConstants.ALL_ANNOUNCEMENTS_BY_HEADING_ID_FOUND + id);
        return new ResponseEntity<>(allByHeadingId, HttpStatus.FOUND);
    }


    /**
     * This method returns to the client a list of filtered announcements by
     * creation {@link LocalDate} and passes the {@link LocalDate}
     * of the {@link AnnouncementDto} created to the
     * {@link AnnouncementService#filterAllByDate(LocalDate)} for filtering.
     *
     * @param date {@link LocalDate}.
     * @return {@link List<AnnouncementDto>}.
     * @throws AnnouncementException if List<announcement> could'nt find.
     */
    @GetMapping(value = "/announcements/filter-by")
    public ResponseEntity<List<AnnouncementDto>> filterAllAnnounceByDate(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate date)
            throws AnnouncementException {
        List<AnnouncementDto> filtersByDateAll = announcementService.
                filterAllByDate(date);
        if (filtersByDateAll.isEmpty()) {
            LOGGER.error(LoggerConstants.
                    ALL_ANNOUNCEMENTS_BY_DATE_NOT_FIND + date);
            throw new AnnouncementException(ExceptionConstant.
                    ANNOUNCEMENT_GET_ALL_BY_DATE_EXCEPTION + date);
        }
        LOGGER.info(LoggerConstants.ALL_ANNOUNCEMENTS_BY_DATE_FOUND + date);
        return new ResponseEntity<>(filtersByDateAll, HttpStatus.FOUND);
    }


    /**
     * This method returns the client a list of filtered announcements by
     * revelationText and passes the revelationText of the
     * {@link AnnouncementDto} was created to the
     * {@link AnnouncementService#filterAllByRevelationText(String)}.
     *
     * @param revelationText {@link String}.
     * @return {@link List<AnnouncementDto>}.
     * @throws AnnouncementException if List<announcement> could'nt find.
     */
    @GetMapping(value = "/announcements/filter-by-revelation")
    public ResponseEntity<List<AnnouncementDto>>
    filterAllAnnouncementByRevelationTest(
            @RequestParam("text") final String revelationText)
            throws AnnouncementException {
        List<AnnouncementDto> filterAllByRevelationText = announcementService.
                filterAllByRevelationText(revelationText);
        if (filterAllByRevelationText.isEmpty()) {
            LOGGER.error(LoggerConstants.
                    ALL_ANNOUNCEMENTS_BY_REVELATION_TEXT_NOT_FIND
                    + revelationText);
            throw new AnnouncementException(ExceptionConstant.
                    ANNOUNCEMENT_GET_ALL_BY_REVELATION_TEXT_EXCEPTION
                    + revelationText);
        }
        LOGGER.info(LoggerConstants.
                ALL_ANNOUNCEMENTS_BY_REVELATION_TEXT_FOUND
                + revelationText);
        return new ResponseEntity<>(
                filterAllByRevelationText, HttpStatus.FOUND);
    }


    /**
     * This method gets adjusted amount of announcements on a pages and passes
     * the size and page of the {@link AnnouncementDto} was created to the
     * {@link AnnouncementService#showSomeAnnouncementsPagination(int, int)}.
     *
     * @param page int.
     * @param size int.
     * @return {@link List<AnnouncementDto>}.
     * @throws AnnouncementException if List<announcementDto> could'nt find.
     */
    @GetMapping(value = "/announcements/pagination")
    public ResponseEntity<List<AnnouncementDto>>
    getSomeAnnouncementsPagination(@RequestParam("page") final int page,
                                   @RequestParam("size") final int size)
            throws AnnouncementException {
        List<AnnouncementDto> announcementsPagination = announcementService.
                showSomeAnnouncementsPagination(page, size);
        if (announcementsPagination.isEmpty()) {
            LOGGER.error(LoggerConstants.
                    PAGINATION_ANNOUNCEMENTS_NOT_FOUND);
            throw new AnnouncementException(ExceptionConstant
                    .ANNOUNCEMENT_GET_ALL_BY_PAGINATION_EXCEPTION);
        }
        LOGGER.info(LoggerConstants.PAGINATION_ANNOUNCEMENTS_FOUND);
        return new ResponseEntity<>(announcementsPagination, HttpStatus.FOUND);
    }
}
