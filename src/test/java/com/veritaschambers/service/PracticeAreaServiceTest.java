package com.veritaschambers.service;

import com.veritaschambers.dto.request.PracticeAreaCreateRequest;
import com.veritaschambers.dto.request.PracticeAreaUpdateRequest;
import com.veritaschambers.dto.response.PracticeAreaResponse;
import com.veritaschambers.entity.PracticeArea;
import com.veritaschambers.entity.enums.ContentStatus;
import com.veritaschambers.exception.ResourceNotFoundException;
import com.veritaschambers.repository.PracticeAreaRepository;
import com.veritaschambers.service.impl.PracticeAreaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PracticeAreaServiceTest {

    @Mock
    private PracticeAreaRepository practiceAreaRepository;

    @InjectMocks
    private PracticeAreaServiceImpl practiceAreaService;

    private PracticeArea practiceArea;

    @BeforeEach
    void setUp() {
        practiceArea = new PracticeArea();
        practiceArea.setId(1L);
        practiceArea.setTitle("Family Law");
        practiceArea.setSlug("family-law");
        practiceArea.setShortDescription("Excerpt");
        practiceArea.setDescription("Description");
        practiceArea.setStatus(ContentStatus.PUBLISHED);
    }

    @Test
    void createPracticeArea_Success() {
        PracticeAreaCreateRequest request = new PracticeAreaCreateRequest("Civil Law", "civil-law", "Exc", "Desc", "icon", 1, ContentStatus.PUBLISHED);
        
        when(practiceAreaRepository.save(any(PracticeArea.class))).thenAnswer(i -> {
            PracticeArea pa = i.getArgument(0);
            pa.setId(2L);
            return pa;
        });

        PracticeAreaResponse response = practiceAreaService.createPracticeArea(request);

        assertNotNull(response);
        assertEquals(2L, response.id());
        assertEquals("Civil Law", response.title());
    }

    @Test
    void getPracticeAreaById_NotFound_ShouldThrow() {
        when(practiceAreaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> practiceAreaService.getPracticeAreaById(99L));
    }
}
