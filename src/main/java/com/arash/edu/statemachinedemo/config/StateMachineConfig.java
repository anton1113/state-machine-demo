package com.arash.edu.statemachinedemo.config;

import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import com.arash.edu.statemachinedemo.service.action.*;
import com.arash.edu.statemachinedemo.service.sm.StateMachineListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends StateMachineConfigurerAdapter<States, Events> {

    private final StateMachineListener stateMachineListener;

    private final FilterExtractionAction filterExtractionAction;
    private final LinkGenerationAction linkGenerationAction;
    private final ResponseBuildingAction responseBuildingAction;
    private final SearchResultPersistenceAction searchResultPersistenceAction;
    private final FinalAction finalAction;

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(false)
                .listener(stateMachineListener);
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
                .withStates()
                .initial(States.INITIAL)
                .stateEntry(States.FILTERS_EXTRACTION, filterExtractionAction)
                .stateEntry(States.LINK_GENERATION, linkGenerationAction)
                .stateEntry(States.RESPONSE_BUILDING, responseBuildingAction)
                .stateExit(States.RESPONSE_BUILDING, searchResultPersistenceAction)
                .stateEntry(States.FINAL, finalAction)
                .end(States.FINAL);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal().source(States.INITIAL).target(States.FILTERS_EXTRACTION).event(Events.INITIALIZED)
                .and()
                .withExternal().source(States.FILTERS_EXTRACTION).target(States.LINK_GENERATION).event(Events.FILTERS_EXTRACTED)
                .and()
                .withExternal().source(States.LINK_GENERATION).target(States.RESPONSE_BUILDING).event(Events.LINK_GENERATED)
                .and()
                .withExternal().source(States.RESPONSE_BUILDING).target(States.FINAL).event(Events.RESPONSE_BUILT);
    }
}
