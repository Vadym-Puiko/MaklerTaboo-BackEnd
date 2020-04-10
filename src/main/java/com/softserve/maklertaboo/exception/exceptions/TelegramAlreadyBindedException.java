package com.softserve.maklertaboo.exception.exceptions;

public class TelegramAlreadyBindedException extends RuntimeException {
    public TelegramAlreadyBindedException(String message) {
        super(message);
    }
}
