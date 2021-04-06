package com.exception.custom_exception;

/**
 * This is custom exception {@link SuitableAdException}.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

public class SuitableAdException extends Exception {

    /**
     * This is constructor for exception
     * {@link SuitableAdException}.
     *
     * @param message String.
     */
    public SuitableAdException(final String message) {
        super(message);
    }
}
