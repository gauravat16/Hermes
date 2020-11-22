package com.hermes.cloudmessaging.model.dto.request;

import com.hermes.cloudmessaging.model.dto.FCMMessage;
import lombok.Data;

@Data
public class SendMsgRequest {
    private FCMMessage message;
    private CloudMessageRequest cloudMessageRequest;
}
