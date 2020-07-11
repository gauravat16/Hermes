package com.hermes.cloudmessaging.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.hermes.cloudmessaging.dto.FCMRegistrationResponse;
import com.hermes.cloudmessaging.dto.request.CloudMessageRequest;
import com.hermes.cloudmessaging.service.QueueService;
import com.hermes.cloudmessaging.service.impl.CloudMsgRegistrationDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GraphQLProvider implements GraphQLQueryResolver, GraphQLMutationResolver {


    @Autowired
    private CloudMsgRegistrationDBService cloudMsgRegistrationDBService;

    public List<FCMRegistrationResponse> getByQuery(String id, String deviceName, String osVersion, String appVersion, String cloudMessagingId, String metadata) {
        return cloudMsgRegistrationDBService.find(paramsToRequest(deviceName, osVersion, appVersion, cloudMessagingId, metadata));
    }

    public FCMRegistrationResponse addDeviceData(String deviceName, String osVersion, String appVersion, String cloudMessagingId, String metadata) {
        return cloudMsgRegistrationDBService.create(paramsToRequest(deviceName, osVersion, appVersion, cloudMessagingId, metadata));
    }

    public List<FCMRegistrationResponse> deleteDeviceData(String deviceName, String osVersion, String appVersion, String cloudMessagingId, String metadata) {
        return cloudMsgRegistrationDBService.delete(paramsToRequest(deviceName, osVersion, appVersion, cloudMessagingId, metadata));
    }

    public FCMRegistrationResponse updateDeviceData(String deviceName, String osVersion, String appVersion, String cloudMessagingId, String metadata) {
        return cloudMsgRegistrationDBService.update(paramsToRequest(deviceName, osVersion, appVersion, cloudMessagingId, metadata));
    }

    private CloudMessageRequest paramsToRequest(String deviceName, String osVersion, String appVersion, String cloudMessagingId, String metadata) {
        return CloudMessageRequest.builder().appVersion(appVersion)
                .cloudMessageId(cloudMessagingId)
                .deviceName(deviceName)
                .metadata(metadata)
                .osVersion(osVersion)
                .build();
    }


}
