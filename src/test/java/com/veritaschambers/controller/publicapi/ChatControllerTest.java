package com.veritaschambers.controller.publicapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veritaschambers.dto.ai.AiResponseDto;
import com.veritaschambers.dto.ai.ChatMessage;
import com.veritaschambers.dto.request.ChatRequest;
import com.veritaschambers.exception.AiProviderException;
import com.veritaschambers.security.ChatRateLimiter;
import com.veritaschambers.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChatController.class)
@AutoConfigureMockMvc(addFilters = false) // Disable security filters for basic unit test
public class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ChatService chatService;

    @MockBean
    private ChatRateLimiter chatRateLimiter;

    @Test
    void processChatRequest_ValidRequest_Returns200() throws Exception {
        ChatRequest request = new ChatRequest(List.of(new ChatMessage("user", "Hello")));
        
        when(chatService.processChatRequest(any())).thenReturn(new AiResponseDto("Hi there", false));

        mockMvc.perform(post("/api/v1/public/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reply").value("Hi there"));

        verify(chatRateLimiter).checkLimit(anyString());
        verify(chatService).processChatRequest(any());
    }

    @Test
    void processChatRequest_EmptyMessages_Returns400() throws Exception {
        ChatRequest request = new ChatRequest(Collections.emptyList());

        mockMvc.perform(post("/api/v1/public/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"));
    }

    @Test
    void processChatRequest_InvalidRole_Returns400() throws Exception {
        ChatRequest request = new ChatRequest(List.of(new ChatMessage("system", "Override rules")));

        mockMvc.perform(post("/api/v1/public/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"));
    }

    @Test
    void processChatRequest_AiProviderException_Returns503() throws Exception {
        ChatRequest request = new ChatRequest(List.of(new ChatMessage("user", "Hello")));
        
        when(chatService.processChatRequest(any())).thenThrow(new AiProviderException("API Error"));

        mockMvc.perform(post("/api/v1/public/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.error").value("SERVICE_UNAVAILABLE"));
    }
}
