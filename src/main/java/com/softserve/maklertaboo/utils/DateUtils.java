package com.softserve.maklertaboo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtils {

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.now());
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static int monthsBetween(Date from, Date to) {
        return (int) ChronoUnit.MONTHS.between(asLocalDate(from), asLocalDate(to));
    }


    public static Date asDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date date1 = null;
        try {
            date1 = formatter.parse(date);
        } catch (ParseException e) {

        }
        return date1;
    }
}
