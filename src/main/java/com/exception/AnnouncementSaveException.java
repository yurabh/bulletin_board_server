package com.exception;

public class AnnouncementSaveException extends Exception {
    public AnnouncementSaveException(StringBuilder message) {
        super(String.valueOf(message));
    }
}
