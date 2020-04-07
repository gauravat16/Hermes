package com.hermes.cloudmessaging.service.impl;

import com.hermes.cloudmessaging.dto.request.FCMRequest;
import com.hermes.cloudmessaging.dto.FCMRegistrationResponse;
import com.hermes.cloudmessaging.entity.FCMRegistryEntity;
import com.hermes.cloudmessaging.jpa.criteria.constants.Appender;
import com.hermes.cloudmessaging.jpa.criteria.constants.Operation;
import com.hermes.cloudmessaging.jpa.criteria.dto.SearchCriteria;
import com.hermes.cloudmessaging.jpa.criteria.services.CustomSpecification;
import com.hermes.cloudmessaging.repository.FCMRegistryRepository;
import com.hermes.cloudmessaging.service.DbCRUDService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service("FCMDbService")
public class CloudMsgRegistrationDBService implements DbCRUDService<FCMRegistryEntity, FCMRequest, FCMRegistrationResponse, Long> {

    @Autowired
    private FCMRegistryRepository fcmRegistryRepository;

    @Override
    public FCMRegistryEntity mapRequestToEntity(FCMRequest request) {
        return FCMRegistryEntity.builder()
                .appVersion(request.getAppVersion())
                .deviceName(request.getDeviceName())
                .cloudMessagingId(request.getFcmId())
                .osVersion(request.getOsVersion())
                .build();
    }

    @Override
    public FCMRegistrationResponse mapEntityToResponse(FCMRegistryEntity entity) {
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
    public FCMRegistrationResponse create(FCMRequest fcmRequest) {
        Assert.notNull(fcmRequest.getFcmId(), "No fcm id present to update");

        if (fcmRegistryRepository.findByCloudMessagingId(fcmRequest.getFcmId()) != null) {
            throw new RuntimeException("FCM data already present");
        }
        return mapEntityToResponse(fcmRegistryRepository.save(mapRequestToEntity(fcmRequest)));
    }

    @Override
    public List<FCMRegistrationResponse> read(final FCMRequest fcmRequest) {
        List<FCMRegistrationResponse> responses = fcmRegistryRepository.findAllByCloudMessagingId(fcmRequest.getFcmId()).
                stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public FCMRegistrationResponse update(FCMRequest fcmRequest) {
        Assert.notNull(fcmRequest.getFcmId(), "No fcm id present to update");
        FCMRegistryEntity fcmRegistryEntity = fcmRegistryRepository.findByCloudMessagingId(fcmRequest.getFcmId());
        Assert.notNull(fcmRegistryEntity, "No data found for " + fcmRequest.getFcmId());

        BeanUtils.copyProperties(fcmRequest, fcmRegistryEntity);
        return mapEntityToResponse(fcmRegistryRepository.save(fcmRegistryEntity));
    }

    @Override
    public void delete(FCMRequest fcmRequest) {
        Assert.notNull(fcmRequest, "No req present to delete");
        List<FCMRegistryEntity> fcmRegistryEntityList = findAllEntities(fcmRequest);
        fcmRegistryEntityList.forEach(fcmRegistryEntity -> fcmRegistryRepository.delete(fcmRegistryEntity));
    }

    @Override
    public List<FCMRegistrationResponse> find(FCMRequest fcmRequest) {
        return findAllEntities(fcmRequest).stream().map(this::mapEntityToResponse).collect(Collectors.toList());
    }


    private List<FCMRegistryEntity> findAllEntities(FCMRequest fcmRequest) {
        CustomSpecification<FCMRegistryEntity> customSpecification = new CustomSpecification<>();

        if (null != fcmRequest.getFcmId()) {
            customSpecification.add(SearchCriteria.of("fcmId", fcmRequest.getFcmId(), Operation.EQUALS, Appender.AND));
        }

        if (null != fcmRequest.getOsVersion()) {
            customSpecification.add(SearchCriteria.of("osVersion", fcmRequest.getOsVersion(), Operation.EQUALS, Appender.AND));
        }


        if (null != fcmRequest.getDeviceName()) {
            customSpecification.add(SearchCriteria.of("deviceName", fcmRequest.getDeviceName(), Operation.LIKE, Appender.AND));
        }


        if (null != fcmRequest.getAppVersion()) {
            customSpecification.add(SearchCriteria.of("appVersion", fcmRequest.getAppVersion(), Operation.EQUALS, Appender.AND));
        }

        return fcmRegistryRepository.findAll(customSpecification);
    }


}
