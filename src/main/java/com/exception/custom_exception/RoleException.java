package com.exception.custom_exception;

/**
 * This is custom exception {@link RoleException}.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

public class RoleException extends Exception {

    /**
     * This is constructor for exception
     * {@link RoleException}.
     *
     * @param message String.
     */
    public RoleException(final String message) {
        super(message);
    }
}
