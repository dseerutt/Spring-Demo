package com.spring.demo.dseerutt.model.exception.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class DateParsingException extends ResponseStatusException {
    public DateParsingException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public DateParsingException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }

    public DateParsingException(String reason, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
    }
}
