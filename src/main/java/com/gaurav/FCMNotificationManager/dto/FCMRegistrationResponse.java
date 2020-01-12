package com.gaurav.FCMNotificationManager.dto;

import lombok.Data;

@Data
public class FCMRegistrationResponse extends BaseResponseDto {

	private String deviceName;
	private String osVersion;
	private String appVersion;
	private String fcmId;

}
