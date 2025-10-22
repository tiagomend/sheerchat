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
        try {
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
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao comunicar com o banco de dados. Tente novamente mais tarde.", e);
        }
    }

    private boolean sendConfirmationEmail(User user) {
        // TODO: Implement email confirmation logic
        // Email sending is not yet implemented. When emailConfirmationEnabled is true,
        // this method should send a confirmation email to the user.
        return false;
    }
}
