package com.hermes.cloudmessaging.database.service;

import com.hermes.cloudmessaging.model.dto.request.IndexCreationRequest;
import com.hermes.cloudmessaging.model.entity.mongo.CloudMessagingRegistryEntity;
import com.hermes.cloudmessaging.database.jpa.criteria.MongoQueryGenerator;
import com.hermes.cloudmessaging.service.repository.CustomMongoRepository;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
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

        switch (indexCreationRequest.getIndexType()) {
            case SINGLE:
                indexDefinition = createSingleIndex(indexCreationRequest);
                break;
            case COMPOUND:
                indexDefinition = createCompoundIndex(indexCreationRequest);
                break;
            case TEXT:
                indexDefinition = createTextIndex(indexCreationRequest);
                break;
            default:
                throw new IllegalArgumentException("Invalid index passed!");
        }

        String result = mongoTemplate.indexOps(clazz).ensureIndex(indexDefinition);
        log.info("Mongo index creation result {}", result);
    }

    private IndexDefinition createSingleIndex(IndexCreationRequest indexCreationRequest) {
        if (indexCreationRequest.getPairs().size() == 1
                && indexCreationRequest.getPairs().get(0).getElementNames().size() == 1) {
            IndexCreationRequest.Pair pair = indexCreationRequest.getPairs().get(0);
            Sort.Direction direction = pair.getDirection().equals(IndexCreationRequest.Sort.ASC) ?
                    Sort.Direction.ASC : Sort.Direction.DESC;
            return new Index(pair.getElementNames().get(0), direction);
        }
        throw new IllegalArgumentException("IndexCreationRequest and index type don't match");
    }

    private IndexDefinition createCompoundIndex(IndexCreationRequest indexCreationRequest) {

        Document document = new Document();

        for (IndexCreationRequest.Pair p : indexCreationRequest.getPairs()) {
            Integer direction = p.getDirection().equals(IndexCreationRequest.Sort.ASC) ? 1 : -1;
            p.getElementNames().forEach(s -> document.append(s, direction));
        }

        return new CompoundIndexDefinition(document);
    }

    private IndexDefinition createTextIndex(IndexCreationRequest indexCreationRequest) {
        TextIndexDefinition.TextIndexDefinitionBuilder builder = new TextIndexDefinition.TextIndexDefinitionBuilder();
        for (IndexCreationRequest.Pair p : indexCreationRequest.getPairs()) {
            p.getElementNames().forEach(builder::onField);
        }
        return builder.build();
    }


}
