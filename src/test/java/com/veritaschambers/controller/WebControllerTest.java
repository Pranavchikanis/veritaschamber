package com.veritaschambers.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

import org.springframework.boot.test.mock.mockito.MockBean;
import com.veritaschambers.service.ArticleService;
import com.veritaschambers.service.FaqService;
import com.veritaschambers.service.LawyerProfileService;
import com.veritaschambers.service.PracticeAreaService;
import java.util.Collections;
import static org.mockito.Mockito.when;

@WebMvcTest(WebController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
class WebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PracticeAreaService practiceAreaService;

    @MockBean
    private LawyerProfileService lawyerProfileService;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private FaqService faqService;

    @Test
    void testHomePageLoads() throws Exception {
        when(practiceAreaService.getActivePracticeAreas()).thenReturn(Collections.emptyList());
        
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("public/home"));
    }
}
