package com.hermes.cloudmessaging.entity.mongo;

import com.hermes.cloudmessaging.constants.enums.UserRoles;
import lombok.*;
import org.springframework.data.annotation.Id;
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
