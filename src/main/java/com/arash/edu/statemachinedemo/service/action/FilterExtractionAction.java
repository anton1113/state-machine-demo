package com.arash.edu.statemachinedemo.service.action;

import com.arash.edu.statemachinedemo.dto.ai.CarSearchFiltersStructuredOutput;
import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import com.arash.edu.statemachinedemo.messaging.message.StateMachineEventMessage;
import com.arash.edu.statemachinedemo.service.ai.FilterExtractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class FilterExtractionAction extends AbstractAction {

    private final FilterExtractionService filterExtractionService;

    @Override
    void doExecute(StateContext<States, Events> context) {
        UUID id = context.getStateMachine().getUuid();
        String message = (String) context.getExtendedState().getVariables().get("message");
        CarSearchFiltersStructuredOutput carSearchFilters = filterExtractionService.extractFilters(message);
        context.getStateMachine().getExtendedState().getVariables().put("filters", carSearchFilters);
        stateMachineEventMessagePublisher.sendEvent(new StateMachineEventMessage(id, Events.FILTERS_EXTRACTED));
    }
}
