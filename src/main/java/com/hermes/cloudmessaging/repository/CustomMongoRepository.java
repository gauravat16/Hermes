package com.hermes.cloudmessaging.repository;

import com.hermes.cloudmessaging.entity.mongo.CloudMessagingRegistryEntity;

import java.util.List;

public interface CustomMongoRepository {

    List<CloudMessagingRegistryEntity> query(Object o, String strQuery);
}
