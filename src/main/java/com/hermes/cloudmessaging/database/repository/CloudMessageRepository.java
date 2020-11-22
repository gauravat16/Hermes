package com.hermes.cloudmessaging.database.repository;

import com.hermes.cloudmessaging.database.entity.mongo.MessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CloudMessageRepository<REQ, RESP> extends MongoRepository<MessageEntity<REQ, RESP>, String> {
}
