package com.hermes.cloudmessaging.repository.impl;

import com.hermes.cloudmessaging.entity.mongo.CloudMessagingRegistryEntity;
import com.hermes.cloudmessaging.jpa.criteria.MongoQueryGenerator;
import com.hermes.cloudmessaging.repository.CustomMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomMongoRepositoryImpl implements CustomMongoRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<CloudMessagingRegistryEntity> query(Object o, String strQuery) {
        return mongoTemplate.find(MongoQueryGenerator.generateQuery(o, strQuery), CloudMessagingRegistryEntity.class);
    }
}
