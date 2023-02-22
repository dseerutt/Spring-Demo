package com.spring.demo.dseerutt.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class AuthenticationException extends ResponseStatusException {
    public AuthenticationException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public AuthenticationException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public AuthenticationException(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
    }
}
