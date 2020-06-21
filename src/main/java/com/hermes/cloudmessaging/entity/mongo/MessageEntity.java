package com.hermes.cloudmessaging.entity.mongo;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document(collection = "cloud_messages")
public class MessageEntity<REQ, RESP> {

    @Id
    private String id;

    private REQ cloudMessage;

    private RESP cloudMessageResponse;

}
