package com.spring.demo.dseerutt.model.exception.computer;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ComputerAlreadyExistsException extends ResponseStatusException {
    public ComputerAlreadyExistsException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public ComputerAlreadyExistsException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public ComputerAlreadyExistsException(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
    }
}
