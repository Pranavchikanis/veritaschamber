package com.veritaschambers.service;

import com.veritaschambers.dto.request.ContactMessageCreateRequest;
import com.veritaschambers.dto.response.ContactMessageResponse;
import com.veritaschambers.entity.ContactMessage;
import com.veritaschambers.entity.enums.MessageStatus;
import com.veritaschambers.exception.ResourceNotFoundException;
import com.veritaschambers.repository.ContactMessageRepository;
import com.veritaschambers.service.impl.ContactMessageServiceImpl;
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
class ContactMessageServiceTest {

    @Mock
    private ContactMessageRepository contactMessageRepository;

    @InjectMocks
    private ContactMessageServiceImpl contactMessageService;

    private ContactMessage contactMessage;

    @BeforeEach
    void setUp() {
        contactMessage = new ContactMessage();
        contactMessage.setId(1L);
        contactMessage.setName("John Doe");
        contactMessage.setEmail("john@example.com");
        contactMessage.setSubject("General Inquiry");
        contactMessage.setMessage("Hello");
        contactMessage.setStatus(MessageStatus.NEW);
    }

    @Test
    void createMessage_Success() {
        ContactMessageCreateRequest request = new ContactMessageCreateRequest("Jane Doe", "jane@example.com", "1234567890", "Subject", "Msg");
        
        when(contactMessageRepository.save(any(ContactMessage.class))).thenAnswer(i -> {
            ContactMessage msg = i.getArgument(0);
            msg.setId(2L);
            return msg;
        });

        ContactMessageResponse response = contactMessageService.createContactMessage(request);

        assertNotNull(response);
        assertEquals(2L, response.id());
        assertEquals("Jane Doe", response.name());
        assertEquals(MessageStatus.NEW, response.status());
    }

    @Test
    void updateStatus_Success() {
        when(contactMessageRepository.findById(1L)).thenReturn(Optional.of(contactMessage));
        when(contactMessageRepository.save(any(ContactMessage.class))).thenReturn(contactMessage);

        ContactMessageResponse response = contactMessageService.updateContactMessageStatus(1L, MessageStatus.READ);

        assertEquals(MessageStatus.READ, contactMessage.getStatus());
        assertEquals(MessageStatus.READ, response.status());
    }

    @Test
    void updateStatus_NotFound_ShouldThrow() {
        when(contactMessageRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> contactMessageService.updateContactMessageStatus(99L, MessageStatus.READ));
    }
}
