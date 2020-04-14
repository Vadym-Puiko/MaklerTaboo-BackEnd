package com.softserve.maklertaboo.exception.exceptions;

/**
 * Exception that occurs when user enters wrong email or password.
 *
 * @author Roman Blavatskyi
 */
public class BadEmailOrPasswordException extends RuntimeException {

    /**
     * Constructor for {@link BadEmailOrPasswordException}.
     */
    public BadEmailOrPasswordException(String message) {
        super(message);
    }
}