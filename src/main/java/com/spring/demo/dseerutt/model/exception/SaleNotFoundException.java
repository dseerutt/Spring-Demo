package com.spring.demo.dseerutt.model.exception;

import org.springframework.http.HttpStatus;

public class SaleNotFoundException extends GenericResponseStatusException {

    public SaleNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

    public SaleNotFoundException(String reason, Throwable cause) {
        super(HttpStatus.NOT_FOUND, reason, cause);
    }
}
