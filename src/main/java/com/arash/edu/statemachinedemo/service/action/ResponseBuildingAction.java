package com.arash.edu.statemachinedemo.service.action;

import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import com.arash.edu.statemachinedemo.messaging.message.CustomerMessage;
import com.arash.edu.statemachinedemo.messaging.message.StateMachineEventMessage;
import com.arash.edu.statemachinedemo.messaging.publisher.CustomerMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class ResponseBuildingAction extends AbstractAction {

    private final CustomerMessagePublisher customerMessagePublisher;

    @Override
    void doExecute(StateContext<States, Events> context) {
        UUID id = context.getStateMachine().getUuid();
        String link = context.getExtendedState().get("link", String.class);
        customerMessagePublisher.sendEvent(new CustomerMessage(id, link));
        stateMachineEventMessagePublisher.sendEvent(new StateMachineEventMessage(id, Events.RESPONSE_BUILT));
    }
}
