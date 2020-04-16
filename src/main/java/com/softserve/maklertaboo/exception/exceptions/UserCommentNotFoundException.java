package com.softserve.maklertaboo.exception.exceptions;

/**
 * Exception that we get when we try to find some object but such object not exist,
 * then we get {@link UserCommentNotFoundException}.
 *
 * @author Isachenko Dmytro
 */

public class UserCommentNotFoundException extends RuntimeException {
    /**
     * Constructor for UserCommentNotFoundException.
     *
     * @param message - giving message.
     */
    public UserCommentNotFoundException(String message) {
        super(message);
    }
}