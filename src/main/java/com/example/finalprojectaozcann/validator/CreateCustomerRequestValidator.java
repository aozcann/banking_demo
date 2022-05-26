package com.example.finalprojectaozcann.validator;

import com.example.finalprojectaozcann.exception.BaseValidationException;
import com.example.finalprojectaozcann.exception.ValidationOperationException;
import com.example.finalprojectaozcann.model.request.CreateCustomerRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component
public class CreateCustomerRequestValidator implements Validator<CreateCustomerRequest> {
    @Override
    public void validate(CreateCustomerRequest request) throws BaseValidationException {
        if (Objects.isNull(request)) {
            throw new ValidationOperationException.CustomerNotValidException("Customer can not be null or empty");
        }
        if (!(StringUtils.hasLength(request.name()))) {
            throw new ValidationOperationException.CustomerNotValidException("Customer name can not be null or empty");
        }
        if (!(StringUtils.hasLength(request.surname()))) {
            throw new ValidationOperationException.CustomerNotValidException("Customer surname can not be null or empty");
        }
        if (Objects.isNull(request.identityNumber())) {
            throw new ValidationOperationException.CustomerNotValidException("Customer identity can not be null or empty");
        }

        if (Objects.isNull(request.birthday())) {
            throw new ValidationOperationException.CustomerNotValidException("Customer birthday can not be null or empty");
        }
        if (!(StringUtils.hasLength(request.phoneNumber()))) {
            throw new ValidationOperationException.CustomerNotValidException("Customer phoneNumber can not be null or empty");
        }
        if (!(StringUtils.hasLength(request.address()))) {
            throw new ValidationOperationException.CustomerNotValidException("Customer address can not be null or empty");
        }
        if (!(StringUtils.hasLength(request.email()))) {
            throw new ValidationOperationException.CustomerNotValidException("Customer email can not be null or empty");
        }
        if (!(StringUtils.hasLength(request.password()))) {
            throw new ValidationOperationException.CustomerNotValidException("Customer password can not be null or empty");
        }
        if (Objects.isNull(request.roles())) {
            throw new ValidationOperationException.CustomerNotValidException("Customer roles can not be null or empty");
        }

    }
}
