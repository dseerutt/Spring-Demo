package com.spring.demo.dseerutt.model.utils;

import com.spring.demo.dseerutt.model.exception.server.DateParsingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    /**
     * parseDate in nominal case, localDate is properly created
     */
    @Test
    void parseDateOkTest() {
        LocalDate localDate = LocalDate.of(2023, 12, 10);
        assertEquals(localDate, Utils.parseDate("10-12-2023"));
    }

    /**
     * parseDate in parsing error case, DateParsingException is thrown
     */
    @Test
    void parseDateConversionExceptionTest() {
        String wrongDate = "aaaee";
        DateParsingException exception = assertThrows(
                DateParsingException.class,
                () -> Utils.parseDate(wrongDate),
                "Should throw DateParsingException");
        assertEquals("Failed to parse date %s".formatted(wrongDate), exception.getReason());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
    }

    /**
     * parseDate in null error case, DateParsingException is thrown
     */
    @Test
    void parseNullDateConversionExceptionTest() {
        DateParsingException exception = assertThrows(
                DateParsingException.class,
                () -> Utils.parseDate(null),
                "Should throw DateParsingException");
        assertEquals("Date is null, cannot convert it", exception.getReason());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
    }


    /**
     * getRandomUUIDString test - check string length only as it's random
     */
    @Test
    void getRandomUUIDStringTest() {
        String uuid = Utils.getRandomUUIDString();
        assertNotNull(uuid);
        assertEquals(36, uuid.length());
    }

}