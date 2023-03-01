package com.spring.demo.dseerutt.model.exception.computer;

import com.spring.demo.dseerutt.model.exception.GenericResponseStatusException;
import org.springframework.http.HttpStatus;

public class ComputerStoreNotFoundException extends GenericResponseStatusException {

    public ComputerStoreNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

    public ComputerStoreNotFoundException(String reason, Throwable cause) {
        super(HttpStatus.NOT_FOUND, reason, cause);
    }
}
