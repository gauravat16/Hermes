package com.hermes.cloudmessaging.entity.mongo;

import com.hermes.cloudmessaging.entity.rdbms.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author gaurav
 */
@Document(collection = "CLOUD_MESSAGING_REGISTRY")
@Data
@NoArgsConstructor
public class CloudMessagingRegistryEntity extends BaseEntity {

    @Builder
    public CloudMessagingRegistryEntity(String deviceName, String osVersion, String appVersion, String cloudMessagingId) {
        this.deviceName = deviceName;
        this.osVersion = osVersion;
        this.appVersion = appVersion;
        this.cloudMessagingId = cloudMessagingId;
    }

    private String deviceName;

    private String osVersion;

    private String appVersion;

    private String cloudMessagingId;
}
