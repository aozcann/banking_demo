package com.example.finalprojectaozcann.controller;


import com.example.finalprojectaozcann.model.entity.Role;
import com.example.finalprojectaozcann.model.enums.RoleType;
import com.example.finalprojectaozcann.model.enums.UserType;
import com.example.finalprojectaozcann.model.request.CreateUserRequest;
import com.example.finalprojectaozcann.model.response.CreateUserResponse;
import com.example.finalprojectaozcann.model.response.GenerateAdminUserResponse;
import com.example.finalprojectaozcann.service.UserService;
import com.example.finalprojectaozcann.validator.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    private Validator<CreateUserRequest> createUserRequestValidator;

    @Test
    void should_returnAdminUser_when_generateAdminUserMethod() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));


        Role role = new Role();
        role.setName(RoleType.ADMIN.toString());
        GenerateAdminUserResponse response = new GenerateAdminUserResponse(10_000_000 - 000L, "12345", new HashSet<>(List.of(role)));

        when(userService.generateAdminUser()).thenReturn(response);

        ResponseEntity<GenerateAdminUserResponse> responseEntity = userController.generateAdminUser();

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody().identityNumber()).isEqualTo(10_000_000 - 000L);
        assertThat(responseEntity.getBody().password()).isEqualTo("12345");
        assertThat(responseEntity.getBody().role()).isEqualTo(new HashSet<>(List.of(role)));
    }

    @Test
    void should_returnUser_whenCreateUserMethod() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        CreateUserResponse response = new CreateUserResponse(1L);

        CreateUserRequest createUserRequest = new CreateUserRequest("Ahmet",
                "Ozcan",
                12345678912L,
                "11/09/1994",
                "05419322436",
                "Ankara",
                "ozcanahmet94@gmail.com",
                "a1b2c3",
                UserType.INDIVIDUAL,
                new HashSet<>(List.of(RoleType.USER)));

        when(userService.create(createUserRequest, (request))).thenReturn(response);

        ResponseEntity<CreateUserResponse> responseEntity = (ResponseEntity<CreateUserResponse>) userController.create(createUserRequest, request);

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody().id()).isEqualTo(1L);

    }


}
