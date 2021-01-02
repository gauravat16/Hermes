package com.hermes.cloudmessaging.core.impl;

import com.hermes.cloudmessaging.model.dto.FCMRegistrationResponse;
import com.hermes.cloudmessaging.model.dto.request.CloudMessageRequest;
import com.hermes.cloudmessaging.model.dto.request.QueueRequest;
import com.hermes.cloudmessaging.model.entity.mongo.CloudMessagingRegistryEntity;
import com.hermes.cloudmessaging.service.repository.FCMRegistryRepository;
import com.hermes.cloudmessaging.service.DbCRUDService;
import com.hermes.cloudmessaging.service.QueueMessageHandler;
import com.hermes.cloudmessaging.service.QueueService;
import com.hermes.cloudmessaging.core.utils.BeanUtils;
import com.mongodb.BasicDBObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service("CloudMsgRegistrationDBService")
public class CloudMsgRegistrationDBService implements DbCRUDService<CloudMessagingRegistryEntity, CloudMessageRequest, FCMRegistrationResponse, Long>,
        QueueMessageHandler {

    @Autowired
    private FCMRegistryRepository fcmRegistryRepository;

    @Autowired
    private QueueService<QueueRequest> queueService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CustomCloudMessagingRepositoryImpl customCloudMessagingRepository;

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
        return findAllEntities(cloudMessageRequest).
                stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FCMRegistrationResponse update(CloudMessageRequest cloudMessageRequest) {
        Assert.notNull(cloudMessageRequest.getCloudMessageId(), "No fcm id present to update");
        CloudMessagingRegistryEntity cloudMessagingRegistryEntity = fcmRegistryRepository.findByCloudMessagingId(cloudMessageRequest.getCloudMessageId());
        Assert.notNull(cloudMessagingRegistryEntity, "No data found for " + cloudMessageRequest.getCloudMessageId());

        BeanUtils.copyNonNullProperties(cloudMessageRequest, cloudMessagingRegistryEntity);
        if(!StringUtils.isEmpty(cloudMessageRequest.getMetadata())){
            cloudMessagingRegistryEntity.setMetadata(BasicDBObject.parse(cloudMessageRequest.getMetadata()));
        }
        return mapEntityToResponse(fcmRegistryRepository.save(cloudMessagingRegistryEntity));
    }

    private List<CloudMessagingRegistryEntity> findAllEntities(CloudMessageRequest cloudMessageRequest) {
        String metadata = cloudMessageRequest.getMetadata();
        cloudMessageRequest.setMetadata(null); //Set it null so that query doesn't fail.
        return customCloudMessagingRepository.query(cloudMessageRequest, metadata);
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

    @Override
    public void handleRequest(Object request) {
        create((CloudMessageRequest) request);
    }

    @Override
    public QueueRequest.Type getQueueRequestType() {
        return QueueRequest.Type.HEART_BEAT;
    }
}
