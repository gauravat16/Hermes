package com.hermes.cloudmessaging.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.webservices")
@Data
public class WebServiceConfig {

    private String googleFCMUrl;
    private String googleFcmAuth;
    private String currentAppUrl;
}
