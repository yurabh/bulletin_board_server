package com.exception.handler;

import com.exception.custom_exception.AnnouncementException;
import com.exception.custom_exception.AuthorException;
import com.exception.custom_exception.DuplicateDataException;
import com.exception.custom_exception.HeadingException;
import com.exception.custom_exception.SuitableAdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * This is {@link CustomExceptionHandler} class which contains
 * some exception handler for {@link com.controller.AnnouncementController}
 * {@link com.controller.SuitableAdController}
 * {@link com.controller.HeadingController}
 * {@link com.controller.AuthorController} classes.
 *
 * @author Yuriy Bahlay.
 */

@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * This handler only intercepts the exception in the case of an exception
     * in the methods of such as classes
     * {@link com.controller.AnnouncementController
     * #save(com.dto.AnnouncementDto)}
     * {@link com.controller.AuthorController#save(com.dto.AuthorDto)}
     * {@link com.controller.HeadingController#save(com.dto.HeadingDto)}
     * {@link com.controller.SuitableAdController#save(com.dto.SuitableAdDto)}.
     *
     * @param e exception {@link ConstraintViolationException}
     * @return {@link ResponseEntity} with error message and error status.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> throwExceptionSaveOrUpdate(
            final ConstraintViolationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }


    /**
     * This handler intercepts the exception in the case
     * of an exception in the methods of controllers such as:
     * for all methods in this controller.
     *
     * @param e {@link AnnouncementException}.
     * @return {@link ResponseEntity} with error message and error status.
     */
    @ExceptionHandler(AnnouncementException.class)
    public ResponseEntity<String> throwAnnouncementExceptionForFindingResources(
            final AnnouncementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
    }


    /**
     * This handler only intercepts the exception in the case
     * of an exception in the method
     * {@link com.controller.AuthorController#find(int)}.
     *
     * @param e exception {@link AuthorException}.
     * @return {@link ResponseEntity)} with error message and error status.
     */
    @ExceptionHandler(value = AuthorException.class)
    public ResponseEntity<String> throwExceptionFindAuthor(
            final AuthorException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }


    /**
     * This handler only intercepts the exception in the case
     * of an exception in the method
     * {@link com.controller.HeadingController#get(int)}.
     *
     * @param e exception {@link HeadingException}.
     * @return {@link ResponseEntity} with error message and error status.
     */
    @ExceptionHandler(HeadingException.class)
    public ResponseEntity<String> throwExceptionHeadingFind(
            final HeadingException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
    }


    /**
     * This handler only intercepts the exception in the case
     * of an exception in the method
     * {@link com.controller.SuitableAdController#get(int)}.
     *
     * @param e exception {@link SuitableAdException}.
     * @return {@link ResponseEntity} with error message and error status.
     */
    @ExceptionHandler(SuitableAdException.class)
    public ResponseEntity<String> throwExceptionSuitableAdFind(
            final SuitableAdException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
    }


    /**
     * This handler only intercepts the exception in the case
     * of an exception in the method
     * {@link com.controller.AuthorController#save(com.dto.AuthorDto)}.
     *
     * @param e exception {@link DuplicateDataException}.
     * @return {@link ResponseEntity} with error message and error status.
     */
    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<String>
    throwExceptionSaveAuthorWithDuplicateException(
            final DuplicateDataException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
