package com.softserve.maklertaboo.exception.exceptions;

/**
 * Exception that occurs when the user is trying to manipulate
 * with requests of flat booking.
 *
 * @author Roman Blavatskyi
 */
public class RequestForFlatBookingException extends RuntimeException {

    /**
     * Constructor for {@link RequestForFlatBookingException}.
     */
    public RequestForFlatBookingException(String message) {
        super(message);
    }
}
