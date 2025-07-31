package com.arash.edu.statemachinedemo.messaging.listener;

import com.arash.edu.statemachinedemo.config.RabbitMqConfig;
import com.arash.edu.statemachinedemo.messaging.message.CustomerMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerMessageListener {

    @RabbitListener(queues = RabbitMqConfig.SSE_QUEUE)
    public void onMessage(CustomerMessage message) {
        log.info("Received CustomerMessage {}", message);
        log.info("CHATBOT RESPONSE IS: {} for session {}", message.getText(), message.getSessionId());
    }
}
