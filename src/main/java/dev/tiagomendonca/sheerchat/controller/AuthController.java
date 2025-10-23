package dev.tiagomendonca.sheerchat.controller;

import dev.tiagomendonca.sheerchat.dto.RegisterRequest;
import dev.tiagomendonca.sheerchat.dto.RegisterResponse;
import dev.tiagomendonca.sheerchat.exception.DatabaseCommunicationException;
import dev.tiagomendonca.sheerchat.exception.EmailAlreadyExistsException;
import dev.tiagomendonca.sheerchat.exception.UsernameAlreadyExistsException;
import dev.tiagomendonca.sheerchat.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        try {
            RegisterResponse response = userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UsernameAlreadyExistsException e) {
            RegisterResponse errorResponse = new RegisterResponse(e.getMessage(), null, null, false, e.getErrorCode());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (EmailAlreadyExistsException e) {
            RegisterResponse errorResponse = new RegisterResponse(e.getMessage(), null, null, false, e.getErrorCode());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (DatabaseCommunicationException e) {
            RegisterResponse errorResponse = new RegisterResponse(e.getMessage(), null, null, false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
