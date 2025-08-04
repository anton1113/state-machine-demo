package com.arash.edu.statemachinedemo.domain.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class SearchResult {

    @Id
    private UUID id;

    private UUID sessionId;

    private String result;
}
