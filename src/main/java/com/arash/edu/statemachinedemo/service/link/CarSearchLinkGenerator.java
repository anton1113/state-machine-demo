package com.arash.edu.statemachinedemo.service.link;

import com.arash.edu.statemachinedemo.domain.Filter;
import com.arash.edu.statemachinedemo.domain.MakeModelsFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class CarSearchLinkGenerator {

    private final QueryParamBuilder queryParamBuilder;

    public String generateCarSearchLink(List<MakeModelsFilter> makeModelsFilters, List<Filter> carSearchFilters) {
        List<QueryParam> queryParams = new ArrayList<>();

        if (makeModelsFilters != null && !makeModelsFilters.isEmpty()) {
            for (int i = 0; i < makeModelsFilters.size(); i++) {
                queryParams.addAll(queryParamBuilder.toMakeModelsFilterQueryParams(makeModelsFilters.get(i), i));
            }
        }

        if (carSearchFilters != null && !carSearchFilters.isEmpty()) {
            for (Filter carSearchFilter: carSearchFilters) {
                queryParams.addAll(queryParamBuilder.toCarSearchFilterQueryParams(carSearchFilter));
            }
        }

        String link = buildSearchLink(queryParams);
        log.info("Generated search link {}", link);
        return link;
    }

    private String buildSearchLink(List<QueryParam> queryParams) {
        String market = "ua";
        String baseSearchUrl = "%s/%s/search/".formatted("anton-cars.com", market);
        if (queryParams == null || queryParams.isEmpty()) {
            return baseSearchUrl;
        }

        String queryParamsStr = queryParams.stream()
                .filter(Objects::nonNull)
                .map(QueryParam::toString)
                .collect(Collectors.joining("&"));
        return "%s?%s".formatted(baseSearchUrl, queryParamsStr);
    }
}
