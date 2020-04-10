package com.hermes.cloudmessaging.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hermes.cloudmessaging.entity.rdbms.CloudMessagingRegistryEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FCMRegistryRepository extends JpaRepository<CloudMessagingRegistryEntity, Long>, JpaSpecificationExecutor<CloudMessagingRegistryEntity> {

    List<CloudMessagingRegistryEntity> findAllByCloudMessagingId(String fcmId);

    CloudMessagingRegistryEntity findByCloudMessagingId(String fcmId);

}
