package com.ntu.messenger.api.controller;

import com.ntu.messenger.api.service.UserService;
import com.ntu.messenger.data.dto.user.UserCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/sign-up")
public class RegistrationController {

    private final UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signUp(@RequestBody @Valid UserCreateDto user) {
        return userService.signUpUser(user) ? ResponseEntity.status(201).body("Created") : ResponseEntity.status(400).body("Rejected");
    }
}
