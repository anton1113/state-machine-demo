package com.arash.edu.statemachinedemo.service.sm;

import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class StateMachineListener extends StateMachineListenerAdapter<States, Events> {

    private final JpaStateMachineService jpaStateMachineService;

    @Override
    public void stateContext(StateContext<States, Events> stateContext) {
        if (stateContext.getStateMachine().getState() == null) {
            // state machine is not started
            return;
        }
        switch (stateContext.getStage()) {
            case STATE_ENTRY: {
                States state = stateContext.getStateMachine().getState().getId();
                log.info("State entry {}", state);
                if (stateContext.getStateMachine().getState().getId() == States.INITIAL) {
                    // skip persistence
                } else {
                    jpaStateMachineService.persistState(stateContext.getStateMachine());
                }
                break;
            }
            case EXTENDED_STATE_CHANGED: {
                log.info("Extended state changed, {}", stateContext.getStateMachine().getExtendedState().getVariables());
                jpaStateMachineService.persistContext(stateContext.getStateMachine());
                break;
            }
        }
    }
}
