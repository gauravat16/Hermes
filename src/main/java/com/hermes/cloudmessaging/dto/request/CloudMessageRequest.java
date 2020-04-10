package com.hermes.cloudmessaging.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloudMessageRequest {

	private String deviceName;
	private String osVersion;
	private String appVersion;
	private String cloudMessageId;

}
