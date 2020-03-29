package com.gaurav.FCMNotificationManager.controller;

import com.gaurav.FCMNotificationManager.dto.response.BaseResponseDto;
import com.gaurav.FCMNotificationManager.dto.FCMRegistrationResponse;
import com.gaurav.FCMNotificationManager.entity.FCMRegistryEntity;
import com.gaurav.FCMNotificationManager.service.DbCRUDService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import com.gaurav.FCMNotificationManager.dto.request.FCMRequest;

import java.util.List;

/**
 * @author gaurav
 */
@Api("")
@RestController
@RequestMapping("device")
public class DeviceController {

    private final DbCRUDService<FCMRegistryEntity, FCMRequest, FCMRegistrationResponse, Long> dbCRUDService;

    public DeviceController(@Qualifier("FCMDbService") DbCRUDService<FCMRegistryEntity, FCMRequest, FCMRegistrationResponse, Long> dbCRUDService) {
        this.dbCRUDService = dbCRUDService;
    }

    @ApiOperation("Record new fcm request")
    @PostMapping("record/fcm-request")

    public BaseResponseDto<FCMRegistrationResponse> recordFCMRequest(@RequestBody FCMRequest registrationRequest) {
        return new BaseResponseDto<>(dbCRUDService.create(registrationRequest));
    }

    @ApiOperation("Update fcm request")
    @PutMapping("update/fcm-request")
    public BaseResponseDto<FCMRegistrationResponse> updateFCMRequest(@RequestBody FCMRequest registrationRequest) {
        return new BaseResponseDto<>(dbCRUDService.update(registrationRequest));
    }

    @ApiOperation("Delete fcm details")
    @DeleteMapping("delete/fcm-request")
    public BaseResponseDto<String> detailsFCMRequest(@RequestBody FCMRequest registrationRequest) {
        dbCRUDService.delete(registrationRequest);
        return new BaseResponseDto<>("Deleted!");
    }

    @ApiOperation("Find fcm details")
    @PostMapping("find/fcm-request")
    public BaseResponseDto<List<FCMRegistrationResponse>> findData(@RequestBody FCMRequest registrationRequest) {
        return new BaseResponseDto<>(dbCRUDService.find(registrationRequest));
    }

}
