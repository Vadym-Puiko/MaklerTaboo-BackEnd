package com.softserve.maklertaboo.exception.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("User with following id not found: " + id);
    }
}
