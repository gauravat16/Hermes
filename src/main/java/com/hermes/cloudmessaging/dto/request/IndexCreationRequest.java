package com.hermes.cloudmessaging.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class IndexCreationRequest {

    public enum Sort {
        ASC, DESC
    }

    @Data
    @NoArgsConstructor
    public static class Pair {
        private List<String> a;
        private Sort b;
    }

    List<Pair> pairs;
}
