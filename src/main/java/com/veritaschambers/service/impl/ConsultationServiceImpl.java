package com.veritaschambers.service.impl;

import com.veritaschambers.dto.request.ConsultationCreateRequest;
import com.veritaschambers.dto.response.ConsultationResponse;
import com.veritaschambers.entity.ConsultationRequest;
import com.veritaschambers.entity.enums.ConsultationStatus;
import com.veritaschambers.exception.ResourceNotFoundException;
import com.veritaschambers.mapper.ConsultationMapper;
import com.veritaschambers.repository.ConsultationRequestRepository;
import com.veritaschambers.service.ConsultationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRequestRepository consultationRequestRepository;

    public ConsultationServiceImpl(ConsultationRequestRepository consultationRequestRepository) {
        this.consultationRequestRepository = consultationRequestRepository;
    }

    @Override
    @Transactional
    public ConsultationResponse createConsultation(ConsultationCreateRequest request) {
        ConsultationRequest entity = ConsultationMapper.toEntity(request);
        ConsultationRequest saved = consultationRequestRepository.save(entity);
        return ConsultationMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultationResponse> getAllConsultations(Pageable pageable) {
        return consultationRequestRepository.findAll(pageable)
                .map(ConsultationMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ConsultationResponse getConsultationById(Long id) {
        return consultationRequestRepository.findById(id)
                .map(ConsultationMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation request not found with id: " + id));
    }

    @Override
    @Transactional
    public ConsultationResponse updateConsultationStatus(Long id, ConsultationStatus status) {
        ConsultationRequest entity = consultationRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation request not found with id: " + id));
        entity.setStatus(status);
        ConsultationRequest updated = consultationRequestRepository.save(entity);
        return ConsultationMapper.toResponse(updated);
    }
}
