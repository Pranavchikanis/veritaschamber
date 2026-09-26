package com.veritaschambers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veritaschambers.dto.request.LoginRequest;
import com.veritaschambers.entity.AdminUser;
import com.veritaschambers.repository.AdminUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
@org.springframework.test.context.ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AdminUserRepository adminUserRepository;

    @Test
    void login_success() throws Exception {
        String rawPassword = "password123";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        AdminUser mockUser = new AdminUser();
        mockUser.setId(1L);
        mockUser.setEmail("admin@example.invalid");
        mockUser.setPasswordHash(encodedPassword);
        mockUser.setRole(com.veritaschambers.entity.enums.Role.ROLE_ADMIN);
        mockUser.setName("Admin User");
        mockUser.setIsActive(true);

        when(adminUserRepository.findByEmail("admin@example.invalid"))
                .thenReturn(Optional.of(mockUser));

        LoginRequest loginRequest = new LoginRequest("admin@example.invalid", rawPassword);

        mockMvc.perform(post("/api/v1/auth/login").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Admin User"))
                .andExpect(jsonPath("$.role").value("ROLE_ADMIN"));
    }

    @Test
    void login_failure_badCredentials() throws Exception {
        String encodedPassword = passwordEncoder.encode("password123");

        AdminUser mockUser = new AdminUser();
        mockUser.setId(1L);
        mockUser.setEmail("admin@example.invalid");
        mockUser.setPasswordHash(encodedPassword);
        mockUser.setRole(com.veritaschambers.entity.enums.Role.ROLE_ADMIN);
        mockUser.setName("Admin User");
        mockUser.setIsActive(true);

        when(adminUserRepository.findByEmail("admin@example.invalid"))
                .thenReturn(Optional.of(mockUser));

        LoginRequest loginRequest = new LoginRequest("admin@example.invalid", "wrongpassword");

        mockMvc.perform(post("/api/v1/auth/login").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }
}
