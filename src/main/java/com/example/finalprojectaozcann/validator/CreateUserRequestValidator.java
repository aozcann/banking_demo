package com.example.finalprojectaozcann.validator;

import com.example.finalprojectaozcann.config.Constants;
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
            throw new ValidationOperationException.UserNotValidException(Constants.ValidationErrorMessage.USER_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (!(StringUtils.hasLength(request.name()))) {
            throw new ValidationOperationException.UserNotValidException(Constants.ValidationErrorMessage.USER_NAME_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (!(StringUtils.hasLength(request.surname()))) {
            throw new ValidationOperationException.UserNotValidException(Constants.ValidationErrorMessage.USER_SURNAME_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (Objects.isNull(request.identityNumber())) {
            throw new ValidationOperationException.UserNotValidException(Constants.ValidationErrorMessage.USER_IDENTITY_NUMBER_CAN_NOT_BE_NULL_OR_EMPTY);
        }

        if (Objects.isNull(request.birthday())) {
            throw new ValidationOperationException.UserNotValidException(Constants.ValidationErrorMessage.USER_BIRTHDAY_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (!(StringUtils.hasLength(request.phoneNumber()))) {
            throw new ValidationOperationException.UserNotValidException(Constants.ValidationErrorMessage.USER_PHONE_NUMBER_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (!(StringUtils.hasLength(request.address()))) {
            throw new ValidationOperationException.UserNotValidException(Constants.ValidationErrorMessage.USER_ADDRESS_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (!(StringUtils.hasLength(request.email()))) {
            throw new ValidationOperationException.UserNotValidException(Constants.ValidationErrorMessage.USER_EMAIL_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (!(StringUtils.hasLength(request.password()))) {
            throw new ValidationOperationException.UserNotValidException(Constants.ValidationErrorMessage.USER_PASSWORD_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (Objects.isNull(request.roles())) {
            throw new ValidationOperationException.UserNotValidException(Constants.ValidationErrorMessage.USER_ROLES_CAN_NOT_BE_NULL_OR_EMPTY);
        }
    }
}
