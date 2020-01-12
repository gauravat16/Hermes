package com.gaurav.FCMNotificationManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaurav.FCMNotificationManager.entity.FCMRegistryEntity;

public interface FCMRegistryRepository extends JpaRepository<FCMRegistryEntity, Long> {

}
