package com.spring.demo.dseerutt.model.utils;

import com.spring.demo.dseerutt.model.exception.server.DateParsingException;
import com.spring.demo.dseerutt.service.impl.SaleServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class Utils {
    private static final Logger LOGGER = LogManager.getLogger(SaleServiceImpl.class);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    public static LocalDate parseDate(String date) {
        if (date == null) {
            throw new DateParsingException("Date is null, cannot convert it");
        }
        try {
            return LocalDate.parse(date, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new DateParsingException("Failed to parse date " + date);
        }
    }

    public static String getRandomUUIDString() {
        return UUID.randomUUID().toString();
    }
}
