package com.softserve.maklertaboo.exception.exceptions;


/**
 * Exception that we get when we try to find some object but such object not exist,
 * then we get {@link FlatCommentNotFoundException}.
 *
 * @author Isachenko Dmytro
 */
public class FlatCommentNotFoundException extends RuntimeException {
    /**
     * Constructor for FlatCommentNotFoundException.
     *
     * @param message - giving message.
     */
    public FlatCommentNotFoundException(String message) {
        super(message);
    }
}
