package com.softserve.maklertaboo.constant;

public class ErrorMessage {

    public static final String BAD_EMAIL_OR_PASSWORD = "Email or password is not valid";
    public static final String REQUEST_NOT_FOUND = "Request with following id not found: ";
    public static final String FLAT_NOT_FOUND = "Flat not found";
    public static final String FAVORITE_FLAT_NOT_FOUND = "Favorite flat not found";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_NOT_FOUND_BY_ID = "User with following id not found: ";
    public static final String USER_NOT_FOUND_BY_USERNAME = "User with following username not found: ";
    public static final String INCORRECT_DATA = "Bad email or password";
    public static final String REFRESH_TOKEN_NOT_VALID = "Refresh token is not valid";
    public static final String USER_ALREADY_EXISTS = "User already exists with given credentials";
    public static final String DUPLICATE_RENTER_REQUEST = "Renter request successfully sent pending approval";
    public static final String DUPLICATE_LANDLORD_REQUEST = "Landlord request successfully sent, wait for approval";
    public static final String FLAT_ALREADY_IN_THE_FAVORITE_LIST = "Flat already exists in the favorite list";
    public static final String FLAT_NOT_FOUND_BY_ID = "Flat with following id not found: ";
    public static final String IS_NOT_OWNER = "User is not owner of current flat";
    public static final String JSON_PARSING_EXCEPTION = "Cannot parse json";
    public static final String TELEGRAM_BINDED_MESSAGE = "Telegram is already binded to this account";
    public static final String EMAIL_SENDING_ERROR = "Email sending error";
    public static final String UPDATE_USER_ERROR = "User tried updating some fields but such user not exist";
    public static final String DELETE_USER_ERROR = "User tried deleting some user but such object not exist";
    public static final String PASSWORDS_DO_NOT_MATCHES = "Password do not matches";
    public static final String CURRENT_PASSWORD_DOES_NOT_MATCH = "Current password is wrong";
    public static final String REQUEST_FOR_FLAT_BOOKING_ALREADY_EXISTS = "You've already sent request to book apartment. " +
            "Wait for approval.";
    public static final String ACCESS_DENIED_TO_BOOK_APARTMENT = "You need to fill passport data in order to book apartment!";
    public static final String REQUEST_FOR_FLAT_BOOKING_NOT_FOUND = "Request for flat booking not found.";


}
