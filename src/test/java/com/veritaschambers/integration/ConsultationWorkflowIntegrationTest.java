package com.veritaschambers.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veritaschambers.dto.request.ConsultationCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ConsultationWorkflowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void consultationE2EWorkflow() throws Exception {
        // 1. Submit consultation publicly
        ConsultationCreateRequest publicRequest = new ConsultationCreateRequest(
                "Jane Client", "jane@example.com", "9876543210", "PHONE", "Divorce Inquiry", "Need help.", null, null
        );

        MvcResult createResult = mockMvc.perform(post("/api/v1/public/consultations").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(publicRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.status").value("NEW"))
                .andReturn();

        // Extract ID
        String contentAsString = createResult.getResponse().getContentAsString();
        Integer id = com.jayway.jsonpath.JsonPath.read(contentAsString, "$.id");

        // 2. Admin retrieves the consultation
        mockMvc.perform(get("/api/v1/admin/consultations/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Client"));

        // 3. Admin updates status
        Map<String, String> updateRequest = Map.of("status", "CONTACTED");

        mockMvc.perform(patch("/api/v1/admin/consultations/{id}/status", id).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONTACTED"));

        // 4. Verify it's updated
        mockMvc.perform(get("/api/v1/admin/consultations/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONTACTED"));
    }
}
