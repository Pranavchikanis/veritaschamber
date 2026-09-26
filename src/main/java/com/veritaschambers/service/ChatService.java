package com.veritaschambers.service;

import com.veritaschambers.dto.ai.AiResponseDto;
import com.veritaschambers.dto.ai.ChatMessage;

import java.util.List;

public interface ChatService {

    /**
     * Orchestrates the chat flow by validating the user's message, 
     * retrieving verified knowledge context, and invoking the AI service.
     *
     * @param conversationHistory the full conversation history, including the user's latest message
     * @return the generated AI response bounded by the knowledge context
     */
    AiResponseDto processChatRequest(List<ChatMessage> conversationHistory);
}
