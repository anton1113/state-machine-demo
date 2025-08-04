package com.arash.edu.statemachinedemo.service.action;

import com.arash.edu.statemachinedemo.dto.ai.CarSearchFiltersStructuredOutput;
import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import com.arash.edu.statemachinedemo.messaging.message.StateMachineEventMessage;
import com.arash.edu.statemachinedemo.service.link.CarSearchLinkGenerator;
import com.arash.edu.statemachinedemo.util.JsonTypeCastUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class LinkGenerationAction extends AbstractAction {

    private final CarSearchLinkGenerator carSearchLinkGenerator;

    @Override
    void doExecute(StateContext<States, Events> context) {
        UUID id = context.getStateMachine().getUuid();
        Object filtersRaw = context.getExtendedState().getVariables().get("filters");
        CarSearchFiltersStructuredOutput filters = JsonTypeCastUtil.cast(filtersRaw, CarSearchFiltersStructuredOutput.class);
        String link = carSearchLinkGenerator.generateCarSearchLink(filters.getCars(), filters.getFilters());
        context.getExtendedState().getVariables().put("link", link);
        stateMachineEventMessagePublisher.sendEvent(new StateMachineEventMessage(id, Events.LINK_GENERATED));
    }
}
