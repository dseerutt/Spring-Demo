package com.spring.demo.dseerutt.model.exception.computer;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ComputerNotFoundException extends ResponseStatusException {
    public ComputerNotFoundException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public ComputerNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

    public ComputerNotFoundException(String reason, Throwable cause) {
        super(HttpStatus.NOT_FOUND, reason, cause);
    }
}
