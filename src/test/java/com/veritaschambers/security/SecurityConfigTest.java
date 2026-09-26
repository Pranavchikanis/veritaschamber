package com.veritaschambers.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@org.springframework.test.context.ActiveProfiles("test")
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void publicEndpoints_shouldBeAccessible() throws Exception {
        mockMvc.perform(get("/api/v1/public/articles"))
                .andExpect(status().isOk());
    }

    @Test
    void adminEndpoints_withoutAuth_shouldReturn401() throws Exception {
        mockMvc.perform(get("/api/v1/admin/articles"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void adminEndpoints_withWrongRole_shouldReturn403() throws Exception {
        mockMvc.perform(get("/api/v1/admin/articles"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminEndpoints_withCorrectRole_shouldReturn404Or200() throws Exception {
        mockMvc.perform(get("/api/v1/admin/articles"))
                .andExpect(status().isOk());
    }
}
