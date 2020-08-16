package com.hermes.cloudmessaging.jpa.criteria.dto;

import com.hermes.cloudmessaging.jpa.criteria.constants.Appender;
import com.hermes.cloudmessaging.jpa.criteria.constants.Operation;
import lombok.Data;

@Data
public class SearchCriteria {
    public enum ValueType {
        String, Long, Double, Boolean;
    }

    private String key;
    private String value;
    private Operation operation;
    private Appender appender;
    private ValueType valueType;

    private SearchCriteria(String key, String value, Operation operation, Appender appender, ValueType valueType) {
        this.key = key;
        this.value = value;
        this.operation = operation;
        this.appender = appender;
        this.valueType = valueType;
    }


    public static SearchCriteria of(String key, String value, Operation operation, Appender appender, ValueType valueType) {
        return new SearchCriteria(key, value, operation, appender, valueType);
    }

    public Object getValue() {
        switch (valueType) {
            case Boolean:
                return value.equalsIgnoreCase("true");
            case Long:
                return Long.parseLong(value);
            case Double:
                return Double.parseDouble(value);
            default:
            case String:
                return value;
        }
    }

    public static ValueType getSearchCriteriaForValue(String value) {

        if (value.startsWith("'") && value.endsWith("'")) {
            return SearchCriteria.ValueType.String;
        } else {
            value = value.trim();
            if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                return SearchCriteria.ValueType.Boolean;
            } else {
                return SearchCriteria.ValueType.Double;
            }
        }
    }
}
