package com.softserve.maklertaboo.exception;

public class BadEmailOrPasswordException extends RuntimeException {

    public BadEmailOrPasswordException(String message) {
        super(message);
    }
}