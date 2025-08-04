package com.arash.edu.statemachinedemo.service.action;

import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import com.arash.edu.statemachinedemo.messaging.message.CustomerMessage;
import com.arash.edu.statemachinedemo.messaging.publisher.CustomerMessagePublisher;
import com.arash.edu.statemachinedemo.service.SearchResultService;
import com.arash.edu.statemachinedemo.service.sm.JpaStateMachinePersister;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class FinalAction extends AbstractAction {

    private final CustomerMessagePublisher customerMessagePublisher;
    private final JpaStateMachinePersister jpaStateMachinePersister;
    private final SearchResultService searchResultService;

    @Override
    void doExecute(StateContext<States, Events> context) {
        boolean persistSearchResult = context.getExtendedState().get("persistSearchResult", Boolean.class);
        UUID searchId = UUID.fromString(context.getExtendedState().get("searchId", String.class));
        UUID id = context.getStateMachine().getUuid();
        String link = context.getExtendedState().get("link", String.class);

        customerMessagePublisher.sendEvent(new CustomerMessage(id, link));
        jpaStateMachinePersister.destroy(context.getStateMachine());

        if (persistSearchResult) {
            searchResultService.persistSearchResult(searchId, id, link);
        }
    }
}
