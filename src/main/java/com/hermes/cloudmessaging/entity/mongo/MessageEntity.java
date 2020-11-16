package com.hermes.cloudmessaging.entity.mongo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@Document(collection = "cloud_messages")
public class MessageEntity<REQ, RESP> extends BaseEntity {

    private REQ cloudMessage;

    private RESP cloudMessageResponse;

}
