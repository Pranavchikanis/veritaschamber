package com.veritaschambers.service;

import com.veritaschambers.dto.request.FaqCreateRequest;
import com.veritaschambers.dto.request.FaqUpdateRequest;
import com.veritaschambers.dto.response.FaqResponse;
import com.veritaschambers.entity.Faq;
import com.veritaschambers.exception.ResourceNotFoundException;
import com.veritaschambers.repository.FaqRepository;
import com.veritaschambers.service.impl.FaqServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FaqServiceTest {

    @Mock
    private FaqRepository faqRepository;

    @InjectMocks
    private FaqServiceImpl faqService;

    private Faq faq;

    @BeforeEach
    void setUp() {
        faq = new Faq();
        faq.setId(1L);
        faq.setQuestion("Question?");
        faq.setAnswer("Answer.");
        faq.setDisplayOrder(1);
        faq.setIsActive(true);
    }

    @Test
    void createFaq_Success() {
        FaqCreateRequest request = new FaqCreateRequest("New Question", "New Answer", 2, true);
        
        when(faqRepository.save(any(Faq.class))).thenAnswer(i -> {
            Faq f = i.getArgument(0);
            f.setId(2L);
            return f;
        });

        FaqResponse response = faqService.createFaq(request);

        assertNotNull(response);
        assertEquals(2L, response.id());
        assertEquals("New Question", response.question());
        assertEquals(2, response.displayOrder());
    }

    @Test
    void updateFaq_Success() {
        FaqUpdateRequest request = new FaqUpdateRequest("Updated Q", "Updated A", 5, false);

        when(faqRepository.findById(1L)).thenReturn(Optional.of(faq));
        when(faqRepository.save(any(Faq.class))).thenReturn(faq);

        FaqResponse response = faqService.updateFaq(1L, request);

        assertEquals("Updated Q", faq.getQuestion());
        assertFalse(faq.getIsActive());
        assertEquals(5, response.displayOrder());
    }

    @Test
    void getActiveFaqs_Success() {
        when(faqRepository.findAll()).thenReturn(List.of(faq));

        List<FaqResponse> responses = faqService.getActiveFaqs();

        assertEquals(1, responses.size());
        assertEquals(1L, responses.get(0).id());
    }
}
