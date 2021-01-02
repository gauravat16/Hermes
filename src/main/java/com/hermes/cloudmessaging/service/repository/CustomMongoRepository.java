package com.hermes.cloudmessaging.service.repository;

import com.hermes.cloudmessaging.model.dto.request.IndexCreationRequest;
import com.hermes.cloudmessaging.model.entity.mongo.CloudMessagingRegistryEntity;

import java.util.List;

public interface CustomMongoRepository<T> {

    List<CloudMessagingRegistryEntity> query(Object o, String strQuery);

    void createIndexes(Class<CloudMessagingRegistryEntity> clazz, IndexCreationRequest indexCreationRequest);
}
