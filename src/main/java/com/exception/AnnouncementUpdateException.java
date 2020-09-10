package com.exception;

public class AnnouncementUpdateException extends Exception {
    public AnnouncementUpdateException(StringBuilder message) {
        super(String.valueOf(message));
    }
}
