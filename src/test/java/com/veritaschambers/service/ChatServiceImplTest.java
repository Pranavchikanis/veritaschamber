package com.veritaschambers.service;

import com.veritaschambers.dto.ai.AiResponseDto;
import com.veritaschambers.dto.ai.ChatMessage;
import com.veritaschambers.entity.AiKnowledgeRecord;
import com.veritaschambers.service.impl.ChatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatServiceImplTest {

    @Mock
    private KnowledgeRetrievalService knowledgeRetrievalService;

    @Mock
    private AiService aiService;

    private ChatServiceImpl chatService;

    @BeforeEach
    void setUp() {
        chatService = new ChatServiceImpl(knowledgeRetrievalService, aiService);
    }

    @Test
    void processChatRequest_EmptyHistory_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> chatService.processChatRequest(Collections.emptyList()));
    }

    @Test
    void processChatRequest_EmptyKnowledge_BuildsSafeContext() {
        List<ChatMessage> history = List.of(new ChatMessage("user", "Hello"));

        when(knowledgeRetrievalService.retrieveContext("Hello")).thenReturn(Collections.emptyList());
        when(aiService.generateResponse(anyList(), anyString()))
                .thenReturn(new AiResponseDto("Hello", false));

        chatService.processChatRequest(history);

        ArgumentCaptor<String> contextCaptor = ArgumentCaptor.forClass(String.class);
        verify(aiService).generateResponse(anyList(), contextCaptor.capture());

        String systemContext = contextCaptor.getValue();
        assertTrue(systemContext.contains("CRITICAL RULES"));
        assertTrue(systemContext.contains("[NO RELEVANT VERIFIED FIRM KNOWLEDGE FOUND FOR THIS QUERY]"));
        assertTrue(systemContext.contains("<verified_knowledge>"));
    }

    @Test
    void processChatRequest_WithKnowledge_InjectsIntoContext() {
        List<ChatMessage> history = List.of(new ChatMessage("user", "Where are you?"));

        AiKnowledgeRecord record = new AiKnowledgeRecord();
        record.setTitleKey("Office Location");
        record.setCategory("FIRM_INFO");
        record.setContent("We are located in Sangli.");

        when(knowledgeRetrievalService.retrieveContext("Where are you?")).thenReturn(List.of(record));
        when(aiService.generateResponse(anyList(), anyString()))
                .thenReturn(new AiResponseDto("In Sangli", false));

        chatService.processChatRequest(history);

        ArgumentCaptor<String> contextCaptor = ArgumentCaptor.forClass(String.class);
        verify(aiService).generateResponse(anyList(), contextCaptor.capture());

        String systemContext = contextCaptor.getValue();
        assertTrue(systemContext.contains("Office Location"));
        assertTrue(systemContext.contains("FIRM_INFO"));
        assertTrue(systemContext.contains("We are located in Sangli."));
    }
}
