package com.hermes.cloudmessaging.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableMongoRepositories(basePackages = {"com.hermes.cloudmessaging.service"})
@SpringBootApplication(scanBasePackages = {"com.hermes.cloudmessaging"})
@EnableConfigurationProperties
@EnableScheduling
public class HermesApp {

    public static void main(String[] args) {
        SpringApplication.run(HermesApp.class, args);
    }

}
