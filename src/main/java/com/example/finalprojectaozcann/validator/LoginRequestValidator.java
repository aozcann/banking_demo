package com.example.finalprojectaozcann.validator;

import com.example.finalprojectaozcann.config.Constants;
import com.example.finalprojectaozcann.exception.BaseValidationException;
import com.example.finalprojectaozcann.model.request.LoginRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component
public class LoginRequestValidator implements Validator<LoginRequest> {


    @Override
    public void validate(LoginRequest request) throws BaseValidationException {
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException(Constants.ValidationErrorMessage.REQUEST_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (Objects.isNull(request.identityNumber())) {
            throw new IllegalArgumentException(Constants.ValidationErrorMessage.USER_IDENTITY_NUMBER_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (!(StringUtils.hasLength(request.password()))) {
            throw new IllegalArgumentException(Constants.ValidationErrorMessage.USER_PASSWORD_CAN_NOT_BE_NULL_OR_EMPTY);
        }
    }
}

