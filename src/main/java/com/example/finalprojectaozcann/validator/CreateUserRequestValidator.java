package com.example.finalprojectaozcann.validator;

import com.example.finalprojectaozcann.exception.BaseValidationException;
import com.example.finalprojectaozcann.exception.ValidationOperationException;
import com.example.finalprojectaozcann.model.request.CreateUserRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component
public class CreateUserRequestValidator implements Validator<CreateUserRequest> {
    @Override
    public void validate(CreateUserRequest request) throws BaseValidationException {
        if (Objects.isNull(request)) {
            throw new ValidationOperationException.UserNotValidException("User can not be null or empty");
        }
        if (!(StringUtils.hasLength(request.name()))) {
            throw new ValidationOperationException.UserNotValidException("User name can not be null or empty");
        }
        if (!(StringUtils.hasLength(request.surname()))) {
            throw new ValidationOperationException.UserNotValidException("User surname can not be null or empty");
        }
        if (Objects.isNull(request.identityNumber())) {
            throw new ValidationOperationException.UserNotValidException("User identity can not be null or empty");
        }

        if (Objects.isNull(request.birthday())) {
            throw new ValidationOperationException.UserNotValidException("User birthday can not be null or empty");
        }
        if (!(StringUtils.hasLength(request.phoneNumber()))) {
            throw new ValidationOperationException.UserNotValidException("User phoneNumber can not be null or empty");
        }
        if (!(StringUtils.hasLength(request.address()))) {
            throw new ValidationOperationException.UserNotValidException("User address can not be null or empty");
        }
        if (!(StringUtils.hasLength(request.email()))) {
            throw new ValidationOperationException.UserNotValidException("User email can not be null or empty");
        }
        if (!(StringUtils.hasLength(request.password()))) {
            throw new ValidationOperationException.UserNotValidException("User password can not be null or empty");
        }
        if (Objects.isNull(request.roles())) {
            throw new ValidationOperationException.UserNotValidException("User roles can not be null or empty");
        }

    }
}
