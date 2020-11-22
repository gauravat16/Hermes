package com.hermes.cloudmessaging.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CloudMessageRequest {

    private String id;
    private String deviceName;
    private String osVersion;
    private String appVersion;
    private String cloudMessageId;
    private String metadata;

}
