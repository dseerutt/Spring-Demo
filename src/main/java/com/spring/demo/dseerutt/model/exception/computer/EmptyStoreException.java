package com.spring.demo.dseerutt.model.exception.computer;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class EmptyStoreException extends ResponseStatusException {
    public EmptyStoreException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public EmptyStoreException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public EmptyStoreException(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
    }
}
