package com.exception;

public class HeadingSaveException extends Exception {
    public HeadingSaveException(StringBuilder message) {
        super(String.valueOf(message));
    }
}
