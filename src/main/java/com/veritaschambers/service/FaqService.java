package com.veritaschambers.service;

import com.veritaschambers.dto.request.FaqCreateRequest;
import com.veritaschambers.dto.request.FaqUpdateRequest;
import com.veritaschambers.dto.response.FaqResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FaqService {
    FaqResponse createFaq(FaqCreateRequest request);
    FaqResponse updateFaq(Long id, FaqUpdateRequest request);
    Page<FaqResponse> getAllFaqs(Pageable pageable);
    java.util.List<FaqResponse> getActiveFaqs();
    FaqResponse getFaqById(Long id);
    void deleteFaq(Long id);
}
