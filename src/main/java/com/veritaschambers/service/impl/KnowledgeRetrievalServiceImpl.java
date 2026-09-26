package com.veritaschambers.service.impl;

import com.veritaschambers.entity.AiKnowledgeRecord;
import com.veritaschambers.entity.enums.VerificationStatus;
import com.veritaschambers.repository.AiKnowledgeRepository;
import com.veritaschambers.service.KnowledgeRetrievalService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class KnowledgeRetrievalServiceImpl implements KnowledgeRetrievalService {

    private final AiKnowledgeRepository aiKnowledgeRepository;
    
    // Bound the maximum number of records we send to the AI to prevent context bloat
    private static final int MAX_RESULTS_PER_QUERY = 5;

    // Words to ignore when tokenizing the user's query for basic keyword extraction
    private static final Set<String> STOP_WORDS = Set.of(
            "a", "an", "the", "and", "or", "but", "is", "are", "am", "was", "were",
            "be", "been", "to", "in", "for", "on", "with", "about", "what", "how",
            "who", "when", "where", "do", "does", "did", "can", "could", "would",
            "should", "i", "you", "he", "she", "it", "we", "they", "my", "your"
    );

    public KnowledgeRetrievalServiceImpl(AiKnowledgeRepository aiKnowledgeRepository) {
        this.aiKnowledgeRepository = aiKnowledgeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiKnowledgeRecord> retrieveContext(String userQuery) {
        if (userQuery == null || userQuery.trim().isEmpty()) {
            return Collections.emptyList();
        }

        // 1. Normalize and extract keywords
        List<String> keywords = extractKeywords(userQuery);

        if (keywords.isEmpty()) {
            // If the query was entirely stop words (e.g. "what is it"), fallback to a generic search term or return empty.
            // Returning empty is safer to prevent sending random context.
            return Collections.emptyList();
        }

        // 2. We use a Set to prevent duplicate records if multiple keywords match the same record
        Set<AiKnowledgeRecord> results = new LinkedHashSet<>();
        
        Pageable pageable = PageRequest.of(0, MAX_RESULTS_PER_QUERY, Sort.by(Sort.Direction.ASC, "displayOrder"));

        // 3. Search for each keyword until we hit our max limit
        for (String keyword : keywords) {
            if (results.size() >= MAX_RESULTS_PER_QUERY) {
                break;
            }
            
            // The repository method strictly enforces verificationStatus = VERIFIED and isActive = true
            List<AiKnowledgeRecord> records = aiKnowledgeRepository.findRelevantKnowledge(
                    keyword, 
                    VerificationStatus.VERIFIED, 
                    pageable
            );
            
            for (AiKnowledgeRecord record : records) {
                results.add(record);
                if (results.size() >= MAX_RESULTS_PER_QUERY) {
                    break;
                }
            }
        }

        return new ArrayList<>(results);
    }

    /**
     * Extremely basic keyword extraction (tokenization and stop-word removal).
     * For a production system, this would be replaced with full-text search or embeddings,
     * but we are explicitly constrained to simple relational retrieval for AI-03.
     */
    private List<String> extractKeywords(String query) {
        String[] words = query.toLowerCase().replaceAll("[^a-z0-9\\s]", "").split("\\s+");
        
        return Arrays.stream(words)
                .filter(w -> !w.isEmpty() && !STOP_WORDS.contains(w) && w.length() > 2)
                .collect(Collectors.toList());
    }
}
