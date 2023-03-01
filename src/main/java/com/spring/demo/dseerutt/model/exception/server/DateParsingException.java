package com.spring.demo.dseerutt.model.exception.server;

import com.spring.demo.dseerutt.model.exception.GenericResponseStatusException;
import org.springframework.http.HttpStatus;

public class DateParsingException extends GenericResponseStatusException {

    public DateParsingException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }

    public DateParsingException(String reason, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
    }
}
