package com.hermes.cloudmessaging.exception;

import org.springframework.http.HttpStatus;

public class EnqueueException extends BaseRuntimeException {

    public EnqueueException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
