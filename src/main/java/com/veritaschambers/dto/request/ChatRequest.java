package com.veritaschambers.dto.request;

import com.veritaschambers.dto.ai.ChatMessage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ChatRequest {

    @NotEmpty(message = "Conversation history cannot be empty")
    @Size(max = 10, message = "Maximum of 10 messages allowed in conversation history")
    @Valid
    private List<ChatMessage> messages;

    public ChatRequest() {
    }

    public ChatRequest(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }
}
