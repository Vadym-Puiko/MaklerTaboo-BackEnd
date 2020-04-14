package com.softserve.maklertaboo.exception.exceptions;

/**
 * Exception that occurs when the user is trying to delete favorite flat
 * that doesn't exists.
 *
 * @author Roman Blavatskyi
 */
public class FavoriteFlatNotFoundException extends RuntimeException {


    /**
     * Constructor for {@link FavoriteFlatNotFoundException}.
     */
    public FavoriteFlatNotFoundException(String message) {
        super(message);
    }
}
