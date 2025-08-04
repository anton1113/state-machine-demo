package com.arash.edu.statemachinedemo.service.action;

import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import com.arash.edu.statemachinedemo.messaging.publisher.StateMachineEventMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@Slf4j
abstract class AbstractAction implements Action<States, Events> {

    private static final boolean DEFAULT_AUTO_STOP_VALUE = true;

    @Autowired
    StateMachineEventMessagePublisher stateMachineEventMessagePublisher;

    @Override
    public final void execute(StateContext<States, Events> context) {
        doExecute(context);
        if (autoStop()) {
            context.getStateMachine().stopReactively().subscribe();
        }
    }

    abstract void doExecute(StateContext<States, Events> context);

    boolean autoStop() {
        return DEFAULT_AUTO_STOP_VALUE;
    }
}
