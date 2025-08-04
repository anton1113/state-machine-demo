package com.arash.edu.statemachinedemo.repository;

import com.arash.edu.statemachinedemo.domain.db.JpaStateContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaStateContextRepository extends JpaRepository<JpaStateContext, UUID> {
}
