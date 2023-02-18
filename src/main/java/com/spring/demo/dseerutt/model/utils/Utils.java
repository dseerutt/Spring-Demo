package com.spring.demo.dseerutt.model.utils;

import com.spring.demo.dseerutt.model.exception.server.DateParsingException;
import com.spring.demo.dseerutt.service.impl.SaleServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    private static final Logger LOGGER = LogManager.getLogger(SaleServiceImpl.class);
    public static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-YYYY", Locale.FRENCH);


    public static Date parseDate(String date) {
        try {
            return DATE_FORMATTER.parse(date);
        } catch (ParseException e) {
            String message = "Failed to parse date " + date;
            LOGGER.error(message, e);
            throw new DateParsingException(message);
        }
    }
}
