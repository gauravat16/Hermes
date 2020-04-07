package com.gaurav.cloudmessaging.jpa.criteria.dto;

import com.gaurav.cloudmessaging.jpa.criteria.constants.Appender;
import com.gaurav.cloudmessaging.jpa.criteria.constants.Operation;
import lombok.Data;

@Data
public class SearchCriteria {
    private String key;
    private String value;
    private Operation operation;
    private Appender appender;

    private SearchCriteria(String key, String value, Operation operation, Appender appender) {
        this.key = key;
        this.value = value;
        this.operation = operation;
        this.appender = appender;
    }


    public static SearchCriteria of(String key, String value, Operation operation, Appender appender) {
        return new SearchCriteria(key, value, operation, appender);
    }
}
