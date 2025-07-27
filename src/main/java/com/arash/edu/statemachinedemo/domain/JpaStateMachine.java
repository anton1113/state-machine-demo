package com.arash.edu.statemachinedemo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
public class JpaStateMachine {

    @Id
    private UUID id;

    private String state;

    private String context;

    public JpaStateMachine(UUID id) {
        this.id = id;
    }
}
