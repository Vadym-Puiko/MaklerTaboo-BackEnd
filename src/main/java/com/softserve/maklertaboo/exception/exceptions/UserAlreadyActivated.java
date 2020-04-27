package com.softserve.maklertaboo.exception.exceptions;

public class UserAlreadyActivated extends RuntimeException {

    public UserAlreadyActivated(String message) {
        super(message);
    }
}
