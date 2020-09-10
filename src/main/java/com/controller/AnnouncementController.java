package com.controller;

import com.domain.Announcement;
import com.domain.Heading;
import com.dto.AnnouncementDto;
import com.exception.*;
import com.service.AnnouncementService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Class {@link AnnouncementController} using to perform announcement operations (save,find,update,delete etc....)for
 * user implementation
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

@RestController
@RequestMapping(value = "announcement")
public class AnnouncementController {


    /**
     * This is field of class {@link Logger} returns a logger for this controller
     */
    private static final Logger LOGGER = Logger.getLogger(AnnouncementController.class);


    /**
     * Field {@link AnnouncementService} is object instance of {@link AnnouncementService} interface with
     * {@link Announcement} type.It connects realization part with user application
     */
    private AnnouncementService announcementService;


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
     * This is a constructor that injects the object gain of the {@link AnnouncementService} class into the
     * {@link AnnouncementController} class
     *
     * @param announcementService {@link AnnouncementService}
     */
    @Autowired
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }


    /**
     * This is a method that takes an {@link AnnouncementDto} object from client and passes it to the
     * {@link AnnouncementService#save(Object)} method save
     *
     * @param announcementDto {@link AnnouncementDto}
     */
    @PostMapping(value = "/announcements")
    public void save(@RequestBody AnnouncementDto announcementDto) {
        Set<ConstraintViolation<AnnouncementDto>> violations = validator.validate(announcementDto);
        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
        announcementService.save(announcementDto.convertingToAnnouncement(announcementDto));
        LOGGER.info("Announcement saved");
    }


    /**
     * This handler only intercepts the exception in the case of an exception in the method
     * {@link AnnouncementController#save(AnnouncementDto)}
     *
     * @param e exception {@link ConstraintViolationException}
     * @return {@link ResponseEntity)} with error message and error status
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> throwExceptionAnnouncementSave(ConstraintViolationException e) {
        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }


    /**
     * This method returns to the client a certain {@link Announcement} on a certain identifier announcement
     * and passes the identifier to the {@link AnnouncementService#find(int)} method for finding
     *
     * @param id int announcement id
     * @return announcement {@link Announcement}
     * @throws AnnouncementFindException if announcement could'nt find
     */
    @GetMapping(value = "/announcements/{id}")
    public ResponseEntity<Announcement> get(@PathVariable("id") int id) throws AnnouncementFindException {
        Announcement announcement = announcementService.find(id);
        if (announcement == null) {
            LOGGER.error("Cannot find announcement by id: " + id);
            throw new AnnouncementFindException("Cannot find announcement by: " + id);
        }
        LOGGER.info("Announcement found by id: " + id);
        return new ResponseEntity<>(announcement, HttpStatus.FOUND);
    }


    /**
     * This handler only intercepts the exception in the case of an exception in the method
     * {@link AnnouncementController#get(int)}
     *
     * @param e exception {@link AnnouncementFindException}
     * @return {@link ResponseEntity} with error message and error status
     */
    @ExceptionHandler(AnnouncementFindException.class)
    public ResponseEntity<String> throwExceptionAnnouncementFind(AnnouncementFindException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
    }


    /**
     * This method takes a {@link AnnouncementDto} object with some id from the client and passes it in
     * {@link AnnouncementService#update(Object)} for updating
     *
     * @param announcementDto {@link AnnouncementDto} with new parameters for setting up
     */
    @PutMapping(value = "/announcements")
    public void update(@RequestBody AnnouncementDto announcementDto) {
        Set<ConstraintViolation<AnnouncementDto>> violations = validator.validate(announcementDto);
        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
        announcementService.update(announcementDto.convertingToAnnouncement(announcementDto));
        LOGGER.info("Announcement updated");
    }


    /**
     * This method takes an ID from the client to delete a specific {@link Announcement} and passes it
     * to {@link AnnouncementService#delete(int)} for deleting
     *
     * @param id int
     */
    @DeleteMapping(value = "/announcements/{id}")
    public void delete(@PathVariable("id") int id) {
        announcementService.delete(id);
    }


    /**
     * This method takes an ID from the client to deleteAnnouncementById a specific {@link Announcement}
     * and passes it to {@link AnnouncementService#deleteAnnouncementById(int)} for deleting
     *
     * @param id int
     */
    @DeleteMapping(value = "/announcements/{id}/delete-by-id")
    public void deleteAnnouncementById(@PathVariable("id") int id) {
        announcementService.deleteAnnouncementById(id);
    }


    /**
     * This method returns to the client all the announcements from a particular {@link Heading} by the identifier of
     * a particular {@link Heading} and passes the heading ID to the
     * {@link AnnouncementService#getAllByHeadingId(int)} for finding
     *
     * @param id int heading id
     * @return {@link List<Announcement>}
     * @throws AnnouncementGetAllByHeadingIdException if List<announcement> could'nt find
     */
    @GetMapping(value = "/announcements/{id}/get-by-heading-id")
    public ResponseEntity<List<Announcement>> getAllAnnouncementByHeadingId(@PathVariable("id") int id)
            throws AnnouncementGetAllByHeadingIdException {
        List<Announcement> allByHeadingId = announcementService.getAllByHeadingId(id);
        if (allByHeadingId.isEmpty()) {
            LOGGER.error("Cannot find announcements by heading id: " + id);
            throw new AnnouncementGetAllByHeadingIdException("Cannot find announcements by heading id: " + id);
        }
        LOGGER.info("Announcements found by heading id: " + id);
        return new ResponseEntity<>(allByHeadingId, HttpStatus.FOUND);
    }


    /**
     * This handler only intercepts the exception in the case of an exception in the method
     * {@link AnnouncementController#getAllAnnouncementByHeadingId(int)}
     *
     * @param e exception {@link AnnouncementGetAllByHeadingIdException}
     * @return {@link ResponseEntity} with error message and error status
     */
    @ExceptionHandler(AnnouncementGetAllByHeadingIdException.class)
    public ResponseEntity<String> throwExceptionGetAllAnnouncementByHeadingId
    (AnnouncementGetAllByHeadingIdException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
    }


    /**
     * This method returns to the client a list of filtered announcements by creation {@link LocalDate} and passes
     * the {@link LocalDate} of the {@link Announcement} created to the
     * {@link AnnouncementService#filterAllByDate(LocalDate)} for filtering
     *
     * @param date {@link LocalDate}
     * @return {@link List<Announcement>}
     * @throws AnnouncementFilterByDateException if List<announcement> could'nt find
     */
    @GetMapping(value = "/announcements/filter-by")
    public ResponseEntity<List<Announcement>> filterAllAnnounceByDate(@RequestParam("date")
                                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                              LocalDate date)
            throws AnnouncementFilterByDateException {
        List<Announcement> filtersByDateAll = announcementService.filterAllByDate(date);
        if (filtersByDateAll.isEmpty()) {
            LOGGER.error("Cannot find announcements by date: " + date);
            throw new AnnouncementFilterByDateException("Cannot find announcements by date: " + date);
        }
        LOGGER.info("Announcements found by date: " + date);
        return new ResponseEntity<>(filtersByDateAll, HttpStatus.FOUND);
    }


    /**
     * This handler only intercepts the exception in the case of an exception in the method
     * {@link AnnouncementController#filterAllAnnounceByDate(LocalDate)}
     *
     * @param e exception {@link AnnouncementFilterByDateException}
     * @return {@link ResponseEntity)} with error message and error status
     */
    @ExceptionHandler(AnnouncementFilterByDateException.class)
    public ResponseEntity<String> throwExceptionFilterAllByDate(AnnouncementFilterByDateException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
    }


    /**
     * This method returns the client a list of filtered announcements by revelationText and passes the revelationText
     * of the {@link Announcement} was created to the {@link AnnouncementService#filterAllByRevelationText(String)}
     *
     * @param revelationText {@link String}
     * @return {@link List<Announcement>}
     * @throws AnnouncementFilterByRevelationTextException if List<announcement> could'nt find
     */
    @GetMapping(value = "/announcements/filter-by-revelation")
    public ResponseEntity<List<Announcement>> filterAllAnnouncementByRevelationTest(@RequestParam("text")
                                                                                            String revelationText)
            throws AnnouncementFilterByRevelationTextException {
        List<Announcement> filterAllByRevelationText = announcementService.filterAllByRevelationText(revelationText);
        if (filterAllByRevelationText.isEmpty()) {
            LOGGER.error("Cannot find announcements by revelationText: " + revelationText);
            throw new AnnouncementFilterByRevelationTextException("Cannot find announcements by revelationText: "
                    + revelationText);
        }
        LOGGER.info("Announcements found by revelationText: " + revelationText);
        return new ResponseEntity<>(filterAllByRevelationText, HttpStatus.FOUND);
    }


    /**
     * This handler only intercepts the exception in the case of an exception in the method
     * {@link AnnouncementController#filterAllAnnouncementByRevelationTest(String)}
     *
     * @param e exception {@link AnnouncementFilterByRevelationTextException}
     * @return {@link ResponseEntity)} with error message and error status
     */
    @ExceptionHandler(AnnouncementFilterByRevelationTextException.class)
    public ResponseEntity<String> throwExceptionFilterAllByRevelationText(AnnouncementFilterByRevelationTextException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
    }


    /**
     * This method gets adjusted amount of announcements on a pages and passes the size and page of the
     * {@link Announcement} was created to the {@link AnnouncementService#showSomeAnnouncementsPagination(int, int)}
     *
     * @param page int
     * @param size int
     * @return {@link List<Announcement>}
     * @throws AnnouncementPaginationException if List<announcement> could'nt find
     */
    @GetMapping(value = "/announcements/pagination")
    public ResponseEntity<List<Announcement>> getSomeAnnouncementsPagination(@RequestParam("page") int page,
                                                                             @RequestParam("size") int size)
            throws AnnouncementPaginationException {
        List<Announcement> announcementsPagination = announcementService.showSomeAnnouncementsPagination(page, size);
        if (announcementsPagination.isEmpty()) {
            LOGGER.error("Cannot pagination some announcements");
            throw new AnnouncementPaginationException("Cannot pagination some announcements");
        }
        LOGGER.info("Pagination went well");
        return new ResponseEntity<>(announcementsPagination, HttpStatus.FOUND);
    }


    /**
     * This handler only intercepts the exception in the case of an exception in the method
     * {@link AnnouncementController#getSomeAnnouncementsPagination(int, int)}
     *
     * @param e exception {@link AnnouncementPaginationException}
     * @return {@link ResponseEntity)} with error message and error status
     */
    @ExceptionHandler(AnnouncementPaginationException.class)
    public ResponseEntity<String> throwExceptionPagination(AnnouncementPaginationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
    }
}
