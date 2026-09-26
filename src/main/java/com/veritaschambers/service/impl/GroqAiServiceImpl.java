package com.veritaschambers.service.impl;

import com.veritaschambers.config.GroqAiProperties;
import com.veritaschambers.dto.ai.AiResponseDto;
import com.veritaschambers.dto.ai.ChatMessage;
import com.veritaschambers.dto.ai.groq.GroqChatRequest;
import com.veritaschambers.dto.ai.groq.GroqChatResponse;
import com.veritaschambers.exception.AiProviderException;
import com.veritaschambers.service.AiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroqAiServiceImpl implements AiService {

    private static final Logger log = LoggerFactory.getLogger(GroqAiServiceImpl.class);

    private final RestClient restClient;
    private final GroqAiProperties properties;

    public GroqAiServiceImpl(RestClient groqRestClient, GroqAiProperties properties) {
        this.restClient = groqRestClient;
        this.properties = properties;
    }

    @Override
    public AiResponseDto generateResponse(List<ChatMessage> conversationHistory, String systemContext) {
        if (properties.getApiKey() == null || properties.getApiKey().isBlank()) {
            log.error("GROQ_API_KEY is not configured.");
            throw new AiProviderException("The assistant is currently unavailable. Please contact the office.");
        }

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", systemContext));
        messages.addAll(conversationHistory);

        GroqChatRequest request = new GroqChatRequest(
                properties.getModel(),
                messages,
                0.2 // lower temperature to encourage more consistent responses
        );

        try {
            ResponseEntity<GroqChatResponse> responseEntity = restClient.post()
                    .uri("/chat/completions")
                    .body(request)
                    .retrieve()
                    .toEntity(GroqChatResponse.class);

            GroqChatResponse responseBody = responseEntity.getBody();

            if (responseBody == null || responseBody.getChoices() == null || responseBody.getChoices().isEmpty()) {
                log.warn("Received empty response from Groq API.");
                throw new AiProviderException("The assistant received an empty response. Please try again later.");
            }

            GroqChatResponse.Choice choice = responseBody.getChoices().get(0);
            if (choice.getMessage() == null || choice.getMessage().getContent() == null || choice.getMessage().getContent().trim().isEmpty()) {
                throw new AiProviderException("The assistant returned empty text.");
            }

            String replyText = choice.getMessage().getContent();

            // Always returning true for requiresDisclaimer as a safety bound for any legal context
            return new AiResponseDto(replyText, true);

        } catch (HttpClientErrorException.TooManyRequests e) {
            log.warn("Groq API Rate Limit Exceeded (429). Retry-After: {}", e.getResponseHeaders().getFirst("Retry-After"));
            throw new AiProviderException("The assistant is currently busy. Please try again in a few moments.");
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.Forbidden e) {
            log.error("Groq API Authentication Failure (401/403). Check API Key.");
            throw new AiProviderException("The assistant is currently unavailable. Please contact the office.");
        } catch (HttpServerErrorException e) {
            log.error("Groq API Server Error ({}).", e.getStatusCode());
            throw new AiProviderException("The assistant is experiencing temporary technical difficulties.");
        } catch (ResourceAccessException e) {
            log.error("Connection to Groq API failed (Timeout/Network): {}", e.getMessage());
            throw new AiProviderException("The assistant is currently unavailable. Please try again later.");
        } catch (RestClientResponseException e) {
            log.error("Unexpected Groq API Error ({}).", e.getStatusCode());
            throw new AiProviderException("The assistant is currently unavailable.");
        } catch (AiProviderException e) {
            throw e; // re-throw explicit domain exceptions
        } catch (Exception e) {
            log.error("Unknown error while communicating with Groq API.", e);
            throw new AiProviderException("An unexpected error occurred. Please contact the office.");
        }
    }
}
