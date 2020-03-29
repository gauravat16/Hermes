package com.gaurav.FCMNotificationManager.service.impl;

import com.gaurav.FCMNotificationManager.config.WebServiceConfig;
import com.gaurav.FCMNotificationManager.dto.FCMMessage;
import com.gaurav.FCMNotificationManager.dto.response.FCMResponse;
import com.gaurav.FCMNotificationManager.service.Messenger;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class FCMMessenger implements Messenger<FCMMessage, FCMResponse> {

    private final WebServiceConfig webServiceConfig;

    private final RestTemplate restTemplate;

    public FCMMessenger(WebServiceConfig webServiceConfig, RestTemplate restTemplate) {
        this.webServiceConfig = webServiceConfig;
        this.restTemplate = restTemplate;
    }

    @Override
    public FCMResponse sendMessageToUser(FCMMessage request) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", webServiceConfig.getGoogleFcmAuth());
        HttpEntity<FCMMessage> requestEntity = new HttpEntity<>(request, headers);

        return restTemplate.postForObject(URI.create(webServiceConfig.getGoogleFCMUrl()), requestEntity, FCMResponse.class);
    }
}
