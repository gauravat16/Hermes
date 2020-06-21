package com.hermes.cloudmessaging.dto.lookup;

import com.hermes.cloudmessaging.dto.BaseMessage;
import lombok.Data;

@Data
public class PromoMessage extends BaseMessage {
    private String isForPro;
    private String isForFree;
    private String fcmNotificationType;
    private String promoStartDate;
    private String promoEndDate;
    private String promoPercentage;
}
