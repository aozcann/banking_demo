package com.example.finalprojectaozcann.controller;

import com.example.finalprojectaozcann.model.enums.RoleType;
import com.example.finalprojectaozcann.model.request.LoginRequest;
import com.example.finalprojectaozcann.model.response.LoginResponse;
import com.example.finalprojectaozcann.service.AuthenticationService;
import com.example.finalprojectaozcann.validator.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @InjectMocks
    AuthenticationController authenticationController;

    @Mock
    AuthenticationService authenticationService;

    @Mock
    private Validator<LoginRequest> loginRequestValidator;

    @Test
    void should_returnLoginResponse_when_loginMethod() throws Exception {

        LoginResponse response = new LoginResponse("adsasdasd",
                new HashSet<>(List.of(RoleType.ADMIN.toString())));

        LoginRequest request = new LoginRequest(12345678911L, "a1b2c3");

        when(authenticationService.login(request)).thenReturn(response);

        ResponseEntity<LoginResponse> responseEntity = (ResponseEntity<LoginResponse>) authenticationController.login(request);

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).token()).isEqualTo("adsasdasd");
        assertThat(responseEntity.getBody().roles()).isEqualTo(new HashSet<>(List.of(RoleType.ADMIN.toString())));

    }


}
