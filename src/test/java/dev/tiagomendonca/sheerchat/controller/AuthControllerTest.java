package dev.tiagomendonca.sheerchat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tiagomendonca.sheerchat.dto.RegisterRequest;
import dev.tiagomendonca.sheerchat.dto.RegisterResponse;
import dev.tiagomendonca.sheerchat.exception.DatabaseCommunicationException;
import dev.tiagomendonca.sheerchat.exception.EmailAlreadyExistsException;
import dev.tiagomendonca.sheerchat.exception.UsernameAlreadyExistsException;
import dev.tiagomendonca.sheerchat.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(dev.tiagomendonca.sheerchat.config.SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void testRegisterUser_Success() throws Exception {
        RegisterRequest request = new RegisterRequest("testuser", "test@example.com", "Password123!");
        RegisterResponse response = new RegisterResponse("Conta criada com sucesso", 1L, "testuser", false);

        when(userService.registerUser(any(RegisterRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Conta criada com sucesso"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.emailConfirmationSent").value(false));
    }

    @Test
    void testRegisterUser_UsernameAlreadyExists() throws Exception {
        RegisterRequest request = new RegisterRequest("existinguser", "test@example.com", "Password123!");

        when(userService.registerUser(any(RegisterRequest.class)))
                .thenThrow(new UsernameAlreadyExistsException("Username already exists"));

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username already exists"))
                .andExpect(jsonPath("$.errorCode").value("USERNAME_ALREADY_EXISTS"));
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() throws Exception {
        RegisterRequest request = new RegisterRequest("newuser", "existing@example.com", "Password123!");

        when(userService.registerUser(any(RegisterRequest.class)))
                .thenThrow(new EmailAlreadyExistsException("Email already exists"));

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email already exists"))
                .andExpect(jsonPath("$.errorCode").value("EMAIL_ALREADY_EXISTS"));
    }

    @Test
    void testRegisterUser_InvalidPassword_MissingNumbers() throws Exception {
        RegisterRequest request = new RegisterRequest("testuser", "test@example.com", "Password!");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegisterUser_InvalidPassword_MissingSymbols() throws Exception {
        RegisterRequest request = new RegisterRequest("testuser", "test@example.com", "Password123");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegisterUser_InvalidPassword_MissingLetters() throws Exception {
        RegisterRequest request = new RegisterRequest("testuser", "test@example.com", "12345678!");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegisterUser_InvalidPassword_TooShort() throws Exception {
        RegisterRequest request = new RegisterRequest("testuser", "test@example.com", "Pass1!");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegisterUser_InvalidEmail() throws Exception {
        RegisterRequest request = new RegisterRequest("testuser", "invalid-email", "Password123!");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegisterUser_EmptyFields() throws Exception {
        RegisterRequest request = new RegisterRequest("", "", "");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegisterUser_DatabaseCommunicationError() throws Exception {
        RegisterRequest request = new RegisterRequest("testuser", "test@example.com", "Password123!");

        when(userService.registerUser(any(RegisterRequest.class)))
                .thenThrow(new DatabaseCommunicationException("Erro ao comunicar com o banco de dados. Tente novamente mais tarde.", new RuntimeException()));

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Erro ao comunicar com o banco de dados. Tente novamente mais tarde."));
    }
}
