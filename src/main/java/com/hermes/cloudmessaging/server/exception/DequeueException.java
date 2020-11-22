package com.hermes.cloudmessaging.server.exception;

import org.springframework.http.HttpStatus;

public class DequeueException extends BaseRuntimeException {

    public DequeueException(String message, HttpStatus httpStatus, boolean logError) {
        super(message, httpStatus, logError);
    }
}
