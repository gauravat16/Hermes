package com.gaurav.cloudmessaging.service;

import com.gaurav.cloudmessaging.dto.request.FCMRequest;
import com.gaurav.cloudmessaging.dto.FCMRegistrationResponse;

public interface DeviceRegistryService {
	
	FCMRegistrationResponse registerFCMRequest(FCMRequest registrationRequest);

}
