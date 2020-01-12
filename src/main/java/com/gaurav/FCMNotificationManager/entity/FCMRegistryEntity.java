package com.gaurav.FCMNotificationManager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;

/**
 * 
 * @author gaurav
 *
 */
@Entity(name = "FCM_REGISTRY")
@Data
public class FCMRegistryEntity extends BaseEntity {
	
	@Column(name = "DEVICE_NAME")
	private String deviceName;
	
	@Column(name = "OS_VERSION")
	private String osVersion;
	
	@Column(name = "APP_VERSION")
	private String appVersion;
	
	@Column(name = "FCM_ID")
	private String fcmId;
}
