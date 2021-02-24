package com.exception.custom_exception;

/**
 * This is custom exception {@link DuplicateDataException}.
 */

public class DuplicateDataException extends Exception {

    /**
     * This is constructor for exception
     * {@link DuplicateDataException}.
     *
     * @param message String.
     */
    public DuplicateDataException(final String message) {
        super(message);
    }
}
