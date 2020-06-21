package com.hermes.cloudmessaging.jpa.criteria.constants;

import lombok.Getter;

@Getter
public enum Operation {

    EQUALS("="),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    GREATER_THAN_EQUAL_TO(">="),
    LESS_THAN_EQUAL_TO("<="),
    LIKE("%%");

    private String value;

    Operation(String value) {
        this.value = value;
    }

}
