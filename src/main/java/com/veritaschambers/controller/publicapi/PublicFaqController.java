package com.veritaschambers.controller.publicapi;

import com.veritaschambers.dto.response.FaqResponse;
import com.veritaschambers.service.FaqService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/faqs")
public class PublicFaqController {

    private final FaqService faqService;

    public PublicFaqController(FaqService faqService) {
        this.faqService = faqService;
    }

    @GetMapping
    public ResponseEntity<List<FaqResponse>> getActiveFaqs() {
        return ResponseEntity.ok(faqService.getActiveFaqs());
    }
}
