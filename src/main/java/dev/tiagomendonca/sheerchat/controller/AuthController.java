package dev.tiagomendonca.sheerchat.controller;

import dev.tiagomendonca.sheerchat.dto.RegisterRequest;
import dev.tiagomendonca.sheerchat.dto.RegisterResponse;
import dev.tiagomendonca.sheerchat.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        try {
            RegisterResponse response = userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            RegisterResponse errorResponse = new RegisterResponse(e.getMessage(), null, null, false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
