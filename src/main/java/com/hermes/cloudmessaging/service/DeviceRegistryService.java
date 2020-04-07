package com.hermes.cloudmessaging.service;

import com.hermes.cloudmessaging.dto.request.CloudMessageRequest;
import com.hermes.cloudmessaging.dto.FCMRegistrationResponse;

public interface DeviceRegistryService {
	
	FCMRegistrationResponse registerFCMRequest(CloudMessageRequest registrationRequest);

}
