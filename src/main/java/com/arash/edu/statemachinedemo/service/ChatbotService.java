package com.arash.edu.statemachinedemo.service;

import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatbotService {

    private final StateMachineService<States, Events> stateMachineService;

    public String processMessage(String message, UUID sessionId) {
        StateMachine<States, Events> stateMachine = stateMachineService.acquireStateMachine(sessionId.toString());
        stateMachine.start();
        log.info("State={}, variables={}", stateMachine.getState().getId(), stateMachine.getExtendedState().getVariables());
        stateMachine.getExtendedState().getVariables().put("message", message);
        stateMachine.sendEvent(buildEvent(stateMachine.getState().getId()));
        return buildMessage(stateMachine.getState().getId());
    }

    private String buildMessage(States state) {
        return switch (state) {
            case INITIAL -> "Hello!";
            case ASK_NAME -> "What is your name!";
            case ASK_AGE -> "How old are you?!";
            case FINAL -> "Bye!";
        };
    }

    private Events buildEvent(States state) {
        return switch (state) {
            case INITIAL -> Events.INITIALIZED;
            case ASK_NAME -> Events.NAME_ASKED;
            case ASK_AGE -> Events.AGE_ASKED;
            case FINAL -> Events.FINALIZED;
        };
    }
}
