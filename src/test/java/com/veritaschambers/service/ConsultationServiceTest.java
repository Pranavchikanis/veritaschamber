package com.veritaschambers.service;

import com.veritaschambers.dto.request.ConsultationCreateRequest;
import com.veritaschambers.dto.response.ConsultationResponse;
import com.veritaschambers.entity.ConsultationRequest;
import com.veritaschambers.entity.enums.ConsultationStatus;
import com.veritaschambers.repository.ConsultationRequestRepository;
import com.veritaschambers.service.impl.ConsultationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultationServiceTest {

    @Mock
    private ConsultationRequestRepository repository;

    @InjectMocks
    private ConsultationServiceImpl consultationService;

    private ConsultationCreateRequest createRequest;
    private ConsultationRequest mockEntity;

    @BeforeEach
    void setUp() {
        createRequest = new ConsultationCreateRequest(
                "John Doe",
                "john@example.com",
                "+1234567890",
                "Email",
                "Legal Advice",
                "Need consultation for business contract.",
                LocalDate.of(2026, 1, 1),
                LocalTime.of(10, 0)
        );

        mockEntity = new ConsultationRequest();
        mockEntity.setId(1L);
        mockEntity.setName("John Doe");
        mockEntity.setEmail("john@example.com");
        mockEntity.setPhone("+1234567890");
        mockEntity.setPreferredContactMethod("Email");
        mockEntity.setSubject("Legal Advice");
        mockEntity.setMessage("Need consultation for business contract.");
        mockEntity.setPreferredDate(LocalDate.of(2026, 1, 1));
        mockEntity.setPreferredTime(LocalTime.of(10, 0));
        mockEntity.setStatus(ConsultationStatus.NEW);
        mockEntity.setCreatedAt(LocalDateTime.now());
        mockEntity.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void createConsultation_success() {
        when(repository.save(any(ConsultationRequest.class))).thenReturn(mockEntity);

        ConsultationResponse response = consultationService.createConsultation(createRequest);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("John Doe", response.name());
        assertEquals(ConsultationStatus.NEW, response.status());

        verify(repository, times(1)).save(any(ConsultationRequest.class));
    }
}
