package com.arash.edu.statemachinedemo.config;

import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import com.arash.edu.statemachinedemo.listener.StateMachineListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends StateMachineConfigurerAdapter<States, Events> {

    @Autowired
    private StateMachineRuntimePersister<States, Events, String> persister;

    @Autowired
    private StateMachineListener stateMachineListener;

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(stateMachineListener)
                .and()
                .withPersistence()
                .runtimePersister(persister);
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
                .withStates()
                .initial(States.INITIAL)
                .state(States.FILTERS_EXTRACTION)
                .state(States.RESPONSE_BUILDING)
                .end(States.RESPONSE_PERSISTING);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal().source(States.INITIAL).target(States.FILTERS_EXTRACTION).event(Events.INITIALIZED)
                .and()
                .withExternal().source(States.FILTERS_EXTRACTION).target(States.RESPONSE_BUILDING).event(Events.FILTERS_EXTRACTED)
                .and()
                .withExternal().source(States.RESPONSE_BUILDING).target(States.RESPONSE_PERSISTING).event(Events.RESPONSE_BUILT);
    }
}
