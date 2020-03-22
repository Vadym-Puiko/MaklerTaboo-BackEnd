package com.softserve.maklertaboo.exception.exceptions;

public class BadEmailOrPasswordException extends RuntimeException {

    public BadEmailOrPasswordException() {
        super("Email or password is not valid");
    }
}