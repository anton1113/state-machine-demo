package com.arash.edu.statemachinedemo.messaging.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerMessage {

    private UUID sessionId;
    private String text;
}
