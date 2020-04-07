package com.hermes.cloudmessaging.service;

import com.hermes.cloudmessaging.dto.request.FCMRequest;
import com.hermes.cloudmessaging.dto.FCMRegistrationResponse;

public interface DeviceRegistryService {
	
	FCMRegistrationResponse registerFCMRequest(FCMRequest registrationRequest);

}
