package dev.tiagomendonca.sheerchat.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.tiagomendonca.sheerchat.dto.UserRegister;
import dev.tiagomendonca.sheerchat.dto.UserResponse;
import dev.tiagomendonca.sheerchat.entities.User;
import dev.tiagomendonca.sheerchat.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Transactional
    public UserResponse registerUser(UserRegister dto) {
        if (repository.existsByUsername(dto.username())) {
            throw new IllegalArgumentException("Username already in use");
        }

        User user = 
        User.builder()
            .firstName(dto.firstName())
            .lastName(dto.lastName())
            .username(dto.username())
            .email(dto.email())
            .password(encoder.encode(dto.password()))
            .build();

        var saved = repository.save(user);

        return new UserResponse(
            saved.getId(),
            saved.getFirstName(),
            saved.getLastName(),
            saved.getUsername(),
            saved.getEmail()
        );
    }
}
