package com.hermes.cloudmessaging.server.event;

import com.hermes.cloudmessaging.service.repository.CloudMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventProcessor {

    @Autowired
    private CloudMessageRepository<Object, Object> cloudMessageRepository;


    @Async
    @EventListener
    public void processNewMessageResponse(NewMessageResponseEvent newMessageResponseEvent) {
        cloudMessageRepository.save(newMessageResponseEvent.getMessageEntity());
    }

}
