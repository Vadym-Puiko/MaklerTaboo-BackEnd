package com.softserve.maklertaboo.exception.exceptions;

/**
 * Exception that we get when we try updating some object but such object not exist,
 * then we get {@link UserNotUpdatedException}.
 *
 * @author Vadym Puiko
 */
public class UserNotUpdatedException extends RuntimeException {
    /**
     * Constructor for NotUpdatedException.
     *
     * @param message - giving message.
     */
    public UserNotUpdatedException(String message) {
        super(message);
    }
}
