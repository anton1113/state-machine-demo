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

    private final JpaStateMachinePersister jpaStateMachinePersister;

    @Override
    public void stateContext(StateContext<States, Events> stateContext) {
        if (stateContext.getStateMachine().getState() == null) {
            // state machine is not started
            return;
        }
        switch (stateContext.getStage()) {
            case TRANSITION_END: {
                if (stateContext.getTransition() != null && stateContext.getTransition().getSource() != null) {
                    log.info("Transition end, source {}, target {}", stateContext.getTransition().getSource().getId(), stateContext.getTransition().getTarget().getId());
                    jpaStateMachinePersister.persist(stateContext.getStateMachine(), stateContext.getExtendedState().getVariables());
                }
                break;
            }
            case EXTENDED_STATE_CHANGED: {
                log.info("Extended state changed, {}", stateContext.getStateMachine().getExtendedState().getVariables());
                jpaStateMachinePersister.persist(stateContext.getStateMachine(), stateContext.getExtendedState().getVariables());
                break;
            }
        }
    }
}
