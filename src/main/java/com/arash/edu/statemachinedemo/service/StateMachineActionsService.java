package com.arash.edu.statemachinedemo.service;

import com.arash.edu.statemachinedemo.domain.db.SearchResponse;
import com.arash.edu.statemachinedemo.dto.ai.CarSearchFiltersStructuredOutput;
import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import com.arash.edu.statemachinedemo.repository.SearchResponseRepository;
import com.arash.edu.statemachinedemo.service.ai.FilterExtractionService;
import com.arash.edu.statemachinedemo.service.link.CarSearchLinkGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@WithStateMachine
public class StateMachineActionsService {

    private final FilterExtractionService filterExtractionService;
    private final CarSearchLinkGenerator carSearchLinkGenerator;
    private final SearchResponseRepository searchResponseRepository;

    @OnTransition(target = "FILTERS_EXTRACTION")
    public void onFiltersExtractionEntry(StateMachine<States, Events> stateMachine) {
        String message = (String) stateMachine.getExtendedState().getVariables().get("message");
        CarSearchFiltersStructuredOutput carSearchFilters = filterExtractionService.extractFilters(message);
        stateMachine.getExtendedState().getVariables().put("filters", carSearchFilters);
        stateMachine.sendEvent(Events.FILTERS_EXTRACTED);
    }

    @OnTransition(source = "RESPONSE_BUILDING")
    public void onResponseBuildingEntry(StateMachine<States, Events> stateMachine) {
        CarSearchFiltersStructuredOutput carSearchFilters = (CarSearchFiltersStructuredOutput)
                stateMachine.getExtendedState().getVariables().get("filters");
        String link = carSearchLinkGenerator.generateCarSearchLink(carSearchFilters.getCars(), carSearchFilters.getFilters());
        stateMachine.getExtendedState().getVariables().put("link", link);
    }

    @OnTransition(source = "RESPONSE_PERSISTING")
    public void onResponsePersistingEntry(StateMachine<States, Events> stateMachine) {
        String link = (String) stateMachine.getExtendedState().getVariables().get("link") ;
        searchResponseRepository.save(new SearchResponse(UUID.randomUUID(), link));
    }
}
