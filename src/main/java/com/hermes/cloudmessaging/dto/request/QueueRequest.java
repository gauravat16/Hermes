package com.hermes.cloudmessaging.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class QueueRequest {

    @Getter
    public enum Type {
        HEART_BEAT(CloudMessageRequest.class);
        private final Class<?> clazz;

        Type(Class<?> clazz) {
            this.clazz = clazz;
        }
    }

    private Type type;
    private Object request;

}
