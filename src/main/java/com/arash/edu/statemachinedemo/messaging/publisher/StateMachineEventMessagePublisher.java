package com.arash.edu.statemachinedemo.messaging.publisher;

import com.arash.edu.statemachinedemo.config.RabbitMqConfig;
import com.arash.edu.statemachinedemo.enums.Events;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class StateMachineEventMessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendEvent(Events event) {
        log.info("Sending event {}", event);
        rabbitTemplate.convertAndSend(RabbitMqConfig.STATE_MACHINE_QUEUE, event);
    }
}
