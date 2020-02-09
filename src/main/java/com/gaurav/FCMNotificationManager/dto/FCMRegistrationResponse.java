package com.gaurav.FCMNotificationManager.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class FCMRegistrationResponse extends BaseResponseDto {

	@Builder
	public FCMRegistrationResponse(long id, Date createdAt, String deviceName, String osVersion, String appVersion, String fcmId) {
		super(id, createdAt);
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
