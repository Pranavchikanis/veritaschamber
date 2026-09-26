package com.veritaschambers.security;

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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityBoundaryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void postWithoutCsrf_ShouldReturn403() throws Exception {
        ArticleCreateRequest request = new ArticleCreateRequest(1L, "Title", "slug", "ex", "content", null, ContentStatus.PUBLISHED);

        // Making POST request without .with(csrf()) to ensure CSRF protection is active
        mockMvc.perform(post("/api/v1/admin/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void unauthenticatedAdminAccess_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/v1/admin/articles"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void wrongRoleAdminAccess_ShouldReturn403() throws Exception {
        mockMvc.perform(get("/api/v1/admin/articles"))
                .andExpect(status().isForbidden());
    }

    @Test
    void publicArticles_ShouldNeverExposeDrafts() throws Exception {
        // By default, DataSeeder has one PUBLISHED article.
        // Even if drafts exist, public endpoint should not return them.
        mockMvc.perform(get("/api/v1/public/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[?(@.status == 'DRAFT')]").doesNotExist());
    }
}
