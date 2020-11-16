package com.hermes.cloudmessaging.constants.enums;

public enum UserRoles {
    ADMIN,
    USER;

    public String getSpringRole(){
        return "ROLE_"+this.name();
    }
}
