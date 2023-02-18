package com.spring.demo.dseerutt.model.exception.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ValidationException extends ResponseStatusException {
    public ValidationException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public ValidationException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public ValidationException(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
    }
}
