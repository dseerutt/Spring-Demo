package com.spring.demo.dseerutt.model.exception.server;

import com.spring.demo.dseerutt.model.exception.GenericResponseStatusException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

public class ValidationException extends GenericResponseStatusException {
    private static final Logger LOGGER = LogManager.getLogger(ValidationException.class);

    public ValidationException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
        LOGGER.error(reason);
    }

    public ValidationException(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
        LOGGER.error(reason, cause);
    }
}
