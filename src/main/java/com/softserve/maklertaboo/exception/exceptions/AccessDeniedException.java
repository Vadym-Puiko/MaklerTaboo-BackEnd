package com.softserve.maklertaboo.exception.exceptions;

/**
 * Exception that occurs when the user is trying to access functionality
 * of the application with no rights.
 *
 * @author Roman Blavatskyi
 */
public class AccessDeniedException extends RuntimeException {

    /**
     * Constructor for {@link AccessDeniedException}.
     */
    public AccessDeniedException(String message) {
        super(message);
    }
}
