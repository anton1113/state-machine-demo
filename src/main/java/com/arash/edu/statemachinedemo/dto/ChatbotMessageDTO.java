package com.arash.edu.statemachinedemo.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ChatbotMessageDTO {

    private UUID sessionId;
    private String message;
}
