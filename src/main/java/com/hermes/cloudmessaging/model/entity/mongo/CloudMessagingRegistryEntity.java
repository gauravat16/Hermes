package com.hermes.cloudmessaging.model.entity.mongo;

import com.mongodb.DBObject;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author gaurav
 */
@EqualsAndHashCode(callSuper = true)
@Document(collection = "CLOUD_MESSAGING_REGISTRY")
@Data
@NoArgsConstructor
public class CloudMessagingRegistryEntity extends BaseEntity {

    @Builder
    public CloudMessagingRegistryEntity(String deviceName, String osVersion, String appVersion, String cloudMessagingId, DBObject metadata) {
        this.deviceName = deviceName;
        this.osVersion = osVersion;
        this.appVersion = appVersion;
        this.cloudMessagingId = cloudMessagingId;
        this.metadata = metadata;
    }

    private String deviceName;

    private String osVersion;

    private String appVersion;

    private String cloudMessagingId;

    /**
     * This data is in JSON format
     */
    private DBObject metadata;
}
