package com.example.finalprojectaozcann.validator;

import com.example.finalprojectaozcann.exception.BaseValidationException;
import com.example.finalprojectaozcann.exception.ValidationOperationException;
import com.example.finalprojectaozcann.model.request.CreateCardRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component
public class CreateCardRequestValidator implements Validator<CreateCardRequest> {
    @Override
    public void validate(CreateCardRequest request) throws BaseValidationException {
        if (Objects.isNull(request)) {
            throw new ValidationOperationException.BankCardNotValidException("Request can not be null or empty");
        }
        if (!(StringUtils.hasLength(request.accountNumber()))) {
            throw new ValidationOperationException.BankCardNotValidException("AccountNumber can not be null or empty");
        }
    }
}

