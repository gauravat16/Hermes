package com.hermes.cloudmessaging.exception;

import org.springframework.http.HttpStatus;

public class DequeueException extends BaseRuntimeException {

    public DequeueException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
