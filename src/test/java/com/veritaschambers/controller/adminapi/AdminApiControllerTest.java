package com.veritaschambers.controller.adminapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.mock.mockito.MockBean;
import com.veritaschambers.service.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AdminApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LawyerProfileService lawyerProfileService;

    @MockBean
    private PracticeAreaService practiceAreaService;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private FaqService faqService;

    @MockBean
    private ConsultationService consultationService;

    @Test
    void adminEndpoints_WithoutAuth_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/v1/admin/lawyer-profile"))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/v1/admin/practice-areas"))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/v1/admin/articles"))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/v1/admin/faqs"))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/v1/admin/consultations"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void adminEndpoints_WithWrongRole_ShouldReturn403() throws Exception {
        mockMvc.perform(get("/api/v1/admin/lawyer-profile"))
                .andExpect(status().isForbidden());
        mockMvc.perform(get("/api/v1/admin/practice-areas"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminEndpoints_WithAdminRole_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/admin/lawyer-profile"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/admin/practice-areas"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/admin/articles"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/admin/faqs"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/admin/consultations"))
                .andExpect(status().isOk());
    }
}
