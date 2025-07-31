package com.arash.edu.statemachinedemo.service;

import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import com.arash.edu.statemachinedemo.service.sm.StateMachineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatbotService {

    private final StateMachineService stateMachineService;

    public String processMessage(String message, UUID sessionId) {
        StateMachine<States, Events> stateMachine = stateMachineService.getStateMachine(sessionId);
        log.info("State={}, variables={}", stateMachine.getState().getId(), stateMachine.getExtendedState().getVariables());
        States state = stateMachine.getState().getId();
        if (States.INITIAL == state) {
            stateMachine.getExtendedState().getVariables().put("message", message);
            stateMachine.sendEvent(Events.INITIALIZED);
        }
        return "OK";
    }
}
