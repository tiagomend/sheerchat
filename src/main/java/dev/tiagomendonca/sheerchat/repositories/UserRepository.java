package dev.tiagomendonca.sheerchat.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.tiagomendonca.sheerchat.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsername(String username);
}
