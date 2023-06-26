package com.test.test.exception.apiexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiRequestException extends RuntimeException {

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
