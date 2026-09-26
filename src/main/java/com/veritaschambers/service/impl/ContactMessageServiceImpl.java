package com.veritaschambers.service.impl;

import com.veritaschambers.dto.request.ContactMessageCreateRequest;
import com.veritaschambers.dto.response.ContactMessageResponse;
import com.veritaschambers.entity.ContactMessage;
import com.veritaschambers.entity.enums.MessageStatus;
import com.veritaschambers.exception.ResourceNotFoundException;
import com.veritaschambers.mapper.ContactMessageMapper;
import com.veritaschambers.repository.ContactMessageRepository;
import com.veritaschambers.service.ContactMessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactMessageServiceImpl implements ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    public ContactMessageServiceImpl(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    @Override
    @Transactional
    public ContactMessageResponse createContactMessage(ContactMessageCreateRequest request) {
        ContactMessage entity = ContactMessageMapper.toEntity(request);
        ContactMessage saved = contactMessageRepository.save(entity);
        return ContactMessageMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactMessageResponse> getAllContactMessages(Pageable pageable) {
        return contactMessageRepository.findAll(pageable)
                .map(ContactMessageMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ContactMessageResponse getContactMessageById(Long id) {
        return contactMessageRepository.findById(id)
                .map(ContactMessageMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Contact message not found with id: " + id));
    }

    @Override
    @Transactional
    public ContactMessageResponse updateContactMessageStatus(Long id, MessageStatus status) {
        ContactMessage entity = contactMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact message not found with id: " + id));
        entity.setStatus(status);
        ContactMessage updated = contactMessageRepository.save(entity);
        return ContactMessageMapper.toResponse(updated);
    }
}
