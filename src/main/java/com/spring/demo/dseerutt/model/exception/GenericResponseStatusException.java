package com.spring.demo.dseerutt.model.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GenericResponseStatusException extends ResponseStatusException {
    private static final Logger LOGGER = LogManager.getLogger(GenericResponseStatusException.class);

    public GenericResponseStatusException(HttpStatus httpStatus, String reason) {
        super(httpStatus, reason);
        LOGGER.error(reason);
    }

    public GenericResponseStatusException(HttpStatus httpStatus, String reason, Throwable cause) {
        super(httpStatus, reason, cause);
        LOGGER.error(reason, cause);
    }
}
