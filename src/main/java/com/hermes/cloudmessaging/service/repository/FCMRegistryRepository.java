package com.hermes.cloudmessaging.service.repository;

import com.hermes.cloudmessaging.model.entity.mongo.CloudMessagingRegistryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FCMRegistryRepository extends MongoRepository<CloudMessagingRegistryEntity, String> {

    CloudMessagingRegistryEntity findByCloudMessagingId(String fcmId);

    CloudMessagingRegistryEntity findByOsVersion(String osVersion);
}
