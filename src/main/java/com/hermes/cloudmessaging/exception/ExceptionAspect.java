package com.hermes.cloudmessaging.exception;

import com.hermes.cloudmessaging.dto.response.BaseResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionAspect {

    @ExceptionHandler(value = {BaseException.class})
    private ResponseEntity<BaseResponseDto<String>> handleBaseException(BaseException e) {
        log.debug(e.getMessage(), e);
        BaseResponseDto<String> baseResponseDto = new BaseResponseDto<>(e.getMessage(), e.getHttpStatus());
        return new ResponseEntity<>(baseResponseDto, e.getHttpStatus());
    }

    @ExceptionHandler(value = {BaseRuntimeException.class})
    private ResponseEntity<BaseResponseDto<String>> handleBaseException(BaseRuntimeException e) {
        if (e.canLogError()) {
            log.debug(e.getMessage(), e);
        }
        BaseResponseDto<String> baseResponseDto = new BaseResponseDto<>(e.getMessage(), e.getHttpStatus());

        return new ResponseEntity<>(baseResponseDto, e.getHttpStatus());
    }

    @ExceptionHandler(value = {Exception.class})
    private ResponseEntity<BaseResponseDto<String>> handleBaseException(Exception e) {
        log.debug(e.getMessage(), e);
        BaseResponseDto<String> baseResponseDto = new BaseResponseDto<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(baseResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    private ResponseEntity<BaseResponseDto<String>> handleBaseException(RuntimeException e) {
        log.debug(e.getMessage(), e);
        BaseResponseDto<String> baseResponseDto = new BaseResponseDto<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(baseResponseDto, HttpStatus.BAD_REQUEST);
    }

}
