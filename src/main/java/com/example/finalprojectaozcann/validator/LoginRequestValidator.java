package com.example.finalprojectaozcann.validator;

import com.example.finalprojectaozcann.model.request.LoginRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component
public class LoginRequestValidator implements Validator<LoginRequest> {


    @Override
    public void validate(LoginRequest request) {
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException("Request can not be null");
        }
        if (Objects.isNull(request.identityNumber())) {
            throw new IllegalArgumentException("IdentityNumber can not be null");
        }
        if (!(StringUtils.hasLength(request.password()))) {
            throw new IllegalArgumentException("Password can not be null");
        }
    }
}

