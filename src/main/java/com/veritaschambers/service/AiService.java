package com.veritaschambers.service;

import com.veritaschambers.dto.ai.AiResponseDto;
import com.veritaschambers.dto.ai.ChatMessage;

import java.util.List;

public interface AiService {
    AiResponseDto generateResponse(List<ChatMessage> conversationHistory, String systemContext);
}
