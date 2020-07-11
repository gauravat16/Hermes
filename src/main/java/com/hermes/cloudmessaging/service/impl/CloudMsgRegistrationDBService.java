package com.hermes.cloudmessaging.service.impl;

import com.hermes.cloudmessaging.dto.FCMRegistrationResponse;
import com.hermes.cloudmessaging.dto.request.CloudMessageRequest;
import com.hermes.cloudmessaging.entity.mongo.CloudMessagingRegistryEntity;
import com.hermes.cloudmessaging.exception.DequeueException;
import com.hermes.cloudmessaging.jpa.criteria.MongoQueryGenerator;
import com.hermes.cloudmessaging.repository.FCMRegistryRepository;
import com.hermes.cloudmessaging.service.DbCRUDService;
import com.hermes.cloudmessaging.service.QueueService;
import com.hermes.cloudmessaging.utils.BeanUtils;
import com.mongodb.BasicDBObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service("CloudMsgRegistrationDBService")
public class CloudMsgRegistrationDBService implements DbCRUDService<CloudMessagingRegistryEntity, CloudMessageRequest, FCMRegistrationResponse, Long> {

    @Autowired
    private FCMRegistryRepository fcmRegistryRepository;

    @Autowired
    @Qualifier("java-CloudMessageRequest")
    private QueueService<CloudMessageRequest> queueService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public CloudMessagingRegistryEntity mapRequestToEntity(CloudMessageRequest request) {


        CloudMessagingRegistryEntity.CloudMessagingRegistryEntityBuilder builder = CloudMessagingRegistryEntity.builder()
                .appVersion(request.getAppVersion())
                .deviceName(request.getDeviceName())
                .cloudMessagingId(request.getCloudMessageId())
                .osVersion(request.getOsVersion());


        builder.metadata(BasicDBObject.parse(request.getMetadata()));


        return builder.build();

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
                .metadata(entity.getMetadata().toString())
                .id(entity.getId())
                .build();
    }

    @Override
    public FCMRegistrationResponse create(CloudMessageRequest cloudMessageRequest) {
        Assert.notNull(cloudMessageRequest.getCloudMessageId(), "No fcm id present to update");

        if (fcmRegistryRepository.findByCloudMessagingId(cloudMessageRequest.getCloudMessageId()) != null) {
            return update(cloudMessageRequest);
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

        BeanUtils.copyNonNullProperties(cloudMessageRequest, cloudMessagingRegistryEntity);
        return mapEntityToResponse(fcmRegistryRepository.save(cloudMessagingRegistryEntity));
    }

    private List<CloudMessagingRegistryEntity> findAllEntities(CloudMessageRequest cloudMessageRequest) {
        String metadata = cloudMessageRequest.getMetadata();
        cloudMessageRequest.setMetadata(null); //Set it null so that query doesn't fail.
        return fcmRegistryRepository.query(cloudMessageRequest, metadata);
    }

    @Override
    public List<FCMRegistrationResponse> delete(CloudMessageRequest cloudMessageRequest) {

        Assert.notNull(cloudMessageRequest, "No req present to delete");
        List<CloudMessagingRegistryEntity> cloudMessagingRegistryEntityList = findAllEntities(cloudMessageRequest);
        cloudMessagingRegistryEntityList.forEach(cloudMessagingRegistryEntity -> fcmRegistryRepository.delete(cloudMessagingRegistryEntity));

        return cloudMessagingRegistryEntityList.stream().map(this::mapEntityToResponse).collect(Collectors.toList());
    }

    @Override
    public List<FCMRegistrationResponse> find(CloudMessageRequest cloudMessageRequest) {
        return findAllEntities(cloudMessageRequest).stream().map(this::mapEntityToResponse).collect(Collectors.toList());
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void asyncCronJob() {
        try {
            create(queueService.dequeue());
        } catch (DequeueException e) {
            log.debug("Dequeue Failed", e);
        }
    }
}
