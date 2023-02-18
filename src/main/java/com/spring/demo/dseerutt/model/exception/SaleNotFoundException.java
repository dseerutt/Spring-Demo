package com.spring.demo.dseerutt.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class SaleNotFoundException extends ResponseStatusException {
    public SaleNotFoundException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public SaleNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

    public SaleNotFoundException(String reason, Throwable cause) {
        super(HttpStatus.NOT_FOUND, reason, cause);
    }
}
