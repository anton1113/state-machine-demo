package com.arash.edu.statemachinedemo.messaging.listener;

import com.arash.edu.statemachinedemo.config.RabbitMqConfig;
import com.arash.edu.statemachinedemo.enums.Events;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StateMachineEventMessageListener {

    @RabbitListener(queues = RabbitMqConfig.STATE_MACHINE_QUEUE)
    public void receive(Events event) {
        log.info("Received event {}", event);
    }
}
