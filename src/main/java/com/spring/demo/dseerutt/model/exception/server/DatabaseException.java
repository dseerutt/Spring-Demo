package com.spring.demo.dseerutt.model.exception.server;

import com.spring.demo.dseerutt.model.exception.GenericResponseStatusException;
import org.springframework.http.HttpStatus;

public class DatabaseException extends GenericResponseStatusException {

    public DatabaseException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }

    public DatabaseException(String reason, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
    }
}
