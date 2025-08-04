package com.arash.edu.statemachinedemo.messaging.listener;

import com.arash.edu.statemachinedemo.config.RabbitMqConfig;
import com.arash.edu.statemachinedemo.enums.Events;
import com.arash.edu.statemachinedemo.enums.States;
import com.arash.edu.statemachinedemo.messaging.message.StateMachineEventMessage;
import com.arash.edu.statemachinedemo.service.sm.StateMachineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class StateMachineEventMessageListener {

    private final StateMachineService stateMachineService;

    @RabbitListener(queues = RabbitMqConfig.STATE_MACHINE_QUEUE)
    public void onMessage(StateMachineEventMessage message) {
        log.info("Received StateMachineEventMessage {}", message);
        StateMachine<States, Events> stateMachine = stateMachineService.getStateMachine(message.getId());
        stateMachine.sendEvent(Mono.just(new GenericMessage<>(message.getEvent()))).subscribe();
    }
}
