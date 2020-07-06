package com.hermes.cloudmessaging.service.impl;

import com.hermes.cloudmessaging.dto.request.CloudMessageRequest;
import org.springframework.stereotype.Service;

@Service(value = "java-CloudMessageRequest")
public class JHeartBeatQueueService extends JavaQueueService<CloudMessageRequest> {
}
