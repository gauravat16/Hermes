package com.gaurav.FCMNotificationManager.service.impl;

import com.gaurav.FCMNotificationManager.dto.request.FCMRequest;
import com.gaurav.FCMNotificationManager.dto.FCMRegistrationResponse;
import com.gaurav.FCMNotificationManager.entity.FCMRegistryEntity;
import com.gaurav.FCMNotificationManager.jpa.criteria.constants.Appender;
import com.gaurav.FCMNotificationManager.jpa.criteria.constants.Operation;
import com.gaurav.FCMNotificationManager.jpa.criteria.dto.SearchCriteria;
import com.gaurav.FCMNotificationManager.jpa.criteria.services.CustomSpecification;
import com.gaurav.FCMNotificationManager.repository.FCMRegistryRepository;
import com.gaurav.FCMNotificationManager.service.DbCRUDService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service("FCMDbService")
public class FCMRegistrationDBService implements DbCRUDService<FCMRegistryEntity, FCMRequest, FCMRegistrationResponse, Long> {

    @Autowired
    private FCMRegistryRepository fcmRegistryRepository;

    @Override
    public FCMRegistryEntity mapRequestToEntity(FCMRequest request) {
        return FCMRegistryEntity.builder()
                .appVersion(request.getAppVersion())
                .deviceName(request.getDeviceName())
                .fcmId(request.getFcmId())
                .osVersion(request.getOsVersion())
                .build();
    }

    @Override
    public FCMRegistrationResponse mapEntityToResponse(FCMRegistryEntity entity) {
        return FCMRegistrationResponse.builder()
                .appVersion(entity.getAppVersion())
                .deviceName(entity.getDeviceName())
                .fcmId(entity.getFcmId())
                .osVersion(entity.getOsVersion())
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Override
    public FCMRegistrationResponse create(FCMRequest fcmRequest) {
        Assert.notNull(fcmRequest.getFcmId(), "No fcm id present to update");

        if (fcmRegistryRepository.findByFcmId(fcmRequest.getFcmId()) != null) {
            throw new RuntimeException("FCM data already present");
        }
        return mapEntityToResponse(fcmRegistryRepository.save(mapRequestToEntity(fcmRequest)));
    }

    @Override
    public List<FCMRegistrationResponse> read(final FCMRequest fcmRequest) {
        List<FCMRegistrationResponse> responses = fcmRegistryRepository.findAllByFcmId(fcmRequest.getFcmId()).
                stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public FCMRegistrationResponse update(FCMRequest fcmRequest) {
        Assert.notNull(fcmRequest.getFcmId(), "No fcm id present to update");
        FCMRegistryEntity fcmRegistryEntity = fcmRegistryRepository.findByFcmId(fcmRequest.getFcmId());
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
