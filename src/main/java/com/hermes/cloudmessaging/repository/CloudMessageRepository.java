package com.hermes.cloudmessaging.repository;

import com.hermes.cloudmessaging.entity.mongo.MessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CloudMessageRepository<REQ, RESP> extends MongoRepository<MessageEntity<REQ, RESP>, String> {
}
