package com.spring.demo.dseerutt.model.exception.computer;

import com.spring.demo.dseerutt.model.exception.GenericResponseStatusException;
import org.springframework.http.HttpStatus;

public class EmptyStoreException extends GenericResponseStatusException {

    public EmptyStoreException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public EmptyStoreException(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
    }
}
