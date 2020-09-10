package com.exception;

public class SuitableAdUpdateException extends Exception {
    public SuitableAdUpdateException(StringBuilder message) {
        super(String.valueOf(message));
    }
}
