package com.arash.edu.statemachinedemo.dto.ai;

import lombok.Data;

import java.util.List;

@Data
public class CarSearchFiltersStructuredOutput {

    private List<MakeModelsFilter> cars;
    private List<Filter> filters;

    @Data
    public static class MakeModelsFilter {

        private String make;
        private List<String> models;
    }

    @Data
    public static class Filter {

        private String name;
        private List<String> values;
    }
}
