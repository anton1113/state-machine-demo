package com.arash.edu.statemachinedemo.service.link;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
class QueryParam {

    private String key;
    private String value;

    @Override
    public String toString() {
        return "%s=%s".formatted(key, value);
    }
}
