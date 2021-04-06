package com.exception.custom_exception;

/**
 * This is custom exception {@link AnnouncementException}.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

public class AnnouncementException extends Exception {

    /**
     * This is constructor for exception
     * {@link AnnouncementException}.
     *
     * @param message String.
     */
    public AnnouncementException(final String message) {
        super(message);
    }
}
