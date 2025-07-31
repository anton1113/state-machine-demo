package com.arash.edu.statemachinedemo.repository;

import com.arash.edu.statemachinedemo.domain.db.JpaStateMachine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaStateMachineRepository extends JpaRepository<JpaStateMachine, UUID> {
}
