package com.hermes.cloudmessaging.model.entity.mongo;

import com.hermes.cloudmessaging.model.constants.enums.UserRoles;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "users")
public class User extends BaseEntity {

    @Indexed(unique = true)
    private String userId;

    private String passwordHash;

    private UserRoles role;

}
