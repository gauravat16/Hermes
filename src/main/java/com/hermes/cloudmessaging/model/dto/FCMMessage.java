package com.hermes.cloudmessaging.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
@NoArgsConstructor
public class FCMMessage {

    private Map<String, Object> data;

    private String to;

    @JsonAlias("collapse_key")
    private String collapseKey;

    public FCMMessage(Map<String, Object> data, String to, String collapseKey) {
        this.data = data;
        this.to = to;
        this.collapseKey = collapseKey;
    }

    public static FCMMessage of(Map<String, Object> data, String to, String collapseKey) {
        return new FCMMessage(data, to, collapseKey);
    }

}
