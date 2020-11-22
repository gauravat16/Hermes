package com.hermes.cloudmessaging.server.graphql;

import com.hermes.cloudmessaging.model.dto.FCMRegistrationResponse;
import com.hermes.cloudmessaging.model.dto.request.CloudMessageRequest;
import com.hermes.cloudmessaging.core.impl.CloudMsgRegistrationDBService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GraphQLProvider implements GraphQLQueryResolver, GraphQLMutationResolver {


    @Autowired
    private CloudMsgRegistrationDBService cloudMsgRegistrationDBService;


    public List<FCMRegistrationResponse> getByQuery(String id, String deviceName, String osVersion, String appVersion, String cloudMessagingId, String customQuery) {
        return cloudMsgRegistrationDBService.find(paramsToRequest(deviceName, osVersion, appVersion, cloudMessagingId, customQuery));
    }

    public FCMRegistrationResponse addDeviceData(String deviceName, String osVersion, String appVersion, String cloudMessagingId, String metadata) {
        return cloudMsgRegistrationDBService.create(paramsToRequest(deviceName, osVersion, appVersion, cloudMessagingId, metadata));
    }

    public List<FCMRegistrationResponse> deleteDeviceData(String deviceName, String osVersion, String appVersion, String cloudMessagingId, String customQuery) {
        return cloudMsgRegistrationDBService.delete(paramsToRequest(deviceName, osVersion, appVersion, cloudMessagingId, customQuery));
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
