package com.arash.edu.statemachinedemo.repository;

import com.arash.edu.statemachinedemo.domain.db.JpaState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaStateRepository extends JpaRepository<JpaState, UUID> {
}
