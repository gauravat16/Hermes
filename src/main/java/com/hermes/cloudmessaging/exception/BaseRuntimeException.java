package com.hermes.cloudmessaging.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseRuntimeException extends RuntimeException {
    private HttpStatus httpStatus;
    private boolean canLogError;

    public BaseRuntimeException(String message, HttpStatus httpStatus, boolean canLogError) {
        super(message);
        this.httpStatus = httpStatus;
        this.canLogError = canLogError;
    }

    public BaseRuntimeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.canLogError = true;
    }

    public boolean canLogError() {
        return canLogError;
    }
}
