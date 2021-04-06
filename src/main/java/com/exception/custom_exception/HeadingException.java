package com.exception.custom_exception;

/**
 * This is custom exception {@link HeadingException}.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

public class HeadingException extends Exception {

    /**
     * This is constructor for exception
     * {@link HeadingException}.
     *
     * @param message String.
     */
    public HeadingException(final String message) {
        super(message);
    }
}
