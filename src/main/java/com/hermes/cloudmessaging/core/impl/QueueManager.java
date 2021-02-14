package com.hermes.cloudmessaging.core.impl;

import com.hermes.cloudmessaging.model.dto.request.QueueRequest;
import com.hermes.cloudmessaging.service.QueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QueueManager {

    private final QueueService<QueueRequest> messageQueueService;
    private final QueueService<QueueRequest> heartBeatQueueService;


    public QueueManager(@Qualifier("send-message") QueueService<QueueRequest> messageQueueService,
                        @Qualifier("heart-beat") QueueService<QueueRequest> heartBeatQueueService) {
        this.messageQueueService = messageQueueService;
        this.heartBeatQueueService = heartBeatQueueService;
    }


    @Scheduled(cron = "*/5 * * * * *")
    public void scheduleMessageQueueCron() {
        try {
            Object o = messageQueueService.dequeue();
            if(o == null) return;
            log.trace("MessageQueue's Dequeued Message {} ", o);
        } catch (Exception e) {
            log.debug("Failed to process queue", e);
        }
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void scheduleHeartBeatQueueCron() {
        try {
            Object o = heartBeatQueueService.dequeue();
            if(o == null) return;
            log.trace("HeartBeatQueue's Dequeued Message {} ", o);
        } catch (Exception e) {
            log.debug("Failed to process queue", e);
        }
    }
}
