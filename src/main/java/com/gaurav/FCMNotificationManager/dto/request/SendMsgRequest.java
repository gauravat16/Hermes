package com.gaurav.FCMNotificationManager.dto.request;

import com.gaurav.FCMNotificationManager.dto.FCMMessage;
import lombok.Data;

@Data
public class SendMsgRequest {
    private FCMMessage message;
    private FCMRequest fcmRequest;
}
