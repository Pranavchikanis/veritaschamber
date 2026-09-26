package com.veritaschambers.controller.adminapi;

import com.veritaschambers.dto.response.ContactMessageResponse;
import com.veritaschambers.entity.enums.MessageStatus;
import com.veritaschambers.service.ContactMessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/contact-messages")
public class AdminContactMessageController {

    private final ContactMessageService contactMessageService;

    public AdminContactMessageController(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    @GetMapping
    public ResponseEntity<Page<ContactMessageResponse>> getAllContactMessages(Pageable pageable) {
        return ResponseEntity.ok(contactMessageService.getAllContactMessages(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactMessageResponse> getContactMessageById(@PathVariable Long id) {
        return ResponseEntity.ok(contactMessageService.getContactMessageById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ContactMessageResponse> updateContactMessageStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusUpdate) {
        MessageStatus status = MessageStatus.valueOf(statusUpdate.get("status").toUpperCase());
        return ResponseEntity.ok(contactMessageService.updateContactMessageStatus(id, status));
    }
}
