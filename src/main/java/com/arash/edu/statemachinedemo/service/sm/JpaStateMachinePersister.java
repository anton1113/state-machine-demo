package com.arash.edu.statemachinedemo.service.sm;

import com.arash.edu.statemachinedemo.domain.db.JpaStateMachine;
import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import com.arash.edu.statemachinedemo.repository.JpaStateMachineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.support.DefaultExtendedState;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class JpaStateMachinePersister implements StateMachinePersister<States, Events, Map<Object, Object>> {

    private final JpaStateMachineRepository jpaStateMachineRepository;

    @Override
    public void persist(StateMachine<States, Events> stateMachine, Map<Object, Object> contextObj) {
        log.debug("Persisting statemachine with id {} and state {}", stateMachine.getUuid(), stateMachine.getState().getId());
        JpaStateMachine jpaStateMachine = new JpaStateMachine();
        jpaStateMachine.setId(stateMachine.getUuid());
        jpaStateMachine.setState(stateMachine.getState().getId());
        jpaStateMachine.setContext(stateMachine.getExtendedState().getVariables());
        jpaStateMachineRepository.save(jpaStateMachine);
    }

    @Override
    public StateMachine<States, Events> restore(StateMachine<States, Events> stateMachine, Map<Object, Object> contextObj) {
        log.debug("Restoring statemachine with id {} and state {}", stateMachine.getId(), stateMachine.getState().getId());
        jpaStateMachineRepository.findById(stateMachine.getUuid()).ifPresent(
                jpaStateMachine -> {
                    stateMachine.getStateMachineAccessor().doWithAllRegions(
                            access -> {
                                access.resetStateMachine(
                                        new DefaultStateMachineContext<>(
                                                jpaStateMachine.getState(),
                                                null,
                                                null,
                                                new DefaultExtendedState(jpaStateMachine.getContext())
                                        )
                                );
                            }
                    );
                }
        );
        return stateMachine;
    }
}
