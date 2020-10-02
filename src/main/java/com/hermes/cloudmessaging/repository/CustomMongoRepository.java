package com.hermes.cloudmessaging.repository;

import com.hermes.cloudmessaging.dto.request.IndexCreationRequest;
import com.hermes.cloudmessaging.entity.mongo.CloudMessagingRegistryEntity;

import java.util.List;

public interface CustomMongoRepository<T> {

    List<CloudMessagingRegistryEntity> query(Object o, String strQuery);

    void createIndexes(Class<CloudMessagingRegistryEntity> clazz, IndexCreationRequest indexCreationRequest);
}
