package com.controller;

import com.domain.Author;
import com.exception.*;
import com.service.AuthorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.Set;

/**
 * Class {@link AuthorController} using to perform author operations and save,delete,update and other for user
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

@RestController
@RequestMapping("author")
public class AuthorController {


    /**
     * This is field of class {@link Logger} returns a logger for this controller
     */
    private static final Logger LOGGER = Logger.getLogger(AuthorController.class);


    /**
     * Field {@link AuthorService} is object instance of {@link AuthorService}
     * interface with {@link Author} type.
     * It connects realization part with user application
     */
    private AuthorService authorService;


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
     * This is a constructor that injects object gain of the {@link AuthorService}
     * into the {@link AuthorController} class
     *
     * @param authorService {@link AuthorService}
     */
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;

    }


    /**
     * This is a method that takes a author object from client and passes it to the
     * {@link AuthorService#save(Object)} method save
     *
     * @param author {@link Author}
     */
    @PostMapping(value = "/authors")
    public void save(@RequestBody Author author) {
        Set<ConstraintViolation<Author>> violations = validator.validate(author);
        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
        authorService.save(author);
        LOGGER.info("Author saved");
    }


    /**
     * This handler only intercepts the exception in the case of an exception in the method
     * {@link AuthorController#save(Author)}
     *
     * @param e accepts exception {@link ConstraintViolationException}
     * @return {@link ResponseEntity} with error message and error status
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> throwExceptionSaveAuthor(ConstraintViolationException e) {
        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }


    /**
     * This method returns to the client a certain author on a certain identifier author and passes the id to the
     * {@link AuthorService#find(int)} for finding
     *
     * @param id int author id
     * @return author {@link Author}
     * @throws AuthorFindException if {@link Author} could'nt find
     */
    @GetMapping(value = "/authors/{id}")
    public ResponseEntity<Author> find(@PathVariable("id") int id) throws AuthorFindException {
        Author author = authorService.find(id);
        if (author == null) {
            LOGGER.error("Cannot find author by id: " + id);
            throw new AuthorFindException("Cannot find author by id: " + id);
        }
        LOGGER.info("Author found by id: " + id);
        return new ResponseEntity<>(author, HttpStatus.FOUND);
    }


    /**
     * This handler only intercepts the exception in the case of an exception in the method
     * {@link AuthorController#find(int)}
     *
     * @param e exception {@link AuthorFindException}
     * @return {@link ResponseEntity)} with error message and error status
     */
    @ExceptionHandler(AuthorFindException.class)
    public ResponseEntity<String> throwExceptionFindAuthor(AuthorFindException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }


    /**
     * This method takes an author object with some id from the client and passes it in
     * {@link AuthorService#update(Object)} for updating
     *
     * @param author {@link Author} with new parameters for setting up
     */
    @PutMapping(value = "/authors")
    public void update(@RequestBody Author author) {
        Set<ConstraintViolation<Author>> violations = validator.validate(author);
        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
        authorService.update(author);
        LOGGER.info("Author updated");
    }


    /**
     * This method takes an ID from the client to delete a specific author and passes it to
     * {@link AuthorService#delete(int)} for deleting
     *
     * @param id int
     */
    @DeleteMapping(value = "/authors/{id}")
    public void delete(@PathVariable("id") int id) {
        authorService.delete(id);
    }


    /**
     * This method takes an ID from the client to deleteAnnouncementsByAuthorId a specific author id and passes it to
     * {@link AuthorService#deleteAnnouncementsByAuthorId(int)} for deleting
     *
     * @param id int
     */
    @DeleteMapping(value = "/authors/{id}/delete-announcements-by-author-id")
    public void deleteAnnouncementsByAuthorId(@PathVariable("id") int id) {
        authorService.deleteAnnouncementsByAuthorId(id);
    }
}
