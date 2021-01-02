package com.hermes.cloudmessaging.server.controller;

import com.hermes.cloudmessaging.model.entity.mongo.CloudMessagingRegistryEntity;
import com.hermes.cloudmessaging.model.dto.FCMMessage;
import com.hermes.cloudmessaging.model.dto.FCMRegistrationResponse;
import com.hermes.cloudmessaging.model.dto.request.CloudMessageRequest;
import com.hermes.cloudmessaging.model.dto.request.SendMsgRequest;
import com.hermes.cloudmessaging.model.dto.response.FCMResponse;
import com.hermes.cloudmessaging.service.DbCRUDService;
import com.hermes.cloudmessaging.service.Messenger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("cloud/messaging")
public class MessagingController {

    @Autowired
    @Qualifier("CloudMessenger")
    private Messenger<FCMMessage, FCMResponse> cloudMessenger;

    @Autowired
    @Qualifier("CloudMsgRegistrationDBService")
    private DbCRUDService<CloudMessagingRegistryEntity, CloudMessageRequest, FCMRegistrationResponse, Long>
            dbCRUDService;

    @PostMapping("/new-message")
    public List<FCMResponse> sendMessage(@RequestBody SendMsgRequest msgRequest) {

        return dbCRUDService.find(msgRequest.getCloudMessageRequest()).stream().map(e -> {
            msgRequest.getMessage().setTo(e.getCloudMessagingId());
            return cloudMessenger.sendMessageToUser(msgRequest.getMessage());
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
