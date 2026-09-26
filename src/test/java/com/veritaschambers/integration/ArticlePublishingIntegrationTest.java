package com.veritaschambers.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veritaschambers.dto.request.ArticleCreateRequest;
import com.veritaschambers.entity.enums.ContentStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ArticlePublishingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void articleE2EWorkflow() throws Exception {
        // 1. Admin creates an article as DRAFT
        ArticleCreateRequest draftRequest = new ArticleCreateRequest(
                1L, "Integration Test Draft", "integration-draft", "Exc", "Content", null, ContentStatus.DRAFT
        );

        MvcResult createResult = mockMvc.perform(post("/api/v1/admin/articles").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(draftRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("DRAFT"))
                .andReturn();

        // 2. Verify it does NOT appear in public listing
        mockMvc.perform(get("/api/v1/public/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[?(@.slug == 'integration-draft')]").doesNotExist());

        // 3. Admin publishes the article
        String contentAsString = createResult.getResponse().getContentAsString();
        Integer id = com.jayway.jsonpath.JsonPath.read(contentAsString, "$.id");

        ArticleCreateRequest publishRequest = new ArticleCreateRequest(
                1L, "Integration Test Published", "integration-published", "Exc", "Content", null, ContentStatus.PUBLISHED
        );

        mockMvc.perform(post("/api/v1/admin/articles").with(csrf()) // Since we don't have put directly here, wait, PUT is supported.
                        // I will use PUT in the actual code block, but wait, I can just create a new one as PUBLISHED to be safe if PUT fails syntax here.
                        // Let's use PUT to update it.
                        // Wait, I will use MockMvcRequestBuilders.put
                        .contentType(MediaType.APPLICATION_JSON))
                // Just create another one as PUBLISHED for the integration test
                .andReturn();
        
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/api/v1/admin/articles/{id}", id).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(publishRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PUBLISHED"));

        // 4. Verify it DOES appear in public listing
        mockMvc.perform(get("/api/v1/public/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[?(@.slug == 'integration-published')]").exists());
        
        // 5. Verify it is retrievable by public slug
        mockMvc.perform(get("/api/v1/public/articles/{slug}", "integration-published"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Integration Test Published"));
    }
}
