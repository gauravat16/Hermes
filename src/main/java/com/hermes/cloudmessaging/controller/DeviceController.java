package com.hermes.cloudmessaging.controller;

import com.hermes.cloudmessaging.dto.response.BaseResponseDto;
import com.hermes.cloudmessaging.dto.FCMRegistrationResponse;
import com.hermes.cloudmessaging.entity.rdbms.FCMRegistryEntity;
import com.hermes.cloudmessaging.service.DbCRUDService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import com.hermes.cloudmessaging.dto.request.CloudMessageRequest;

import java.util.List;

/**
 * @author gaurav
 */
@Api("")
@RestController
@RequestMapping("device")
public class DeviceController {

    private final DbCRUDService<FCMRegistryEntity, CloudMessageRequest, FCMRegistrationResponse, Long> dbCRUDService;

    public DeviceController(@Qualifier("FCMDbService") DbCRUDService<FCMRegistryEntity, CloudMessageRequest, FCMRegistrationResponse, Long> dbCRUDService) {
        this.dbCRUDService = dbCRUDService;
    }

    @ApiOperation("Record new fcm request")
    @PostMapping("record/fcm-request")

    public BaseResponseDto<FCMRegistrationResponse> recordFCMRequest(@RequestBody CloudMessageRequest registrationRequest) {
        return new BaseResponseDto<>(dbCRUDService.create(registrationRequest));
    }

    @ApiOperation("Update fcm request")
    @PutMapping("update/fcm-request")
    public BaseResponseDto<FCMRegistrationResponse> updateFCMRequest(@RequestBody CloudMessageRequest registrationRequest) {
        return new BaseResponseDto<>(dbCRUDService.update(registrationRequest));
    }

    @ApiOperation("Delete fcm details")
    @DeleteMapping("delete/fcm-request")
    public BaseResponseDto<String> detailsFCMRequest(@RequestBody CloudMessageRequest registrationRequest) {
        dbCRUDService.delete(registrationRequest);
        return new BaseResponseDto<>("Deleted!");
    }

    @ApiOperation("Find fcm details")
    @PostMapping("find/fcm-request")
    public BaseResponseDto<List<FCMRegistrationResponse>> findData(@RequestBody CloudMessageRequest registrationRequest) {
        return new BaseResponseDto<>(dbCRUDService.find(registrationRequest));
    }

}
