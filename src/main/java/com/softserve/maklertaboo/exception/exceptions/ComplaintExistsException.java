package com.softserve.maklertaboo.exception.exceptions;

/**
 * Get exception when we find some object and such object exist,
 * then we get {@link ComplaintExistsException}.
 *
 * @author Isachenko Dmytro
 */
public class ComplaintExistsException extends RuntimeException {
    public ComplaintExistsException(String message) {
        super(message);
    }
}