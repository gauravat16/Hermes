package com.gaurav.FCMNotificationManager.dto;

import lombok.Data;

@Data
public class FCMRegistrationRequest {

	private String deviceName;
	private String osVersion;
	private String appVersion;
	private String fcmId;

}
