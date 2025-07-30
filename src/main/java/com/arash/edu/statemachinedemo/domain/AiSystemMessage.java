package com.arash.edu.statemachinedemo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class AiSystemMessage {

    @Id
    private UUID id;

    private String key;

    @Column(columnDefinition = "TEXT")
    private String message;
}
