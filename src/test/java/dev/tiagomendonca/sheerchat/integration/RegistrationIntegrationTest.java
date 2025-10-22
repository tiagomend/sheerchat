package dev.tiagomendonca.sheerchat.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tiagomendonca.sheerchat.dto.RegisterRequest;
import dev.tiagomendonca.sheerchat.model.User;
import dev.tiagomendonca.sheerchat.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationIntegrationTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        @ServiceConnection
        MySQLContainer<?> mysqlContainer() {
            return new MySQLContainer<>(DockerImageName.parse("mysql:latest"));
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testCompleteRegistrationFlow() throws Exception {
        RegisterRequest request = new RegisterRequest("integrationuser", "integration@example.com", "password123");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Conta criada com sucesso"))
                .andExpect(jsonPath("$.username").value("integrationuser"))
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.emailConfirmationSent").value(false));

        Optional<User> savedUser = userRepository.findByUsername("integrationuser");
        assertTrue(savedUser.isPresent());
        assertEquals("integrationuser", savedUser.get().getUsername());
        assertEquals("integration@example.com", savedUser.get().getEmail());
        assertTrue(passwordEncoder.matches("password123", savedUser.get().getPassword()));
        assertTrue(savedUser.get().isEmailConfirmed());
    }

    @Test
    void testDuplicateUsernameRegistration() throws Exception {
        RegisterRequest request1 = new RegisterRequest("duplicateuser", "user1@example.com", "password123");
        
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request1))
                .with(csrf()))
                .andExpect(status().isCreated());

        RegisterRequest request2 = new RegisterRequest("duplicateuser", "user2@example.com", "password456");
        
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2))
                .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username already exists"));
    }

    @Test
    void testDuplicateEmailRegistration() throws Exception {
        RegisterRequest request1 = new RegisterRequest("user1", "duplicate@example.com", "password123");
        
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request1))
                .with(csrf()))
                .andExpect(status().isCreated());

        RegisterRequest request2 = new RegisterRequest("user2", "duplicate@example.com", "password456");
        
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2))
                .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email already exists"));
    }
}
