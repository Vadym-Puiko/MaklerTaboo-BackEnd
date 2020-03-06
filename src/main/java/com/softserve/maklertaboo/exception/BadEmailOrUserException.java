package com.softserve.maklertaboo.exception;

public class BadEmailOrUserException extends RuntimeException {

    public BadEmailOrUserException(String message) {
        super(message);
    }
}