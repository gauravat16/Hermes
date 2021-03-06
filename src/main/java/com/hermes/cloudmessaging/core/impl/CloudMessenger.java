package com.hermes.cloudmessaging.core.impl;

import com.hermes.cloudmessaging.model.dto.FCMMessage;
import com.hermes.cloudmessaging.model.dto.response.FCMResponse;
import com.hermes.cloudmessaging.server.config.WebServiceConfig;
import com.hermes.cloudmessaging.service.Messenger;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service(value = "CloudMessenger")
public class CloudMessenger implements Messenger<FCMMessage, FCMResponse> {

    private final WebServiceConfig webServiceConfig;

    private final RestTemplate restTemplate;

    public CloudMessenger(WebServiceConfig webServiceConfig, RestTemplate restTemplate) {
        this.webServiceConfig = webServiceConfig;
        this.restTemplate = restTemplate;
    }

    @Override
    public FCMResponse sendMessageToUser(FCMMessage request) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "key="+webServiceConfig.getGoogleFcmAuth());
        HttpEntity<FCMMessage> requestEntity = new HttpEntity<>(request, headers);

        return restTemplate.postForObject(URI.create(webServiceConfig.getGoogleFCMUrl()), requestEntity, FCMResponse.class);
    }

}
