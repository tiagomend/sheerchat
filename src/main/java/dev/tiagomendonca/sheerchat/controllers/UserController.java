package dev.tiagomendonca.sheerchat.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.tiagomendonca.sheerchat.dto.UserRegister;
import dev.tiagomendonca.sheerchat.dto.UserResponse;
import dev.tiagomendonca.sheerchat.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegister dto) {
        UserResponse createdUser = userService.registerUser(dto);
        return ResponseEntity.ok(createdUser);
    } 
}
