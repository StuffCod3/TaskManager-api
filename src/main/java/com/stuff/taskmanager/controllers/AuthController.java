package com.stuff.taskmanager.controllers;

import com.stuff.taskmanager.dtos.AuthDto;
import com.stuff.taskmanager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody AuthDto authDto){
        return ResponseEntity.ok(userService.createAuthToken(authDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody AuthDto authDto){
        return ResponseEntity.ok(userService.signUp(authDto));
    }
}
