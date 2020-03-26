package com.softserve.maklertaboo.exception.exceptions;

public class UserAlreadyExists extends RuntimeException {

    public UserAlreadyExists(String message) {
        super(message);
    }
}
