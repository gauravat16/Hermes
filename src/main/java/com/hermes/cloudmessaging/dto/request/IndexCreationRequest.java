package com.hermes.cloudmessaging.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class IndexCreationRequest {

    public enum IndexType {
        SINGLE, COMPOUND, TEXT
    }

    public enum Sort {
        ASC, DESC, VOID
    }

    @Data
    @NoArgsConstructor
    public static class Pair {
        private List<String> elementNames;
        private Sort direction;
    }

    private List<Pair> pairs;
    private IndexType indexType;
}
