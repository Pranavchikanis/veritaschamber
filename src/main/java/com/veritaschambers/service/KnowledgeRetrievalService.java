package com.veritaschambers.service;

import com.veritaschambers.entity.AiKnowledgeRecord;

import java.util.List;

public interface KnowledgeRetrievalService {
    
    /**
     * Retrieves relevant VERIFIED knowledge records based on the user's query.
     * Guaranteed to only return records with verification_status = VERIFIED and is_active = true.
     *
     * @param userQuery the raw natural language query from the user
     * @return a bounded list of relevant knowledge records
     */
    List<AiKnowledgeRecord> retrieveContext(String userQuery);
}
