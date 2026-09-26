package com.veritaschambers.controller.publicapi;

import com.veritaschambers.dto.ai.AiResponseDto;
import com.veritaschambers.dto.request.ChatRequest;
import com.veritaschambers.dto.response.ChatResponse;
import com.veritaschambers.security.ChatRateLimiter;
import com.veritaschambers.service.ChatService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public/chat")
public class ChatController {

    private final ChatService chatService;
    private final ChatRateLimiter chatRateLimiter;

    public ChatController(ChatService chatService, ChatRateLimiter chatRateLimiter) {
        this.chatService = chatService;
        this.chatRateLimiter = chatRateLimiter;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> processChatRequest(
            @Valid @RequestBody ChatRequest request, 
            HttpServletRequest httpRequest) {
        
        // 1. Enforce rate limiting based on client IP
        String clientIp = getClientIp(httpRequest);
        chatRateLimiter.checkLimit(clientIp);

        // 2. Delegate to the ChatService which orchestrates knowledge retrieval + AI provider
        AiResponseDto aiResponse = chatService.processChatRequest(request.getMessages());

        // 3. Map to public response DTO (exposing only safe information)
        ChatResponse response = new ChatResponse(
                aiResponse.getReply(),
                aiResponse.isRequiresDisclaimer()
        );

        return ResponseEntity.ok(response);
    }

    private String getClientIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
