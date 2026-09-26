package com.veritaschambers.service;

import com.veritaschambers.entity.AiKnowledgeRecord;
import com.veritaschambers.entity.enums.VerificationStatus;
import com.veritaschambers.repository.AiKnowledgeRepository;
import com.veritaschambers.service.impl.KnowledgeRetrievalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KnowledgeRetrievalServiceTest {

    @Mock
    private AiKnowledgeRepository aiKnowledgeRepository;

    private KnowledgeRetrievalServiceImpl knowledgeRetrievalService;

    @BeforeEach
    void setUp() {
        knowledgeRetrievalService = new KnowledgeRetrievalServiceImpl(aiKnowledgeRepository);
    }

    @Test
    void retrieveContext_EmptyQuery_ReturnsEmptyList() {
        List<AiKnowledgeRecord> result = knowledgeRetrievalService.retrieveContext("");
        assertTrue(result.isEmpty());
    }

    @Test
    void retrieveContext_OnlyStopWords_ReturnsEmptyList() {
        List<AiKnowledgeRecord> result = knowledgeRetrievalService.retrieveContext("what is the in for");
        assertTrue(result.isEmpty());
    }

    @Test
    void retrieveContext_ValidQuery_ReturnsVerifiedRecords() {
        AiKnowledgeRecord record = new AiKnowledgeRecord();
        record.setId(1L);
        record.setTitleKey("Test Record");
        record.setContent("Test Content");

        when(aiKnowledgeRepository.findRelevantKnowledge(eq("test"), eq(VerificationStatus.VERIFIED), any(Pageable.class)))
                .thenReturn(List.of(record));

        List<AiKnowledgeRecord> result = knowledgeRetrievalService.retrieveContext("test");

        assertEquals(1, result.size());
        assertEquals("Test Record", result.get(0).getTitleKey());
    }

    @Test
    void retrieveContext_MultipleKeywords_DeDuplicatesRecords() {
        AiKnowledgeRecord record = new AiKnowledgeRecord();
        record.setId(1L);
        record.setTitleKey("Test Record");

        // The query "test law" produces two valid keywords: "test" and "law"
        when(aiKnowledgeRepository.findRelevantKnowledge(eq("test"), eq(VerificationStatus.VERIFIED), any(Pageable.class)))
                .thenReturn(List.of(record));
        when(aiKnowledgeRepository.findRelevantKnowledge(eq("law"), eq(VerificationStatus.VERIFIED), any(Pageable.class)))
                .thenReturn(List.of(record)); // same record matched

        List<AiKnowledgeRecord> result = knowledgeRetrievalService.retrieveContext("test law");

        // The LinkedHashSet should deduplicate the identical record
        assertEquals(1, result.size());
    }
}
