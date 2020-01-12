package com.gaurav.FCMNotificationManager.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;

public class BaseEntity {

	@Column(name = "ID")
	@GeneratedValue
	private long id;
	
	@Column(name = "CREATED_AT")
	private Date createdAt = new Date();

}
