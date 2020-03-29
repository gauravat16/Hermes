package com.gaurav.FCMNotificationManager.service;

import com.gaurav.FCMNotificationManager.dto.request.FCMRequest;
import com.gaurav.FCMNotificationManager.dto.FCMRegistrationResponse;

public interface DeviceRegistryService {
	
	FCMRegistrationResponse registerFCMRequest(FCMRequest registrationRequest);

}
