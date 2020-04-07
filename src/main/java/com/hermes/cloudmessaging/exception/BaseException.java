package com.hermes.cloudmessaging.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BaseException extends Exception {

    private HttpStatus httpStatus;

    public BaseException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
