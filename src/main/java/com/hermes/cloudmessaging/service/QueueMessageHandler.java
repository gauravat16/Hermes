package com.hermes.cloudmessaging.service;

import com.hermes.cloudmessaging.dto.request.QueueRequest;

public interface QueueMessageHandler {

    void handleRequest(Object request);

    QueueRequest.Type getQueueRequestType();
}
