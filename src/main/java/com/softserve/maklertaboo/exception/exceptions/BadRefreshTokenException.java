package com.softserve.maklertaboo.exception.exceptions;

public class BadRefreshTokenException extends RuntimeException{

    public BadRefreshTokenException(String message) {
        super(message);
    }
}
