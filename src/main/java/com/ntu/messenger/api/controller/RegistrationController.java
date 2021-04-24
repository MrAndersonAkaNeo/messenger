package com.ntu.messenger.api.controller;

import com.ntu.messenger.api.service.RegistrationService;
import com.ntu.messenger.data.dto.user.UserCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/sign-up")
    public ResponseEntity<String> signUp(@RequestBody @Valid UserCreateDto user) {
        return registrationService.signUpUser(user) ? ResponseEntity.status(201).body("Created") : ResponseEntity.status(400).body("Rejected");
    }
}
