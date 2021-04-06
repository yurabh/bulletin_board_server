package com.controller;

import com.constant.ExceptionConstant;
import com.constant.LoggerConstants;
import com.dto.AuthorDto;
import com.exception.custom_exception.AuthorException;
import com.exception.custom_exception.DuplicateDataException;
import com.service.AuthorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * Class {@link AuthorController} using to perform author operations and
 * save,delete,update and other for user.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@RestController
@RequestMapping("author")
public class AuthorController {


    /**
     * This is field of class {@link Logger}
     * returns a logger for this controller.
     */
    private static final Logger LOGGER = Logger.
            getLogger(AuthorController.class);


    /**
     * Field {@link AuthorService} is object instance
     * of {@link AuthorService} interface with {@link com.domain.Author} type.
     * It connects realization part with user application.
     */
    private final AuthorService authorService;


    /**
     * This field use for validation bean instances.
     * This is object instance of {@link Validator}.
     */
    private Validator validator;


    /**
     * This is a constructor that injects object gain of the
     * {@link AuthorService} into the {@link AuthorController} class.
     *
     * @param serviceAuthor {@link AuthorService}.
     */
    @Autowired
    public AuthorController(
            final AuthorService serviceAuthor) {
        this.authorService = serviceAuthor;
    }


    /**
     * This is method for injecting {@link Validator}.
     * It'll injects class {@link Validator}
     * in controllers such as:
     * {@link AuthorController}
     * and AuthorControllerTest for test cases.
     *
     * @param validatorAuthor {@link Validator}.
     */
    @Autowired
    public void setValidator(final Validator validatorAuthor) {
        this.validator = validatorAuthor;
    }


    /**
     * This is a method that takes a author object from client and
     * passes it to the{@link AuthorService#save(Object)} method save.
     *
     * @param authorDto {@link AuthorDto}.
     * @throws ConstraintViolationException if Author couldn't save.
     * @throws DuplicateDataException       if Author couldn't save.
     */
    @PostMapping(value = "/authors")
    public void save(@RequestBody final AuthorDto authorDto)
            throws DuplicateDataException {
        Set<ConstraintViolation<AuthorDto>> violations = validator.
                validate(authorDto);
        if (!violations.isEmpty()) {
            LOGGER.error(violations.toString());
            throw new ConstraintViolationException(violations);
        }
        try {
            authorService.save(authorDto);
            LOGGER.info(LoggerConstants.AUTHOR_SAVED);
        } catch (Exception e) {
            LOGGER.error(e.getCause().getCause().toString());
            throw new DuplicateDataException(
                    e.getCause().getCause().toString());
        }
    }


    /**
     * This is method which's pass {@link AuthorDto} to the service
     * layer and there generate token and pass it to the client.
     *
     * @param authorDto {@link AuthorDto}.
     * @return responseEntity {@link ResponseEntity}.
     */
    @PostMapping(value = "/authentication")
    public ResponseEntity<String> authentication(
            @RequestBody final AuthorDto authorDto) {
        return ResponseEntity.ok(authorService.authentication(authorDto));
    }


    /**
     * This method returns to the client a certain authorDto on a certain
     * identifier author and passes the id to the
     * {@link AuthorService#find(int)} for finding.
     *
     * @param id int author id.
     * @return authorDto {@link AuthorDto}.
     * @throws AuthorException if {@link com.domain.Author} could'nt find.
     */
    @GetMapping(value = "/authors/{id}")
    public ResponseEntity<AuthorDto> find(
            @PathVariable("id") final int id) throws AuthorException {
        AuthorDto authorDto = authorService.find(id);
        if (authorDto == null) {
            LOGGER.error(LoggerConstants.AUTHOR_NOT_FOUND + id);
            throw new AuthorException(ExceptionConstant
                    .AUTHOR_GET_EXCEPTION + id);
        }
        LOGGER.info(LoggerConstants.AUTHOR_FOUND + id);
        return new ResponseEntity<>(authorDto, HttpStatus.FOUND);
    }


    /**
     * This method takes an authorDto object with some id from the
     * client and passes it in {@link AuthorService#update(Object)}
     * for updating.
     *
     * @param authorDto {@link AuthorDto} with new parameters
     *                  for setting up.
     * @throws ConstraintViolationException if author couldn't update.
     * @throws DuplicateDataException       if author couldn't update.
     */
    @PutMapping(value = "/authors")
    public void update(@RequestBody final AuthorDto authorDto)
            throws DuplicateDataException {
        Set<ConstraintViolation<AuthorDto>> violations = validator.
                validate(authorDto);
        if (!violations.isEmpty()) {
            LOGGER.error(violations.toString());
            throw new ConstraintViolationException(violations);
        }
        try {
            authorService.update(authorDto);
            LOGGER.info(LoggerConstants.AUTHOR_UPDATE);
        } catch (Exception e) {
            LOGGER.error(e.getCause().getCause().toString());
            throw new DuplicateDataException(
                    e.getCause().getCause().toString());
        }
    }


    /**
     * This method takes an ID from the client to delete a specific author
     * and passes it to {@link AuthorService#delete(int)} for deleting.
     *
     * @param id int.
     */
    @DeleteMapping(value = "/authors/{id}")
    public void delete(@PathVariable("id") final int id) {
        authorService.delete(id);
    }


    /**
     * This method takes an ID from the client to
     * deleteAnnouncementsByAuthorId a specific author id and passes it to
     * {@link AuthorService#deleteAnnouncementsByAuthorId(int)} for deleting.
     *
     * @param id int.
     */
    @DeleteMapping(value = "/authors/{id}/delete-announcements-by-author-id")
    public void deleteAnnouncementsByAuthorId(
            @PathVariable("id") final int id) {
        authorService.deleteAnnouncementsByAuthorId(id);
    }
}
