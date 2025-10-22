package dev.tiagomendonca.sheerchat.service;

import dev.tiagomendonca.sheerchat.dto.RegisterRequest;
import dev.tiagomendonca.sheerchat.dto.RegisterResponse;
import dev.tiagomendonca.sheerchat.model.User;
import dev.tiagomendonca.sheerchat.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userService, "emailConfirmationEnabled", false);
    }

    @Test
    void testRegisterUser_Success() {
        RegisterRequest request = new RegisterRequest("testuser", "test@example.com", "Password123!");
        User savedUser = new User("testuser", "test@example.com", "encodedPassword");
        savedUser.setId(1L);

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("Password123!")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        RegisterResponse response = userService.registerUser(request);

        assertNotNull(response);
        assertEquals("Conta criada com sucesso", response.getMessage());
        assertEquals(1L, response.getUserId());
        assertEquals("testuser", response.getUsername());
        assertFalse(response.isEmailConfirmationSent());

        verify(userRepository).existsByUsername("testuser");
        verify(userRepository).existsByEmail("test@example.com");
        verify(passwordEncoder).encode("Password123!");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterUser_UsernameExists() {
        RegisterRequest request = new RegisterRequest("existinguser", "test@example.com", "Password123!");

        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(request);
        });

        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository).existsByUsername("existinguser");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterUser_EmailExists() {
        RegisterRequest request = new RegisterRequest("newuser", "existing@example.com", "Password123!");

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(request);
        });

        assertEquals("Email already exists", exception.getMessage());
        verify(userRepository).existsByUsername("newuser");
        verify(userRepository).existsByEmail("existing@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterUser_WithEmailConfirmationEnabled() {
        ReflectionTestUtils.setField(userService, "emailConfirmationEnabled", true);

        RegisterRequest request = new RegisterRequest("testuser", "test@example.com", "Password123!");
        User savedUser = new User("testuser", "test@example.com", "encodedPassword");
        savedUser.setId(1L);

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("Password123!")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        RegisterResponse response = userService.registerUser(request);

        assertNotNull(response);
        assertEquals("Conta criada com sucesso", response.getMessage());
        assertFalse(response.isEmailConfirmationSent());
    }
}
