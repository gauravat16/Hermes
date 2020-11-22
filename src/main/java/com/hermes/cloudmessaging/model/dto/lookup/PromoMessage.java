package com.hermes.cloudmessaging.model.dto.lookup;

import com.hermes.cloudmessaging.model.dto.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PromoMessage extends BaseMessage {
    private String isForPro;
    private String isForFree;
    private String fcmNotificationType;
    private String promoStartDate;
    private String promoEndDate;
    private String promoPercentage;
}
