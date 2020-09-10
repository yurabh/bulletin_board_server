package com.exception;

public class AuthorSaveException extends Exception {
    public AuthorSaveException(StringBuilder message) {
        super(String.valueOf(message));
    }
}
