package com.veritaschambers.service.impl;

import com.veritaschambers.dto.request.FaqCreateRequest;
import com.veritaschambers.dto.request.FaqUpdateRequest;
import com.veritaschambers.dto.response.FaqResponse;
import com.veritaschambers.entity.Faq;
import com.veritaschambers.exception.ResourceNotFoundException;
import com.veritaschambers.mapper.FaqMapper;
import com.veritaschambers.repository.FaqRepository;
import com.veritaschambers.service.FaqService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FaqServiceImpl implements FaqService {

    private final FaqRepository faqRepository;

    public FaqServiceImpl(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    @Override
    @Transactional
    public FaqResponse createFaq(FaqCreateRequest request) {
        Faq entity = FaqMapper.toEntity(request);
        Faq saved = faqRepository.save(entity);
        return FaqMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public FaqResponse updateFaq(Long id, FaqUpdateRequest request) {
        Faq entity = faqRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FAQ not found"));
        
        FaqMapper.updateEntity(entity, request);
        Faq updated = faqRepository.save(entity);
        return FaqMapper.toResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FaqResponse> getAllFaqs(Pageable pageable) {
        return faqRepository.findAll(pageable)
                .map(FaqMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<FaqResponse> getActiveFaqs() {
        return faqRepository.findAll().stream()
                .filter(com.veritaschambers.entity.Faq::getIsActive)
                .map(FaqMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FaqResponse getFaqById(Long id) {
        return faqRepository.findById(id)
                .map(FaqMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("FAQ not found"));
    }

    @Override
    @Transactional
    public void deleteFaq(Long id) {
        if (!faqRepository.existsById(id)) {
            throw new ResourceNotFoundException("FAQ not found");
        }
        faqRepository.deleteById(id);
    }
}
