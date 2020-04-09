package com.softserve.maklertaboo.exception.exceptions;

/**
 * Exception that we get when user trying to add place or left comment that is blocked.
 *
 * @author Vadym Puiko
 */
public class UserBlockedException extends RuntimeException {
    /**
     * Constructor for UserBlockedException.
     */
    public UserBlockedException(String message) {
        super(message);
    }
}
