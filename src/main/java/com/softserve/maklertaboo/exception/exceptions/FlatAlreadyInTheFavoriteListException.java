package com.softserve.maklertaboo.exception.exceptions;

/**
 * Exception that occurs when user is trying to add flat to the favorite list
 * while this flat is already in that list.
 *
 * @author Roman Blavatskyi
 */
public class FlatAlreadyInTheFavoriteListException extends RuntimeException {

    /**
     * Constructor for {@link FlatAlreadyInTheFavoriteListException}.
     */
    public FlatAlreadyInTheFavoriteListException(String message) {
        super(message);
    }
}
