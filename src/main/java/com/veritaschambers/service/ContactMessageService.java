package com.veritaschambers.service;

import com.veritaschambers.dto.request.ContactMessageCreateRequest;
import com.veritaschambers.dto.response.ContactMessageResponse;
import com.veritaschambers.entity.enums.MessageStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactMessageService {
    ContactMessageResponse createContactMessage(ContactMessageCreateRequest request);
    Page<ContactMessageResponse> getAllContactMessages(Pageable pageable);
    ContactMessageResponse getContactMessageById(Long id);
    ContactMessageResponse updateContactMessageStatus(Long id, MessageStatus status);
}
