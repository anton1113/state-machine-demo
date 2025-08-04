package com.arash.edu.statemachinedemo.service.sm;

import com.arash.edu.statemachinedemo.domain.db.JpaState;
import com.arash.edu.statemachinedemo.domain.db.JpaStateContext;
import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import com.arash.edu.statemachinedemo.repository.JpaStateContextRepository;
import com.arash.edu.statemachinedemo.repository.JpaStateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.DefaultExtendedState;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class JpaStateMachineService {

    private final JpaStateRepository jpaStateRepository;
    private final JpaStateContextRepository jpaStateContextRepository;

    public void persistState(StateMachine<States, Events> stateMachine) {
        log.debug("Persisting state {} for state machine with id {}", stateMachine.getState().getId(), stateMachine.getUuid());
        JpaState jpaState = new JpaState(stateMachine.getUuid(), stateMachine.getState().getId().name());
        jpaStateRepository.save(jpaState);
    }

    public void persistContext(StateMachine<States, Events> stateMachine) {
        log.debug("Persisting context {} for state machine with id {}", stateMachine.getExtendedState().getVariables(), stateMachine.getUuid());
        JpaStateContext jpaStateContext = new JpaStateContext(stateMachine.getUuid(), stateMachine.getExtendedState().getVariables());
        jpaStateContextRepository.save(jpaStateContext);
    }

    public StateMachine<States, Events> restore(StateMachine<States, Events> stateMachine) {
        log.debug("Restoring statemachine with id {}", stateMachine.getUuid());
        UUID id = stateMachine.getUuid();
        jpaStateRepository.findById(id).ifPresent(
                state -> {
                    Map<Object, Object> context = jpaStateContextRepository.findById(id)
                            .map(JpaStateContext::getContext)
                            .orElseGet(Collections::emptyMap);
                    stateMachine.getStateMachineAccessor().doWithAllRegions(
                            access -> {
                                access.resetStateMachine(
                                        new DefaultStateMachineContext<>(
                                                States.valueOf(state.getState()),
                                                null,
                                                null,
                                                new DefaultExtendedState(context)
                                        )
                                );
                            }
                    );
                }
        );
        return stateMachine;
    }

    public void destroy(StateMachine<States, Events> stateMachine) {
        log.debug("Destroying statemachine with id {}", stateMachine.getUuid());
        jpaStateRepository.deleteById(stateMachine.getUuid());
        jpaStateContextRepository.deleteById(stateMachine.getUuid());
    }
}
