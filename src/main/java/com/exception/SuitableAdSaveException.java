package com.exception;

public class SuitableAdSaveException extends Exception {
    public SuitableAdSaveException(StringBuilder message) {
        super(String.valueOf(message));
    }
}
