package com.hermes.cloudmessaging.service.impl;

import com.hermes.cloudmessaging.dto.FCMRegistrationResponse;
import com.hermes.cloudmessaging.dto.request.CloudMessageRequest;
import com.hermes.cloudmessaging.entity.mongo.CloudMessagingRegistryEntity;
import com.hermes.cloudmessaging.repository.FCMRegistryRepository;
import com.hermes.cloudmessaging.service.DbCRUDService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service("CloudMsgRegistrationDBService")
public class CloudMsgRegistrationDBService implements DbCRUDService<CloudMessagingRegistryEntity, CloudMessageRequest, FCMRegistrationResponse, Long> {

    @Autowired
    private FCMRegistryRepository fcmRegistryRepository;

    @Override
    public CloudMessagingRegistryEntity mapRequestToEntity(CloudMessageRequest request) {
        return CloudMessagingRegistryEntity.builder()
                .appVersion(request.getAppVersion())
                .deviceName(request.getDeviceName())
                .cloudMessagingId(request.getCloudMessageId())
                .osVersion(request.getOsVersion())
                .build();
    }

    @Override
    public FCMRegistrationResponse mapEntityToResponse(CloudMessagingRegistryEntity entity) {
        return FCMRegistrationResponse.builder()
                .appVersion(entity.getAppVersion())
                .deviceName(entity.getDeviceName())
                .cloudMessagingId(entity.getCloudMessagingId())
                .osVersion(entity.getOsVersion())
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Override
    public FCMRegistrationResponse create(CloudMessageRequest cloudMessageRequest) {
        Assert.notNull(cloudMessageRequest.getCloudMessageId(), "No fcm id present to update");

        if (fcmRegistryRepository.findByCloudMessagingId(cloudMessageRequest.getCloudMessageId()) != null) {
            throw new RuntimeException("FCM data already present");
        }
        return mapEntityToResponse(fcmRegistryRepository.save(mapRequestToEntity(cloudMessageRequest)));
    }

    @Override
    public List<FCMRegistrationResponse> read(final CloudMessageRequest cloudMessageRequest) {
        List<FCMRegistrationResponse> responses = fcmRegistryRepository.findAllByCloudMessagingId(cloudMessageRequest.getCloudMessageId()).
                stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public FCMRegistrationResponse update(CloudMessageRequest cloudMessageRequest) {
        Assert.notNull(cloudMessageRequest.getCloudMessageId(), "No fcm id present to update");
        CloudMessagingRegistryEntity cloudMessagingRegistryEntity = fcmRegistryRepository.findByCloudMessagingId(cloudMessageRequest.getCloudMessageId());
        Assert.notNull(cloudMessagingRegistryEntity, "No data found for " + cloudMessageRequest.getCloudMessageId());

        BeanUtils.copyProperties(cloudMessageRequest, cloudMessagingRegistryEntity);
        return mapEntityToResponse(fcmRegistryRepository.save(cloudMessagingRegistryEntity));
    }

//    @Override
//    public void delete(CloudMessageRequest cloudMessageRequest) {
//        Assert.notNull(cloudMessageRequest, "No req present to delete");
//        List<CloudMessagingRegistryEntity> cloudMessagingRegistryEntityList = findAllEntities(cloudMessageRequest);
//        cloudMessagingRegistryEntityList.forEach(cloudMessagingRegistryEntity -> fcmRegistryRepository.delete(cloudMessagingRegistryEntity));
//    }

//    @Override
//    public List<FCMRegistrationResponse> find(CloudMessageRequest cloudMessageRequest) {
//        return findAllEntities(cloudMessageRequest).stream().map(this::mapEntityToResponse).collect(Collectors.toList());
//    }


    @Override
    public void delete(CloudMessageRequest cloudMessageRequest) {

    }

    @Override
    public List<FCMRegistrationResponse> find(CloudMessageRequest cloudMessageRequest) {
        return null;
    }
}
