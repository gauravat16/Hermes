package com.hermes.cloudmessaging.model.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@ToString
public class FCMResponse {

    @JsonAlias("multicast_id")
    private String multicastId;

    private String success;

    private String failure;

    @JsonAlias("canonical_ids")
    private String canonicalIds;

    private List<Map<String, String>> results;
}
