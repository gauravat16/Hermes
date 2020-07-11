package com.hermes.cloudmessaging.repository;

import com.hermes.cloudmessaging.entity.mongo.CloudMessagingRegistryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FCMRegistryRepository extends MongoRepository<CloudMessagingRegistryEntity, Long>, CustomMongoRepository {

    List<CloudMessagingRegistryEntity> findAllByCloudMessagingId(String fcmId);

    CloudMessagingRegistryEntity findByCloudMessagingId(String fcmId);

    CloudMessagingRegistryEntity findByOsVersion(String osVersion);
}
