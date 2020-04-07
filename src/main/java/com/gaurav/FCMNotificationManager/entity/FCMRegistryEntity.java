package com.gaurav.FCMNotificationManager.entity;

import javax.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author gaurav
 *
 */
@Entity(name = "CLOUD_MESSAGING_REGISTRY")
@Data
@NoArgsConstructor
@Table(indexes = {@Index(columnList = "CLOUD_MESSAGING_ID")})
public class FCMRegistryEntity extends BaseEntity {

	@Builder
	public FCMRegistryEntity(String deviceName, String osVersion, String appVersion, String cloudMessagingId) {
		this.deviceName = deviceName;
		this.osVersion = osVersion;
		this.appVersion = appVersion;
		this.cloudMessagingId = cloudMessagingId;
	}

	@Column(name = "DEVICE_NAME")
	private String deviceName;
	
	@Column(name = "OS_VERSION")
	private String osVersion;
	
	@Column(name = "APP_VERSION")
	private String appVersion;

	@Column(name = "CLOUD_MESSAGING_ID", unique = true)
	private String cloudMessagingId;
}
