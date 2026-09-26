package com.veritaschambers.controller.publicapi;

import com.veritaschambers.dto.request.ContactMessageCreateRequest;
import com.veritaschambers.dto.response.ContactMessageResponse;
import com.veritaschambers.service.ContactMessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/contact-messages")
public class PublicContactMessageController {

    private final ContactMessageService contactMessageService;

    public PublicContactMessageController(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    @PostMapping
    public ResponseEntity<ContactMessageResponse> createContactMessage(
            @Valid @RequestBody ContactMessageCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contactMessageService.createContactMessage(request));
    }
}
