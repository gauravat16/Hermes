package com.hermes.cloudmessaging.core.impl;

import com.hermes.cloudmessaging.model.dto.FCMMessage;
import com.hermes.cloudmessaging.model.dto.FCMRegistrationResponse;
import com.hermes.cloudmessaging.model.dto.request.CloudMessageRequest;
import com.hermes.cloudmessaging.model.dto.request.QueueRequest;
import com.hermes.cloudmessaging.model.dto.request.SendMsgRequest;
import com.hermes.cloudmessaging.model.dto.response.FCMResponse;
import com.hermes.cloudmessaging.model.entity.mongo.CloudMessagingRegistryEntity;
import com.hermes.cloudmessaging.service.DbCRUDService;
import com.hermes.cloudmessaging.service.MessageRequestProcessor;
import com.hermes.cloudmessaging.service.Messenger;
import com.hermes.cloudmessaging.service.QueueMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("sync")
@Slf4j
public class SyncMessageRequestProcessor implements MessageRequestProcessor, QueueMessageHandler {


    private final Messenger<FCMMessage, FCMResponse> cloudMessenger;

    private final DbCRUDService<CloudMessagingRegistryEntity, CloudMessageRequest, FCMRegistrationResponse, Long>
            dbCRUDService;

    public SyncMessageRequestProcessor(@Qualifier("CloudMessenger") Messenger<FCMMessage, FCMResponse> cloudMessenger,
                                       @Qualifier("CloudMsgRegistrationDBService") DbCRUDService<CloudMessagingRegistryEntity,
                                               CloudMessageRequest, FCMRegistrationResponse, Long> dbCRUDService) {
        this.cloudMessenger = cloudMessenger;
        this.dbCRUDService = dbCRUDService;
    }

    @Override
    public List<FCMResponse> process(SendMsgRequest sendMsgRequest) {
        return dbCRUDService.find(sendMsgRequest.getCloudMessageRequest()).stream().map(e -> {
            sendMsgRequest.getMessage().setTo(e.getCloudMessagingId());
            return cloudMessenger.sendMessageToUser(sendMsgRequest.getMessage());
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public void handleRequest(Object request) {
        if (request instanceof SendMsgRequest) {
            SendMsgRequest sendMsgRequest = (SendMsgRequest) request;
            List<FCMResponse> fcmResponses = process(sendMsgRequest);
            log.debug("fcmResponses {}", fcmResponses);
        }
    }

    @Override
    public QueueRequest.Type getQueueRequestType() {
        return QueueRequest.Type.SEND_MESSAGE;
    }
}
