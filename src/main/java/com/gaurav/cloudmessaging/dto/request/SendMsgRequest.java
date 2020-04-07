package com.gaurav.cloudmessaging.dto.request;

import com.gaurav.cloudmessaging.dto.FCMMessage;
import lombok.Data;

@Data
public class SendMsgRequest {
    private FCMMessage message;
    private FCMRequest fcmRequest;
}
