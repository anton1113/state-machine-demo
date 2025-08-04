package com.arash.edu.statemachinedemo.repository;

import com.arash.edu.statemachinedemo.domain.db.SearchResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SearchResultRepository extends JpaRepository<SearchResult, UUID> {
}
