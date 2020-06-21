package com.hermes.cloudmessaging.dto;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Date;

@Builder
@Data
public class FCMRegistrationResponse {


    private String deviceName;
    private String osVersion;
    private String appVersion;
    private String cloudMessagingId;
    private String id;
    private Date createdAt;

}
