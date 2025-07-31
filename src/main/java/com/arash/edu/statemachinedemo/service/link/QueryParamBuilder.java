package com.arash.edu.statemachinedemo.service.link;

import com.arash.edu.statemachinedemo.domain.Filter;
import com.arash.edu.statemachinedemo.domain.MakeModelsFilter;
import com.arash.edu.statemachinedemo.util.SnakeCaseUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.CaseUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
class QueryParamBuilder {

    private static final String URL_ENCODED_SPACE = "%20";
    private static final String MAKE_KEY = "brand%d";
    private static final String MODELS_KEY = "models%d";

    List<QueryParam> toMakeModelsFilterQueryParams(@NonNull MakeModelsFilter makeModelsFilter, int index) {
        String makeKey = MAKE_KEY.formatted(index);
        String makeValue = SnakeCaseUtil.toSnakeCase(makeModelsFilter.getMake());
        QueryParam makeQueryParam = new QueryParam(makeKey, makeValue);
        if (makeModelsFilter.getModels() == null || makeModelsFilter.getModels().isEmpty()) {
            return List.of(makeQueryParam);
        }

        String modelsKey = MODELS_KEY.formatted(index);
        String modelsValue = makeModelsFilter.getModels().stream()
                .map(s -> s.replaceAll(" ", URL_ENCODED_SPACE))
                .collect(Collectors.joining(","));
        QueryParam modelsQueryParam = new QueryParam(modelsKey, modelsValue);
        return List.of(makeQueryParam, modelsQueryParam);
    }

    List<QueryParam> toCarSearchFilterQueryParams(@NonNull Filter filter) {
        String key = CaseUtils.toCamelCase(filter.getName(), false);
        return filter.getValues().stream()
                .map(val -> new QueryParam(key, val))
                .collect(Collectors.toList());

    }
}
