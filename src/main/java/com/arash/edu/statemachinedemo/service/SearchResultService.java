package com.arash.edu.statemachinedemo.service;

import com.arash.edu.statemachinedemo.domain.db.SearchResult;
import com.arash.edu.statemachinedemo.repository.SearchResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchResultService {

    private final SearchResultRepository searchResultRepository;

    public void persistSearchResult(UUID searchId, UUID smId, String result) {
        SearchResult searchResult = new SearchResult(searchId, smId, result);
        searchResultRepository.save(searchResult);
    }

    public SearchResult waitForSearchResult(UUID searchId, Duration timeout) {
        Instant deadline = Instant.now().plus(timeout);
        while (Instant.now().isBefore(deadline)) {
            Optional<SearchResult> result = searchResultRepository.findById(searchId);
            if (result.isPresent()) {
                searchResultRepository.deleteById(searchId);
                return result.get();
            }
            sleep(200);
        }
        throw new RuntimeException("Timeout waiting result for search " + searchId);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("Sleep interrupted", e);
        }
    }
}
