package com.arash.edu.statemachinedemo.service.sm;

import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class StateMachineService {

    private final StateMachineFactory<States, Events> stateMachineFactory;
    private final JpaStateMachinePersister stateMachinePersister;

    public StateMachine<States, Events> getStateMachine(@NonNull UUID id) {
        log.debug("Get statemachine with id {}", id);
        StateMachine<States, Events> stateMachine = stateMachineFactory.getStateMachine(id);
        stateMachinePersister.restore(stateMachine, null);
        stateMachine.startReactively().subscribe();
        return stateMachine;
    }
}
