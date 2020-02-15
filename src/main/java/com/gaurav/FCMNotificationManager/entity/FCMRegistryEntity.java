package com.gaurav.FCMNotificationManager.entity;

import javax.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 
 * @author gaurav
 *
 */
@Entity(name = "FCM_REGISTRY")
@Data
@NoArgsConstructor
@Table(indexes = {@Index(columnList = "FCM_ID")})
public class FCMRegistryEntity extends BaseEntity {

	@Builder
	public FCMRegistryEntity(String deviceName, String osVersion, String appVersion, String fcmId) {
		this.deviceName = deviceName;
		this.osVersion = osVersion;
		this.appVersion = appVersion;
		this.fcmId = fcmId;
	}

	@Column(name = "DEVICE_NAME")
	private String deviceName;
	
	@Column(name = "OS_VERSION")
	private String osVersion;
	
	@Column(name = "APP_VERSION")
	private String appVersion;

	@Column(name = "FCM_ID", unique = true)
	private String fcmId;
}
