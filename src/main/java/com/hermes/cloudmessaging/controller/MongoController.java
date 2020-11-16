package com.hermes.cloudmessaging.controller;

import com.hermes.cloudmessaging.dto.request.IndexCreationRequest;
import com.hermes.cloudmessaging.entity.mongo.CloudMessagingRegistryEntity;
import com.hermes.cloudmessaging.repository.impl.CustomCloudMessagingRepositoryImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiKeyAuthDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mongo")
public class MongoController {

    private CustomCloudMessagingRepositoryImpl customCloudMessagingRepository;

    @Autowired
    public MongoController(CustomCloudMessagingRepositoryImpl customCloudMessagingRepository) {
        this.customCloudMessagingRepository = customCloudMessagingRepository;
    }

    @PutMapping("/indexes")
    public void addIndexes(IndexCreationRequest indexCreationRequest) {
        customCloudMessagingRepository.createIndexes(CloudMessagingRegistryEntity.class, indexCreationRequest);
    }
}
