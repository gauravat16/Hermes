package com.hermes.cloudmessaging.core.impl;

import com.hermes.cloudmessaging.model.dto.request.QueueRequest;
import com.hermes.cloudmessaging.service.QueueMessageHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("heart-beat")
public class HeartBeatJavaQueueService extends AbstractJavaQueueService<QueueRequest> {

    public HeartBeatJavaQueueService(List<QueueMessageHandler> queueMessageHandlers) {
        super(queueMessageHandlers);
    }

    @Override
    public QueueRequest.Type getQueryType() {
        return QueueRequest.Type.HEART_BEAT;
    }
}
