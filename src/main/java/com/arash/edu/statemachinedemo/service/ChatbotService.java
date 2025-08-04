package com.arash.edu.statemachinedemo.service;

import com.arash.edu.statemachinedemo.domain.db.SearchResult;
import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import com.arash.edu.statemachinedemo.service.sm.StateMachineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatbotService {

    private final StateMachineService stateMachineService;
    private final SearchResultService searchResultService;

    public void processMessage(String message, UUID sessionId) {
        StateMachine<States, Events> stateMachine = stateMachineService.getStateMachine(sessionId);
        log.info("State={}, variables={}", stateMachine.getState().getId(), stateMachine.getExtendedState().getVariables());
        States state = stateMachine.getState().getId();
        if (States.INITIAL == state) {
            initializeSearch(stateMachine, message, false);
        }
    }

    public String processMessageSync(String message, UUID sessionId) {
        StateMachine<States, Events> stateMachine = stateMachineService.getStateMachine(sessionId);
        log.info("State={}, variables={}", stateMachine.getState().getId(), stateMachine.getExtendedState().getVariables());
        States state = stateMachine.getState().getId();
        if (States.INITIAL == state) {
            UUID searchId = initializeSearch(stateMachine, message, true);
            SearchResult searchResult = searchResultService.waitForSearchResult(searchId, Duration.ofSeconds(5));
            return searchResult.getResult();
        } else {
            throw new IllegalStateException("Unexpected state " + state);
        }
    }

    private UUID initializeSearch(StateMachine<States, Events> stateMachine, String message, boolean persistSearchResult) {
        UUID searchId = UUID.randomUUID();
        stateMachine.getExtendedState().getVariables().putAll(
                Map.of(
                        "message", message,
                        "searchId", searchId,
                        "persistSearchResult", persistSearchResult
                )
        );
        stateMachine.sendEvent(Mono.just(new GenericMessage<>(Events.INITIALIZED))).subscribe();
        return searchId;
    }
}
