package com.gaurav.FCMNotificationManager.controller;

import com.gaurav.FCMNotificationManager.dto.BaseResponseDto;
import com.gaurav.FCMNotificationManager.dto.FCMRegistrationResponse;
import com.gaurav.FCMNotificationManager.entity.FCMRegistryEntity;
import com.gaurav.FCMNotificationManager.service.DbCRUDService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.FCMNotificationManager.dto.FCMRegistrationRequest;

/**
 * @author gaurav
 */
@Api("")
@RestController
@RequestMapping("device")
public class DeviceController {

    @Autowired
    @Qualifier("FCMDbService")
    private DbCRUDService<FCMRegistryEntity, FCMRegistrationRequest, FCMRegistrationResponse, Long> dbCRUDService;


    @ApiOperation("Record new fcm request")
    @PostMapping("record/fcm-request")

    public BaseResponseDto<FCMRegistrationResponse> recordFCMRequest(@RequestBody FCMRegistrationRequest registrationRequest) {
        return new BaseResponseDto<>(dbCRUDService.create(registrationRequest));
    }

    @ApiOperation("Update fcm request")
    @PostMapping("update/fcm-request")
    public BaseResponseDto<FCMRegistrationResponse> updateFCMRequest(@RequestBody FCMRegistrationRequest registrationRequest) {
        return new BaseResponseDto<>(dbCRUDService.update(registrationRequest));
    }

    @ApiOperation("Delete fcm details")
    @PostMapping("delete/fcm-request")
    public BaseResponseDto<String> detailsFCMRequest(@RequestBody FCMRegistrationRequest registrationRequest) {
        dbCRUDService.delete(registrationRequest);
        return new BaseResponseDto<>("Deleted!");
    }

}
