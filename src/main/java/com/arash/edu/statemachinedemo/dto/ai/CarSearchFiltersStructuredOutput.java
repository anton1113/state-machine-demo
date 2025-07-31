package com.arash.edu.statemachinedemo.dto.ai;

import com.arash.edu.statemachinedemo.domain.Filter;
import com.arash.edu.statemachinedemo.domain.MakeModelsFilter;
import lombok.Data;

import java.util.List;

@Data
public class CarSearchFiltersStructuredOutput {

    private List<MakeModelsFilter> cars;
    private List<Filter> filters;
}
