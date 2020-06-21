package com.hermes.cloudmessaging.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
public class FCMMessage {

    private Map<String, String> data;

    private String to;

    @JsonAlias("collapse_key")
    private String collapseKey;

    public FCMMessage(Map<String, String> data, String to, String collapseKey) {
        this.data = data;
        this.to = to;
        this.collapseKey = collapseKey;
    }

    public static FCMMessage of(Map<String, String> data, String to, String collapseKey) {
        return new FCMMessage(data, to, collapseKey);
    }

}
