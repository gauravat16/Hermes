package com.gaurav.FCMNotificationManager.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class FCMRegistrationResponse {

	@Builder
	public FCMRegistrationResponse(long id, Date createdAt, String deviceName, String osVersion, String appVersion, String fcmId) {
		this.deviceName = deviceName;
		this.osVersion = osVersion;
		this.appVersion = appVersion;
		this.fcmId = fcmId;
	}

	private String deviceName;
	private String osVersion;
	private String appVersion;
	private String fcmId;

}