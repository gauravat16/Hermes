package com.hermes.cloudmessaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
public class HermesApp {

	public static void main(String[] args) {
		SpringApplication.run(HermesApp.class, args);
	}

}
