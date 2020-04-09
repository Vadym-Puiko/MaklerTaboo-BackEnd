package com.softserve.maklertaboo.exception.exceptions;

/**
 * Exception that we get when we try deleting some object but such object not exist,
 * then we get {@link UserNotDeletedException}.
 *
 * @author Vadym Puiko
 */
public class UserNotDeletedException extends RuntimeException {
    /**
     * Constructor for NotDeletedException.
     *
     * @param message - giving message.
     */
    public UserNotDeletedException(String message) {
        super(message);
    }
}
