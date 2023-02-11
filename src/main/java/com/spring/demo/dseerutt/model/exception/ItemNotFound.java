package com.spring.demo.dseerutt.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ItemNotFound extends ResponseStatusException {
    public ItemNotFound(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public ItemNotFound(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public ItemNotFound(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
    }
}
