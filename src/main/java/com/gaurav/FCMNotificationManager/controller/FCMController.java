package com.gaurav.FCMNotificationManager.controller;

import com.gaurav.FCMNotificationManager.dto.request.SendMsgRequest;
import com.gaurav.FCMNotificationManager.dto.response.FCMResponse;
import com.gaurav.FCMNotificationManager.service.impl.FCMMessenger;
import com.gaurav.FCMNotificationManager.service.impl.FCMRegistrationDBService;
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
@RequestMapping("messaging/fcm")
public class FCMController {

    @Autowired
    private FCMMessenger fcmMessenger;

    @Autowired
    private FCMRegistrationDBService dbCRUDService;

    @PostMapping("/send_message")
    public List<FCMResponse> sendMessage(@RequestBody SendMsgRequest msgRequest) {

        return dbCRUDService.find(msgRequest.getFcmRequest()).stream().map(e -> {
            msgRequest.getMessage().setTo(e.getFcmId());
            try {
                return fcmMessenger.sendMessageToUser(msgRequest.getMessage());
            }catch (Exception ex){
                log.debug("Failed to send msg to " + e.getFcmId() ,ex);
            }
            return null;

        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
