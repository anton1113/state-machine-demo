package com.arash.edu.statemachinedemo.service.action;

import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import com.arash.edu.statemachinedemo.service.sm.JpaStateMachineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class FinalAction extends AbstractAction {

    private final JpaStateMachineService jpaStateMachineService;

    @Override
    void doExecute(StateContext<States, Events> context) {
        UUID id = context.getStateMachine().getUuid();
        log.info("Destroying state machine with id {}", id);
        jpaStateMachineService.destroy(context.getStateMachine());
    }
}
