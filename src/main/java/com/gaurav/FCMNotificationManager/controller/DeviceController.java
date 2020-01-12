package com.gaurav.FCMNotificationManager.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.FCMNotificationManager.dto.FCMRegistrationRequest;

/**
 * 
 * @author gaurav
 *
 */
@RestController
public class DeviceController {

	@PostMapping
	public void recordFCMRequest(@RequestBody FCMRegistrationRequest registrationRequest) {
	}

}
