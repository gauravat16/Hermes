package com.gaurav.FCMNotificationManager.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseDto {

	protected long id;

	protected Date createdAt;

}
