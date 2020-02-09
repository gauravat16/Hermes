package com.gaurav.FCMNotificationManager.controller;

import com.gaurav.FCMNotificationManager.service.DbCRUDService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.FCMNotificationManager.dto.FCMRegistrationRequest;

/**
 * 
 * @author gaurav
 *
 */
@Api("")
@RestController
@RequestMapping("device")
public class DeviceController {

	@Autowired
	@Qualifier("FCMDbService")
	private DbCRUDService dbCRUDService;


	@ApiOperation("")
	@PostMapping("record/fcm-request")

	public void recordFCMRequest(@RequestBody FCMRegistrationRequest registrationRequest) {
		dbCRUDService.create(registrationRequest);
	}

}
