package com.spring.demo.dseerutt.model.exception.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class DatabaseException extends ResponseStatusException {
    public DatabaseException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public DatabaseException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }

    public DatabaseException(String reason, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
    }
}
