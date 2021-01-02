package com.hermes.cloudmessaging.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hermes.cloudmessaging.model.constants.enums.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationResponse {

    private String userId;

    private UserRoles role;

    @JsonIgnore
    private String passwordHash;
}
