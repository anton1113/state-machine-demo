package com.arash.edu.statemachinedemo.service.action;

import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import com.arash.edu.statemachinedemo.service.SearchResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class SearchResultPersistenceAction extends AbstractAction {

    private final SearchResultService searchResultService;

    @Override
    void doExecute(StateContext<States, Events> context) {
        boolean persistSearchResult = context.getExtendedState().get("persistSearchResult", Boolean.class);
        UUID searchId = UUID.fromString(context.getExtendedState().get("searchId", String.class));
        UUID id = context.getStateMachine().getUuid();
        String link = context.getExtendedState().get("link", String.class);

        if (persistSearchResult) {
            searchResultService.persistSearchResult(searchId, id, link);
        }
    }
}
