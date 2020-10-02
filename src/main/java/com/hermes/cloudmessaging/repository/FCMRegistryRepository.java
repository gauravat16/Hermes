package com.hermes.cloudmessaging.repository;

import com.hermes.cloudmessaging.entity.mongo.CloudMessagingRegistryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FCMRegistryRepository extends MongoRepository<CloudMessagingRegistryEntity, String> {

    CloudMessagingRegistryEntity findByCloudMessagingId(String fcmId);

    CloudMessagingRegistryEntity findByOsVersion(String osVersion);
}
