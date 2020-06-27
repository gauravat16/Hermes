package com.hermes.cloudmessaging.entity.mongo;

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
    public CloudMessagingRegistryEntity(String deviceName, String osVersion, String appVersion, String cloudMessagingId, String metadata) {
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
    private String metadata;
}
