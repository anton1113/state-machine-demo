package com.arash.edu.statemachinedemo.repository;

import com.arash.edu.statemachinedemo.domain.AiStructuredOutput;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AiStructuredOutputRepository extends JpaRepository<AiStructuredOutput, UUID> {

    Optional<AiStructuredOutput> findByKey(String key);
}
