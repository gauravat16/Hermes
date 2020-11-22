package com.hermes.cloudmessaging.database.repository;

import com.hermes.cloudmessaging.database.entity.mongo.CloudMessagingRegistryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FCMRegistryRepository extends MongoRepository<CloudMessagingRegistryEntity, String> {

    CloudMessagingRegistryEntity findByCloudMessagingId(String fcmId);

    CloudMessagingRegistryEntity findByOsVersion(String osVersion);
}
