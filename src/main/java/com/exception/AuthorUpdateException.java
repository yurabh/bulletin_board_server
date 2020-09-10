package com.exception;

public class AuthorUpdateException extends Exception {
    public AuthorUpdateException(StringBuilder message) {
        super(String.valueOf(message));
    }
}
