package com.gaurav.cloudmessaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class FcmNotificationManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FcmNotificationManagerApplication.class, args);
	}

}
