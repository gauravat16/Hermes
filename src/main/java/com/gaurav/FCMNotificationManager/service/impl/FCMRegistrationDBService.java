package com.gaurav.FCMNotificationManager.service.impl;

import com.gaurav.FCMNotificationManager.dto.FCMRegistrationRequest;
import com.gaurav.FCMNotificationManager.dto.FCMRegistrationResponse;
import com.gaurav.FCMNotificationManager.entity.FCMRegistryEntity;
import com.gaurav.FCMNotificationManager.repository.FCMRegistryRepository;
import com.gaurav.FCMNotificationManager.service.DbCRUDService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service("FCMDbService")
public class FCMRegistrationDBService implements DbCRUDService<FCMRegistryEntity, FCMRegistrationRequest, FCMRegistrationResponse, Long> {

    @Autowired
    private FCMRegistryRepository fcmRegistryRepository;

    @Override
    public FCMRegistryEntity mapRequestToEntity(FCMRegistrationRequest request) {
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
    public FCMRegistrationResponse create(FCMRegistrationRequest fcmRegistrationRequest) {
        Assert.notNull(fcmRegistrationRequest.getFcmId(), "No fcm id present to update");

        if (fcmRegistryRepository.findByFcmId(fcmRegistrationRequest.getFcmId()) != null) {
            throw new RuntimeException("FCM data already present");
        }
        return mapEntityToResponse(fcmRegistryRepository.save(mapRequestToEntity(fcmRegistrationRequest)));
    }

    @Override
    public List<FCMRegistrationResponse> read(final FCMRegistrationRequest fcmRegistrationRequest) {
        List<FCMRegistrationResponse> responses = fcmRegistryRepository.findAllByFcmId(fcmRegistrationRequest.getFcmId()).
                stream()
                .map(e -> mapEntityToResponse(e))
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public FCMRegistrationResponse update(FCMRegistrationRequest fcmRegistrationRequest) {
        Assert.notNull(fcmRegistrationRequest.getFcmId(), "No fcm id present to update");
        FCMRegistryEntity fcmRegistryEntity = fcmRegistryRepository.findByFcmId(fcmRegistrationRequest.getFcmId());
        Assert.notNull(fcmRegistryEntity, "No data found for " + fcmRegistrationRequest.getFcmId());

        BeanUtils.copyProperties(fcmRegistrationRequest, fcmRegistryEntity);
        return mapEntityToResponse(fcmRegistryRepository.save(fcmRegistryEntity));
    }

    @Override
    public void delete(FCMRegistrationRequest fcmRegistrationRequest) {
        Assert.notNull(fcmRegistrationRequest.getFcmId(), "No fcm id present to update");
        FCMRegistryEntity fcmRegistryEntity = fcmRegistryRepository.findByFcmId(fcmRegistrationRequest.getFcmId());
        Assert.notNull(fcmRegistryEntity, "No data to delete!");
        fcmRegistryRepository.delete(fcmRegistryEntity);
    }

    @Override
    public FCMRegistrationResponse find(FCMRegistrationRequest fcmRegistrationRequest) {
        return null;
    }


}
