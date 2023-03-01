package com.spring.demo.dseerutt.model.utils;

import com.spring.demo.dseerutt.model.exception.server.DateParsingException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class Utils {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String NULL_DATE_MESSAGE = "Date is null, cannot convert it";
    private static final String DATE_PARSING_FAILURE_MESSAGE = "Failed to parse date %s";


    public static LocalDate parseDate(String date) {
        if (date == null) {
            throw new DateParsingException(NULL_DATE_MESSAGE);
        }
        try {
            return LocalDate.parse(date, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new DateParsingException(DATE_PARSING_FAILURE_MESSAGE.formatted(date));
        }
    }

    public static String getRandomUUIDString() {
        return UUID.randomUUID().toString();
    }
}
