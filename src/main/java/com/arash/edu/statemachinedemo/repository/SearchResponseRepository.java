package com.arash.edu.statemachinedemo.repository;

import com.arash.edu.statemachinedemo.domain.db.SearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SearchResponseRepository extends JpaRepository<SearchResponse, UUID> {
}
