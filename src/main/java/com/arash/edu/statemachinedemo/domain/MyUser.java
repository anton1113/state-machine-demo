package com.arash.edu.statemachinedemo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class MyUser {

    @Id
    private UUID id;

    private String name;

    private String age;
}
