package com.veritaschambers.dto.ai;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ChatMessage {
    
    @NotBlank(message = "Role must not be blank")
    @Pattern(regexp = "^(user|assistant)$", message = "Role must be 'user' or 'assistant'")
    private String role;

    @NotBlank(message = "Message content must not be blank")
    @Size(max = 1000, message = "Message content cannot exceed 1000 characters")
    private String content;

    public ChatMessage() {
    }

    public ChatMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
