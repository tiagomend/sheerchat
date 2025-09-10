package dev.tiagomendonca.sheerchat.dto;

import java.util.UUID;

public record UserResponse(
    UUID id, String firstName, String lastName, String username, String email) { }
