package com.hermes.cloudmessaging.database.jpa.criteria.constants;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Getter
public enum Appender {
    AND("$and"), OR("$or");

    private final String value;
    private static final Map<Appender, String> escapedStrMap = new HashMap<>();

    static {
        Arrays.stream(values()).forEach(appender -> escapedStrMap.put(appender, Pattern.quote(appender.getValue())));
    }

    Appender(String value) {
        this.value = value;
    }

    public static String getEscapedValue(Appender appender) {
        Assert.notNull(appender, "Appender passed was null");
        return escapedStrMap.get(appender);
    }
}
