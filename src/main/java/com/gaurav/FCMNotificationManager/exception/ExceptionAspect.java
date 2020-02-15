package com.gaurav.FCMNotificationManager.exception;

import com.gaurav.FCMNotificationManager.dto.BaseResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class ExceptionAspect {

    @ExceptionHandler(value = {BaseException.class})
    private ResponseEntity<BaseResponseDto<String>> handleBaseException(BaseException e) {
        log.debug(e);
        BaseResponseDto baseResponseDto = new BaseResponseDto<>(e.getMessage(), e.getHttpStatus());
        return new ResponseEntity<>(baseResponseDto, e.getHttpStatus());
    }

    @ExceptionHandler(value = {BaseRuntimeException.class})
    private ResponseEntity<BaseResponseDto<BaseResponseDto<String>>> handleBaseException(BaseRuntimeException e) {
        log.debug(e);
        BaseResponseDto baseResponseDto = new BaseResponseDto<>(e.getMessage(), e.getHttpStatus());

        return new ResponseEntity<>(baseResponseDto, e.getHttpStatus());
    }

    @ExceptionHandler(value = {Exception.class})
    private ResponseEntity<BaseResponseDto<BaseResponseDto<String>>> handleBaseException(Exception e) {
        log.debug(e);
        BaseResponseDto baseResponseDto = new BaseResponseDto<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(baseResponseDto, HttpStatus.BAD_REQUEST);
    }

}
