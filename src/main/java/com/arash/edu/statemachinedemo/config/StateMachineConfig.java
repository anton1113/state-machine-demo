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
                .state(States.ASK_NAME)
                .state(States.ASK_AGE)
                .end(States.FINAL);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal().source(States.INITIAL).target(States.ASK_NAME).event(Events.INITIALIZED)
                .and()
                .withExternal().source(States.ASK_NAME).target(States.ASK_AGE).event(Events.NAME_ASKED)
                .and()
                .withExternal().source(States.ASK_AGE).target(States.FINAL).event(Events.AGE_ASKED);
    }
}
