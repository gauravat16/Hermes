package com.hermes.cloudmessaging.controller;

import com.hermes.cloudmessaging.dto.request.SendMsgRequest;
import com.hermes.cloudmessaging.dto.response.FCMResponse;
import com.hermes.cloudmessaging.service.impl.CloudMessenger;
import com.hermes.cloudmessaging.service.impl.CloudMsgRegistrationDBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CloudMessenger cloudMessenger;

    @Autowired
    private CloudMsgRegistrationDBService dbCRUDService;

    @PostMapping("/new-message")
    public List<FCMResponse> sendMessage(@RequestBody SendMsgRequest msgRequest) {


        return dbCRUDService.find(msgRequest.getCloudMessageRequest()).stream().map(e -> {
            msgRequest.getMessage().setTo(e.getCloudMessagingId());
            return cloudMessenger.sendMessageToUser(msgRequest.getMessage());
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
