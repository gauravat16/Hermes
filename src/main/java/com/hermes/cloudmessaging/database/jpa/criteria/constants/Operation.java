package com.hermes.cloudmessaging.database.jpa.criteria.constants;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Operation {

    EQUALS("="),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    GREATER_THAN_EQUAL_TO(">="),
    LESS_THAN_EQUAL_TO("<="),
    LIKE("%%");

    private static Map<String, Operation> stringOperationMap;

    private String value;

    static {
        stringOperationMap = new HashMap<>();
        for (Operation operation : values()) {
            stringOperationMap.put(operation.getValue(), operation);
        }
    }

    Operation(String value) {
        this.value = value;
    }

    public static Operation getOperationForString(String ops) {
        return stringOperationMap.get(ops);
    }

}
