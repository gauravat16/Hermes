package com.hermes.cloudmessaging.dto.request;

import com.hermes.cloudmessaging.dto.FCMMessage;
import lombok.Data;

@Data
public class SendMsgRequest {
    private FCMMessage message;
    private FCMRequest fcmRequest;
}
