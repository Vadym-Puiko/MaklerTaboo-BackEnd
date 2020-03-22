package com.softserve.maklertaboo.exception.exceptions;

public class BadEmailOrPasswordException extends RuntimeException {

    public BadEmailOrPasswordException(String message) {
        super(message);
    }
}