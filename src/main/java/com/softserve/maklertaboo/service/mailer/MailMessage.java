package com.softserve.maklertaboo.service.mailer;

public class MailMessage {
    public static final String MAIL_HEADER = "New Flats upcoming! Check our service";
    public static final String MAIL_BODY = "Some new flats with your criterias has been added";
    public static final String AGREEMENT_HEADER = "Rental Agreement";
    public static final String AGREEMENT_BODY = "Dear customer,\n\nYou have " +
            "successfully created rental agreement. It is attached bellow.\n" +
            "Please, download your agreement and finish all necessary steps " +
            "of renting process.\n\nBest regards,\nMaklerTaboo service.";
    public static final String CONGRATS = "Dear %s,\n";
    public static final String VERIFY_EMAIL_SUBJECT = "Maklertaboo — Confirm registration";
    public static final String VERIFY_EMAIL_BODY = "\nPlease verify your email address to complete registration to 'Maklertaboo'." +
            "\nTo verify your account just follow the link: %s\n" +
            "This link will expire in 24 hours.";
    public static final String RESEND_TOKEN_BODY = "\n\nTo get another link for " +
            "registration follow link:\n";
    public static final String SIGN = "\nBest regards,\nMaklertaboo";

}
