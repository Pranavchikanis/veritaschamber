package com.veritaschambers.controller.publicapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veritaschambers.dto.request.ConsultationCreateRequest;
import com.veritaschambers.dto.request.ContactMessageCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.mock.mockito.MockBean;
import com.veritaschambers.service.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PublicApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LawyerProfileService lawyerProfileService;

    @MockBean
    private PracticeAreaService practiceAreaService;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private FaqService faqService;

    @MockBean
    private TestimonialService testimonialService;

    @MockBean
    private ConsultationService consultationService;

    @MockBean
    private ContactMessageService contactMessageService;

    @Test
    void getLawyerProfile_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/public/lawyer-profile"))
                .andExpect(status().isOk());
    }

    @Test
    void getPracticeAreas_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/public/practice-areas"))
                .andExpect(status().isOk());
    }

    @Test
    void getArticles_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/public/articles"))
                .andExpect(status().isOk());
    }

    @Test
    void getFaqs_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/public/faqs"))
                .andExpect(status().isOk());
    }

    @Test
    void getTestimonials_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/public/testimonials"))
                .andExpect(status().isOk());
    }

    @Test
    void createConsultation_Valid_ShouldReturn201() throws Exception {
        ConsultationCreateRequest request = new ConsultationCreateRequest(
                "Test User", "test@example.invalid", "1234567890",
                "EMAIL", "Subject", "Message body that is long enough",
                null, null
        );

        mockMvc.perform(post("/api/v1/public/consultations").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void createConsultation_Invalid_ShouldReturn400() throws Exception {
        ConsultationCreateRequest request = new ConsultationCreateRequest(
                "", "invalid-email", "1234567890",
                "EMAIL", "Subject", "",
                null, null
        );

        mockMvc.perform(post("/api/v1/public/consultations").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createContactMessage_Valid_ShouldReturn201() throws Exception {
        ContactMessageCreateRequest request = new ContactMessageCreateRequest(
                "Test User", "test@example.invalid", "1234567890",
                "Subject", "Message body that is long enough"
        );

        mockMvc.perform(post("/api/v1/public/contact-messages").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
