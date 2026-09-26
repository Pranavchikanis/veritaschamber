package com.veritaschambers.service;

import com.veritaschambers.dto.request.PracticeAreaCreateRequest;
import com.veritaschambers.dto.request.PracticeAreaUpdateRequest;
import com.veritaschambers.dto.response.PracticeAreaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PracticeAreaService {
    PracticeAreaResponse createPracticeArea(PracticeAreaCreateRequest request);
    PracticeAreaResponse updatePracticeArea(Long id, PracticeAreaUpdateRequest request);
    Page<PracticeAreaResponse> getAllPracticeAreas(Pageable pageable);
    java.util.List<PracticeAreaResponse> getActivePracticeAreas();
    PracticeAreaResponse getPracticeAreaById(Long id);
    PracticeAreaResponse getPracticeAreaBySlug(String slug);
    void deletePracticeArea(Long id);
}
