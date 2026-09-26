package com.veritaschambers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veritaschambers.config.GroqAiProperties;
import com.veritaschambers.dto.ai.AiResponseDto;
import com.veritaschambers.dto.ai.ChatMessage;
import com.veritaschambers.dto.ai.groq.GroqChatResponse;
import com.veritaschambers.exception.AiProviderException;
import com.veritaschambers.service.impl.GroqAiServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

public class GroqAiServiceImplTest {

    private GroqAiServiceImpl aiService;
    private MockRestServiceServer mockServer;
    private ObjectMapper objectMapper = new ObjectMapper();
    private GroqAiProperties properties;

    @BeforeEach
    public void setup() {
        properties = new GroqAiProperties();
        properties.setApiKey("test-key");
        properties.setBaseUrl("https://api.groq.com/openai/v1");
        properties.setModel("openai/gpt-oss-20b");

        RestClient.Builder builder = RestClient.builder().baseUrl(properties.getBaseUrl());
        mockServer = MockRestServiceServer.bindTo(builder).build();
        RestClient restClient = builder.build();

        aiService = new GroqAiServiceImpl(restClient, properties);
    }

    @Test
    public void generateResponse_Success() throws Exception {
        GroqChatResponse mockResponse = new GroqChatResponse();
        GroqChatResponse.Choice choice = new GroqChatResponse.Choice();
        ChatMessage responseMessage = new ChatMessage("assistant", "This is a test response.");
        choice.setMessage(responseMessage);
        mockResponse.setChoices(Collections.singletonList(choice));

        mockServer.expect(requestTo("https://api.groq.com/openai/v1/chat/completions"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(objectMapper.writeValueAsString(mockResponse), MediaType.APPLICATION_JSON));

        AiResponseDto response = aiService.generateResponse(Collections.emptyList(), "system context");
        
        assertNotNull(response);
        assertEquals("This is a test response.", response.getReply());
        assertTrue(response.isRequiresDisclaimer());
        
        mockServer.verify();
    }

    @Test
    public void generateResponse_EmptyResponse_ThrowsAiProviderException() throws Exception {
        GroqChatResponse mockResponse = new GroqChatResponse();
        mockResponse.setChoices(Collections.emptyList()); // Empty choices

        mockServer.expect(requestTo("https://api.groq.com/openai/v1/chat/completions"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(mockResponse), MediaType.APPLICATION_JSON));

        assertThrows(AiProviderException.class, () -> aiService.generateResponse(Collections.emptyList(), "system context"));
        
        mockServer.verify();
    }

    @Test
    public void generateResponse_MissingApiKey_ThrowsAiProviderException() {
        properties.setApiKey(null); // Missing key
        
        AiProviderException ex = assertThrows(AiProviderException.class, 
                () -> aiService.generateResponse(Collections.emptyList(), "system context"));
        
        assertTrue(ex.getMessage().contains("unavailable"));
    }

    @Test
    public void generateResponse_RateLimitExceeded_ThrowsAiProviderException() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Retry-After", "10");
        
        mockServer.expect(requestTo("https://api.groq.com/openai/v1/chat/completions"))
                .andRespond(withStatus(HttpStatus.TOO_MANY_REQUESTS).headers(headers));

        AiProviderException ex = assertThrows(AiProviderException.class, 
                () -> aiService.generateResponse(Collections.emptyList(), "system context"));
        
        assertTrue(ex.getMessage().contains("busy"));
        
        mockServer.verify();
    }

    @Test
    public void generateResponse_ServerError_ThrowsAiProviderException() {
        mockServer.expect(requestTo("https://api.groq.com/openai/v1/chat/completions"))
                .andRespond(withServerError());

        AiProviderException ex = assertThrows(AiProviderException.class, 
                () -> aiService.generateResponse(Collections.emptyList(), "system context"));
        
        assertTrue(ex.getMessage().contains("technical difficulties"));
        
        mockServer.verify();
    }

    @Test
    public void generateResponse_Unauthorized_ThrowsAiProviderException() {
        mockServer.expect(requestTo("https://api.groq.com/openai/v1/chat/completions"))
                .andRespond(withStatus(HttpStatus.UNAUTHORIZED));

        AiProviderException ex = assertThrows(AiProviderException.class, 
                () -> aiService.generateResponse(Collections.emptyList(), "system context"));
        
        assertTrue(ex.getMessage().contains("unavailable"));
        mockServer.verify();
    }

    @Test
    public void generateResponse_BadRequest_ThrowsAiProviderException() {
        mockServer.expect(requestTo("https://api.groq.com/openai/v1/chat/completions"))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        AiProviderException ex = assertThrows(AiProviderException.class, 
                () -> aiService.generateResponse(Collections.emptyList(), "system context"));
        
        assertTrue(ex.getMessage().contains("unavailable"));
        mockServer.verify();
    }

    @Test
    public void generateResponse_NotFound_ThrowsAiProviderException() {
        mockServer.expect(requestTo("https://api.groq.com/openai/v1/chat/completions"))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        AiProviderException ex = assertThrows(AiProviderException.class, 
                () -> aiService.generateResponse(Collections.emptyList(), "system context"));
        
        assertTrue(ex.getMessage().contains("unavailable"));
        mockServer.verify();
    }

    @Test
    public void generateResponse_NullContent_ThrowsAiProviderException() throws Exception {
        GroqChatResponse mockResponse = new GroqChatResponse();
        GroqChatResponse.Choice choice = new GroqChatResponse.Choice();
        ChatMessage responseMessage = new ChatMessage("assistant", null);
        choice.setMessage(responseMessage);
        mockResponse.setChoices(Collections.singletonList(choice));

        mockServer.expect(requestTo("https://api.groq.com/openai/v1/chat/completions"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(mockResponse), MediaType.APPLICATION_JSON));

        AiProviderException ex = assertThrows(AiProviderException.class, 
                () -> aiService.generateResponse(Collections.emptyList(), "system context"));
        
        assertTrue(ex.getMessage().contains("empty text"));
        mockServer.verify();
    }
}
