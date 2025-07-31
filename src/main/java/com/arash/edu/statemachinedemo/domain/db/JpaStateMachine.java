package com.arash.edu.statemachinedemo.domain.db;

import com.arash.edu.statemachinedemo.enums.States;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Data
@Entity(name = "state_machine")
public class JpaStateMachine {

    @Id
    private UUID id;

    private String state;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<Object, Object> context;
}
