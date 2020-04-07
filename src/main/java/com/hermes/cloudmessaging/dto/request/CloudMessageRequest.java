package com.hermes.cloudmessaging.dto.request;

import lombok.Data;

/**
 *
 */
@Data
public class CloudMessageRequest {

	private String deviceName;
	private String osVersion;
	private String appVersion;
	private String cloudMessageId;

}
