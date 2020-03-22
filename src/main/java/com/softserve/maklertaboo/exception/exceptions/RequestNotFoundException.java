package com.softserve.maklertaboo.exception.exceptions;

public class RequestNotFoundException extends RuntimeException {
    public RequestNotFoundException(Long id) {
        super("Request with following id not found: " + id);
    }
}
