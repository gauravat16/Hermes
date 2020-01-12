package com.gaurav.FCMNotificationManager.service;

import com.gaurav.FCMNotificationManager.dto.FCMRegistrationRequest;
import com.gaurav.FCMNotificationManager.dto.FCMRegistrationResponse;

public interface DeviceRegistryService {
	
	FCMRegistrationResponse registerFCMRequest(FCMRegistrationRequest registrationRequest);

}
