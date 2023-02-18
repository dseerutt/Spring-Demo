package com.spring.demo.dseerutt.model.exception.computer;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ComputerStoreNotFoundException extends ResponseStatusException {
    public ComputerStoreNotFoundException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public ComputerStoreNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

    public ComputerStoreNotFoundException(String reason, Throwable cause) {
        super(HttpStatus.NOT_FOUND, reason, cause);
    }
}
