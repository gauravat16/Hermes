package com.gaurav.cloudmessaging.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BaseRuntimeException extends RuntimeException {
    private HttpStatus httpStatus;

    public BaseRuntimeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
