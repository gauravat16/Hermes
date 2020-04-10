package com.hermes.cloudmessaging.event;

import com.hermes.cloudmessaging.entity.mongo.MessageEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NewMessageResponseEvent extends ApplicationEvent {
    private MessageEntity<Object, Object> messageEntity;

    public NewMessageResponseEvent(Object source, MessageEntity<Object, Object> messageEntity) {
        super(source);
        this.messageEntity = messageEntity;
    }
}
