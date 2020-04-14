package com.softserve.maklertaboo.exception.exceptions;

/**
 * Exception that occurs when the user is trying to make a request for the second time.
 *
 * @author Roman Blavatskyi
 */
public class RequestAlreadyExistsException extends RuntimeException {

    /**
     * Constructor for {@link RequestAlreadyExistsException}.
     */
    public RequestAlreadyExistsException(String message) {
        super(message);
    }
}
