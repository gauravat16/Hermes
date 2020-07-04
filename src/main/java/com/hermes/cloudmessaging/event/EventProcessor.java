package com.hermes.cloudmessaging.event;

import com.hermes.cloudmessaging.repository.CloudMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EventProcessor {

    @Autowired
    private CloudMessageRepository<Object, Object> cloudMessageRepository;

    @Async
    @EventListener
    public void processNewMessageResponse(NewMessageResponseEvent newMessageResponseEvent) {
        cloudMessageRepository.save(newMessageResponseEvent.getMessageEntity());
    }

}
