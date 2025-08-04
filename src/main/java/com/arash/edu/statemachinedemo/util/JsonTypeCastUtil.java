package com.arash.edu.statemachinedemo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonTypeCastUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    public static <T> T cast(Object obj, Class<T> type) {
        String json = OBJECT_MAPPER.writeValueAsString(obj);
        return OBJECT_MAPPER.readValue(json, type);
    }
}
