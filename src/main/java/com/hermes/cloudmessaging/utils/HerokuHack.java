package com.hermes.cloudmessaging.utils;

import com.hermes.cloudmessaging.config.WebServiceConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Log4j2
public class HerokuHack {

    private final RestTemplate restTemplate;
    private final WebServiceConfig webServiceConfig;

    @Autowired
    public HerokuHack(RestTemplate restTemplate, WebServiceConfig webServiceConfig) {
        this.restTemplate = restTemplate;
        this.webServiceConfig = webServiceConfig;
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    private void hitSelfToKeepAlive() {
        String url = webServiceConfig.getCurrentAppUrl();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url + "/hermes/ping", String.class);
            log.info("response from self {}", response);
        } catch (Exception e) {
            log.debug("Exception while hitting self ping url", e);
        }
    }
}
