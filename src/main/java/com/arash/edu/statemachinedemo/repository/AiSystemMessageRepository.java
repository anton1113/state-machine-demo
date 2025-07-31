package com.arash.edu.statemachinedemo.repository;

import com.arash.edu.statemachinedemo.domain.db.AiSystemMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AiSystemMessageRepository extends JpaRepository<AiSystemMessage, UUID> {

    Optional<AiSystemMessage> findByKey(String key);
}
