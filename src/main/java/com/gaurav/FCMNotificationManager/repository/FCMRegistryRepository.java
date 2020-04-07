package com.gaurav.FCMNotificationManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaurav.FCMNotificationManager.entity.FCMRegistryEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FCMRegistryRepository extends JpaRepository<FCMRegistryEntity, Long>, JpaSpecificationExecutor<FCMRegistryEntity> {

    List<FCMRegistryEntity> findAllByCloudMessagingId(String fcmId);

    FCMRegistryEntity findByCloudMessagingId(String fcmId);

}
