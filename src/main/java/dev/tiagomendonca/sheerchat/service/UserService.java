package dev.tiagomendonca.sheerchat.service;

import dev.tiagomendonca.sheerchat.dto.RegisterRequest;
import dev.tiagomendonca.sheerchat.dto.RegisterResponse;
import dev.tiagomendonca.sheerchat.model.User;
import dev.tiagomendonca.sheerchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Value("${app.email.confirmation.enabled:false}")
    private boolean emailConfirmationEnabled;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmailConfirmed(!emailConfirmationEnabled);

        User savedUser = userRepository.save(user);

        boolean emailSent = false;
        if (emailConfirmationEnabled) {
            emailSent = sendConfirmationEmail(savedUser);
        }

        return new RegisterResponse(
            "Conta criada com sucesso",
            savedUser.getId(),
            savedUser.getUsername(),
            emailSent
        );
    }

    private boolean sendConfirmationEmail(User user) {
        return false;
    }
}
