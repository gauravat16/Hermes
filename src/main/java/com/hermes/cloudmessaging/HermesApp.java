package com.hermes.cloudmessaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class HermesApp {

	public static void main(String[] args) {
		SpringApplication.run(HermesApp.class, args);
	}

}
