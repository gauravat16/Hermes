package com.gaurav.cloudmessaging.jpa.criteria.constants;

import lombok.Getter;

@Getter
public enum Appender {
    AND("and"), OR("or");

    private String value;

    Appender(String value) {
        this.value = value;
    }
}
