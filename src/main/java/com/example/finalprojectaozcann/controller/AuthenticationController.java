package com.example.finalprojectaozcann.controller;

import com.example.finalprojectaozcann.model.request.LoginRequest;
import com.example.finalprojectaozcann.service.AuthenticationService;
import com.example.finalprojectaozcann.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final Validator<LoginRequest> loginRequestValidatorValidator;
    private final AuthenticationService authenticationService;

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        loginRequestValidatorValidator.validate(request);
        return ResponseEntity.ok(authenticationService.login(request));
    }

}
