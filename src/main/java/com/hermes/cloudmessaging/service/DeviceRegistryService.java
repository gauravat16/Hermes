package com.hermes.cloudmessaging.service;

import com.hermes.cloudmessaging.model.dto.request.CloudMessageRequest;
import com.hermes.cloudmessaging.model.dto.FCMRegistrationResponse;

public interface DeviceRegistryService {
	
	FCMRegistrationResponse registerFCMRequest(CloudMessageRequest registrationRequest);

}
