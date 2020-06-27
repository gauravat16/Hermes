package com.hermes.cloudmessaging.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.hermes.cloudmessaging.entity.mongo.CloudMessagingRegistryEntity;
import com.hermes.cloudmessaging.repository.FCMRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GraphQLProvider implements GraphQLQueryResolver {

    @Autowired
    private FCMRegistryRepository fcmRegistryRepository;

    public CloudMessagingRegistryEntity getDataById(long id) {
        return fcmRegistryRepository.findById(id).get();
    }

    public CloudMessagingRegistryEntity getDataByOsVersion(String osVersion) {
        return fcmRegistryRepository.findByOsVersion(osVersion);
    }

    public List<CloudMessagingRegistryEntity> getByQuery(String id, String deviceName, String osVersion, String appVersion, String cloudMessagingId) {
        CloudMessagingRegistryEntity entity = new CloudMessagingRegistryEntity(deviceName, osVersion, appVersion, cloudMessagingId, null);
        entity.setId(id);
        return fcmRegistryRepository.findAll(Example.of(entity, ExampleMatcher.matchingAny()));
    }


}
