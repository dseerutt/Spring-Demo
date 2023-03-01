package com.spring.demo.dseerutt.model.exception.computer;

import com.spring.demo.dseerutt.model.exception.GenericResponseStatusException;
import org.springframework.http.HttpStatus;

public class ComputerAlreadyExistsException extends GenericResponseStatusException {

    public ComputerAlreadyExistsException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public ComputerAlreadyExistsException(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
    }
}
