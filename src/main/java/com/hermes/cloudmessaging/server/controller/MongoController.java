package com.hermes.cloudmessaging.server.controller;

import com.hermes.cloudmessaging.model.dto.request.IndexCreationRequest;
import com.hermes.cloudmessaging.database.entity.mongo.CloudMessagingRegistryEntity;
import com.hermes.cloudmessaging.database.repository.impl.CustomCloudMessagingRepositoryImpl;
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
