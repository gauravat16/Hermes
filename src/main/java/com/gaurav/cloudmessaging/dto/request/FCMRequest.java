package com.gaurav.cloudmessaging.dto.request;

import lombok.Data;

/**
 *
 */
@Data
public class FCMRequest {

	private String deviceName;
	private String osVersion;
	private String appVersion;
	private String fcmId;

}
