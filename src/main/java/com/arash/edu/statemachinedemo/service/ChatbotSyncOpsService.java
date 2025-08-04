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
public class ChatbotSyncOpsService {

    private final StateMachineService stateMachineService;
    private final SearchResultService searchResultService;

    public String processMessage(String message, UUID sessionId) {
        StateMachine<States, Events> stateMachine = stateMachineService.getStateMachine(sessionId);
        States state = stateMachine.getState().getId();

        if (state != States.INITIAL) {
            throw new IllegalStateException("Unexpected state " + state);
        }

        UUID searchId = UUID.randomUUID();
        stateMachine.getExtendedState().getVariables().putAll(
                Map.of(
                        "message", message,
                        "searchId", searchId,
                        "persistSearchResult", true
                )
        );
        stateMachine.sendEvent(Mono.just(new GenericMessage<>(Events.INITIALIZED))).subscribe();
        SearchResult searchResult = searchResultService.waitForSearchResult(searchId, Duration.ofSeconds(500));
        return searchResult.getResult();
    }
}
