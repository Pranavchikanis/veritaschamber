package com.veritaschambers.dto.ai.groq;

import com.veritaschambers.dto.ai.ChatMessage;

import java.util.List;

public class GroqChatRequest {
    
    private String model;
    private List<ChatMessage> messages;
    private Double temperature;

    public GroqChatRequest() {
    }

    public GroqChatRequest(String model, List<ChatMessage> messages, Double temperature) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
