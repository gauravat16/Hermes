package com.hermes.cloudmessaging.model.dto.response;

import java.util.Date;
import java.util.UUID;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BaseResponseDto<T> {

    protected T data;

    protected String httpStatus;

    protected String id = UUID.randomUUID().toString();

    protected Date createdAt = new Date();

    public BaseResponseDto(T data, HttpStatus status) {
        this.data = data;
        this.httpStatus = status.value()+"";
    }

    public BaseResponseDto(T data) {
        this.data = data;
        this.httpStatus = HttpStatus.OK.value()+"";
    }


}
