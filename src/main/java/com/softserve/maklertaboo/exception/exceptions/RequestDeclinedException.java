package com.softserve.maklertaboo.exception.exceptions;

/**
 * Exception that occurs when the user is trying to book apartment
 * one more time while his request was declined
 *
 * @author Roman Blavatskyi
 */
public class RequestDeclinedException extends RuntimeException {

    /**
     * Constructor for {@link RequestDeclinedException}.
     */
    public RequestDeclinedException(String message) {
        super(message);
    }
}
