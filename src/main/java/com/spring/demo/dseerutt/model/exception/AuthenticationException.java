package com.spring.demo.dseerutt.model.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends GenericResponseStatusException {

    public AuthenticationException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public AuthenticationException(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
    }
}
