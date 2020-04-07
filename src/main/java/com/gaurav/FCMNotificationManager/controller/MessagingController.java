package com.gaurav.FCMNotificationManager.controller;

import com.gaurav.FCMNotificationManager.dto.request.SendMsgRequest;
import com.gaurav.FCMNotificationManager.dto.response.FCMResponse;
import com.gaurav.FCMNotificationManager.service.impl.CloudMessenger;
import com.gaurav.FCMNotificationManager.service.impl.CloudMsgRegistrationDBService;
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

        return dbCRUDService.find(msgRequest.getFcmRequest()).stream().map(e -> {
            msgRequest.getMessage().setTo(e.getCloudMessagingId());
            try {
                return cloudMessenger.sendMessageToUser(msgRequest.getMessage());
            }catch (Exception ex){
                log.debug("Failed to send msg to " + e.getCloudMessagingId() ,ex);
            }
            return null;

        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
