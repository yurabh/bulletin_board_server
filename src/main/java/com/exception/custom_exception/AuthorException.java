package com.exception.custom_exception;

/**
 * This is custom exception {@link AuthorException}.
 */

public class AuthorException extends Exception {

    /**
     * This is constructor for exception
     * {@link AuthorException}.
     *
     * @param message String.
     */
    public AuthorException(final String message) {
        super(message);
    }
}
