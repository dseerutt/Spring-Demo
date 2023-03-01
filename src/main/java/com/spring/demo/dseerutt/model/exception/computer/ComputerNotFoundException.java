package com.spring.demo.dseerutt.model.exception.computer;

import com.spring.demo.dseerutt.model.exception.GenericResponseStatusException;
import org.springframework.http.HttpStatus;

public class ComputerNotFoundException extends GenericResponseStatusException {

    public ComputerNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

    public ComputerNotFoundException(String reason, Throwable cause) {
        super(HttpStatus.NOT_FOUND, reason, cause);
    }
}
