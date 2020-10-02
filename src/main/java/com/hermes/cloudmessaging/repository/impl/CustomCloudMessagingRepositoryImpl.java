package com.hermes.cloudmessaging.repository.impl;

import com.hermes.cloudmessaging.dto.request.IndexCreationRequest;
import com.hermes.cloudmessaging.entity.mongo.CloudMessagingRegistryEntity;
import com.hermes.cloudmessaging.jpa.criteria.MongoQueryGenerator;
import com.hermes.cloudmessaging.repository.CustomMongoRepository;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Log4j2
@Service
public class CustomCloudMessagingRepositoryImpl implements CustomMongoRepository<CloudMessagingRegistryEntity> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<CloudMessagingRegistryEntity> query(Object o, String strQuery) {
        return mongoTemplate.find(MongoQueryGenerator.generateQuery(o, strQuery), CloudMessagingRegistryEntity.class);
    }

    @Override
    public void createIndexes(Class<CloudMessagingRegistryEntity> clazz, IndexCreationRequest indexCreationRequest) {
        Assert.notNull(clazz, "Entity Clazz not passed");
        Assert.notNull(indexCreationRequest, "Request is null");
        Assert.notEmpty(indexCreationRequest.getPairs(), "No indices passed");

        IndexDefinition indexDefinition;

        if (indexCreationRequest.getPairs().size() == 1
                && indexCreationRequest.getPairs().get(0).getA().size() == 1) {
            IndexCreationRequest.Pair pair = indexCreationRequest.getPairs().get(0);
            Sort.Direction direction = pair.getB().equals(IndexCreationRequest.Sort.ASC) ?
                    Sort.Direction.ASC : Sort.Direction.DESC;
            indexDefinition = new Index(pair.getA().get(0), direction);
        } else {
            Document document = new Document();

            for (IndexCreationRequest.Pair p : indexCreationRequest.getPairs()) {
                Integer direction = p.getB().equals(IndexCreationRequest.Sort.ASC) ? 1 : -1;
                p.getA().forEach(s -> document.append(s, direction));
            }
            indexDefinition = new CompoundIndexDefinition(document);
        }
        String result = mongoTemplate.indexOps(clazz).ensureIndex(indexDefinition);
        log.info("Mongo index creation result {}", result);
    }


}
