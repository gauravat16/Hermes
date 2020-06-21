package com.hermes.cloudmessaging.graphql;

import com.hermes.cloudmessaging.entity.mongo.CloudMessagingRegistryEntity;
import com.hermes.cloudmessaging.repository.FCMRegistryRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataFetchers {

    @Autowired
    private FCMRegistryRepository fcmRegistryRepository;

    public final DataFetcher<CloudMessagingRegistryEntity> registryEntityDataFetcher = environment -> {
        Long id = Long.parseLong(environment.getArgument("id"));
        return fcmRegistryRepository.findById(id).get();
    };

    public final DataFetcher<CloudMessagingRegistryEntity> registryEntityDataFetcherByOsVersion = environment -> {
        String osVersion = environment.getArgument("osVersion");
        return fcmRegistryRepository.findByOsVersion(osVersion);
    };
}
