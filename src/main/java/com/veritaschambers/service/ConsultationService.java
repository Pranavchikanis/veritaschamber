package com.veritaschambers.service;

import com.veritaschambers.dto.request.ConsultationCreateRequest;
import com.veritaschambers.dto.response.ConsultationResponse;
import com.veritaschambers.entity.enums.ConsultationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsultationService {
    ConsultationResponse createConsultation(ConsultationCreateRequest request);
    Page<ConsultationResponse> getAllConsultations(Pageable pageable);
    ConsultationResponse getConsultationById(Long id);
    ConsultationResponse updateConsultationStatus(Long id, ConsultationStatus status);
}
