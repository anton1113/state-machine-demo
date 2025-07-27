package com.arash.edu.statemachinedemo.service;

import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import com.arash.edu.statemachinedemo.repository.JpaStateMachineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatbotService {

    private final StateMachineFactory<States, Events> factory;
    private final JpaStateMachineRepository jpaStateMachineRepository;

    public String processMessage(String message, UUID sessionId) {
        StateMachine<States, Events> stateMachine = factory.getStateMachine(sessionId);
        stateMachine.start();
        stateMachine.sendEvent(Events.INITIALIZED);
        return "foo";
    }
}
