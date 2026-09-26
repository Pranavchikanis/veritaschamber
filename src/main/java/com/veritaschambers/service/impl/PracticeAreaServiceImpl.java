package com.veritaschambers.service.impl;

import com.veritaschambers.dto.request.PracticeAreaCreateRequest;
import com.veritaschambers.dto.request.PracticeAreaUpdateRequest;
import com.veritaschambers.dto.response.PracticeAreaResponse;
import com.veritaschambers.entity.PracticeArea;
import com.veritaschambers.exception.BusinessValidationException;
import com.veritaschambers.exception.ResourceNotFoundException;
import com.veritaschambers.mapper.PracticeAreaMapper;
import com.veritaschambers.repository.PracticeAreaRepository;
import com.veritaschambers.service.PracticeAreaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PracticeAreaServiceImpl implements PracticeAreaService {

    private final PracticeAreaRepository practiceAreaRepository;

    public PracticeAreaServiceImpl(PracticeAreaRepository practiceAreaRepository) {
        this.practiceAreaRepository = practiceAreaRepository;
    }

    @Override
    @Transactional
    public PracticeAreaResponse createPracticeArea(PracticeAreaCreateRequest request) {
        PracticeArea entity = PracticeAreaMapper.toEntity(request);
        try {
            PracticeArea saved = practiceAreaRepository.save(entity);
            return PracticeAreaMapper.toResponse(saved);
        } catch (Exception e) {
            throw new BusinessValidationException("Could not save Practice Area. Slug might already exist.");
        }
    }

    @Override
    @Transactional
    public PracticeAreaResponse updatePracticeArea(Long id, PracticeAreaUpdateRequest request) {
        PracticeArea entity = practiceAreaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Practice Area not found"));
        
        PracticeAreaMapper.updateEntity(entity, request);
        
        try {
            PracticeArea updated = practiceAreaRepository.save(entity);
            return PracticeAreaMapper.toResponse(updated);
        } catch (Exception e) {
            throw new BusinessValidationException("Could not update Practice Area. Slug might already exist.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PracticeAreaResponse> getAllPracticeAreas(Pageable pageable) {
        return practiceAreaRepository.findAll(pageable)
                .map(PracticeAreaMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<PracticeAreaResponse> getActivePracticeAreas() {
        return practiceAreaRepository.findAll().stream()
                .filter(pa -> pa.getStatus() == com.veritaschambers.entity.enums.ContentStatus.PUBLISHED)
                .map(PracticeAreaMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PracticeAreaResponse getPracticeAreaById(Long id) {
        return practiceAreaRepository.findById(id)
                .map(PracticeAreaMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Practice Area not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public PracticeAreaResponse getPracticeAreaBySlug(String slug) {
        return practiceAreaRepository.findBySlug(slug)
                .map(PracticeAreaMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Practice Area not found"));
    }

    @Override
    @Transactional
    public void deletePracticeArea(Long id) {
        if (!practiceAreaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Practice Area not found");
        }
        practiceAreaRepository.deleteById(id);
    }
}
