package com.arash.edu.statemachinedemo.service;

import com.arash.edu.statemachinedemo.dto.ai.CarSearchFiltersStructuredOutput;
import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import com.arash.edu.statemachinedemo.messaging.message.CustomerMessage;
import com.arash.edu.statemachinedemo.messaging.message.StateMachineEventMessage;
import com.arash.edu.statemachinedemo.messaging.publisher.CustomerMessagePublisher;
import com.arash.edu.statemachinedemo.messaging.publisher.StateMachineEventMessagePublisher;
import com.arash.edu.statemachinedemo.service.ai.FilterExtractionService;
import com.arash.edu.statemachinedemo.service.link.CarSearchLinkGenerator;
import com.arash.edu.statemachinedemo.service.sm.JpaStateMachinePersister;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.OnStateEntry;
import org.springframework.statemachine.annotation.WithStateMachine;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@WithStateMachine
public class StateMachineActionService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final FilterExtractionService filterExtractionService;
    private final CarSearchLinkGenerator carSearchLinkGenerator;

    private final StateMachineEventMessagePublisher stateMachineEventMessagePublisher;
    private final CustomerMessagePublisher customerMessagePublisher;
    private final JpaStateMachinePersister jpaStateMachinePersister;

    @OnStateEntry(target = "FILTERS_EXTRACTION")
    public void onFiltersExtractionEntry(StateMachine<States, Events> stateMachine) {
        String message = (String) stateMachine.getExtendedState().getVariables().get("message");
        CarSearchFiltersStructuredOutput carSearchFilters = filterExtractionService.extractFilters(message);
        stateMachine.getExtendedState().getVariables().put("filters", carSearchFilters);
        stateMachineEventMessagePublisher.sendEvent(new StateMachineEventMessage(stateMachine.getUuid(), Events.FILTERS_EXTRACTED));
        stateMachine.stop();
    }

    @OnStateEntry(target = "LINK_GENERATION")
    public void onLinkGenerationEntry(StateMachine<States, Events> stateMachine) {
        CarSearchFiltersStructuredOutput carSearchFilters = jsonCast(
                stateMachine.getExtendedState().getVariables().get("filters"),
                CarSearchFiltersStructuredOutput.class
        );
        String link = carSearchLinkGenerator.generateCarSearchLink(carSearchFilters.getCars(), carSearchFilters.getFilters());
        stateMachine.getExtendedState().getVariables().put("link", link);
        stateMachineEventMessagePublisher.sendEvent(new StateMachineEventMessage(stateMachine.getUuid(), Events.LINK_GENERATED));
        stateMachine.stop();
    }

    @OnStateEntry(target = "FINAL")
    public void onFinalEntry(StateMachine<States, Events> stateMachine) {
        UUID id = stateMachine.getUuid();
        String link = (String) stateMachine.getExtendedState().getVariables().get("link") ;
        customerMessagePublisher.sendEvent(new CustomerMessage(id, link));
        stateMachine.stop();
        jpaStateMachinePersister.destroy(stateMachine);
    }

    @SneakyThrows
    private <T> T jsonCast(Object obj, Class<T> type) {
        String json = OBJECT_MAPPER.writeValueAsString(obj);
        return OBJECT_MAPPER.readValue(json, type);
    }
}
