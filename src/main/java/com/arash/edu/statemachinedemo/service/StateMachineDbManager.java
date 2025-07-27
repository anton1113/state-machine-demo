package com.arash.edu.statemachinedemo.service;

import com.arash.edu.statemachinedemo.constants.ContextVariables;
import com.arash.edu.statemachinedemo.domain.JpaStateMachine;
import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import com.arash.edu.statemachinedemo.repository.JpaStateMachineRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class StateMachineDbManager {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final JpaStateMachineRepository jpaStateMachineRepository;

    public void persist(StateMachine<States, Events> stateMachine, UUID sessionId) {
        stateMachine.getExtendedState().getVariables().put(ContextVariables.SESSION_ID, sessionId);
        JpaStateMachine jpaStateMachine = new JpaStateMachine(sessionId);
        jpaStateMachine.setState(stateMachine.getState().toString());
        jpaStateMachine.setContext(toJson(stateMachine.getExtendedState().getVariables()));
        jpaStateMachineRepository.save(jpaStateMachine);
    }

    private String toJson(Map<Object, Object> map) {
        try {
            return OBJECT_MAPPER.writeValueAsString(map);
        } catch (Exception e) {
            log.error("toJson failed", e);
            return null;
        }
    }
}
