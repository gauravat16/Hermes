package com.hermes.cloudmessaging.server.controller;

import com.hermes.cloudmessaging.model.dto.FCMRegistrationResponse;
import com.hermes.cloudmessaging.model.dto.request.CloudMessageRequest;
import com.hermes.cloudmessaging.model.dto.request.QueueRequest;
import com.hermes.cloudmessaging.model.dto.response.BaseResponseDto;
import com.hermes.cloudmessaging.service.QueueService;
import com.hermes.cloudmessaging.core.impl.CloudMsgRegistrationDBService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gaurav
 */
@Api("")
@RestController
@RequestMapping("device")
@Log4j2
public class DeviceController {

    private final CloudMsgRegistrationDBService dbCRUDService;


    private final QueueService<QueueRequest> queueService;

    public DeviceController(CloudMsgRegistrationDBService dbCRUDService,
                            QueueService<QueueRequest> queueService) {
        this.dbCRUDService = dbCRUDService;
        this.queueService = queueService;
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

    @ApiOperation("Send heart beat")
    @PostMapping("heart-beat")
    public BaseResponseDto<String> heartBeat(@RequestBody CloudMessageRequest registrationRequest) {
        log.debug("Request for heartbeat {}", registrationRequest);
        queueService.enqueue(new QueueRequest(QueueRequest.Type.HEART_BEAT, registrationRequest));
        return new BaseResponseDto<>("Received");
    }


}
