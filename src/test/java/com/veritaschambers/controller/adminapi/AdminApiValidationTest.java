package com.veritaschambers.controller.adminapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veritaschambers.dto.request.ArticleCreateRequest;
import com.veritaschambers.entity.enums.ContentStatus;
import com.veritaschambers.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AdminApiValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean private ArticleService articleService;
    @MockBean private PracticeAreaService practiceAreaService;
    @MockBean private FaqService faqService;
    @MockBean private ConsultationService consultationService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void createArticle_InvalidPayload_ShouldReturn400() throws Exception {
        // Missing title, content, and categoryId
        ArticleCreateRequest request = new ArticleCreateRequest(null, "", "slug", "ex", "", null, ContentStatus.PUBLISHED);

        mockMvc.perform(post("/api/v1/admin/articles").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.fieldErrors").exists());
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void createArticle_ValidPayload_ShouldReturn201() throws Exception {
        ArticleCreateRequest request = new ArticleCreateRequest(1L, "Valid Title", "valid-slug", "ex", "content", null, ContentStatus.PUBLISHED);

        mockMvc.perform(post("/api/v1/admin/articles").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
