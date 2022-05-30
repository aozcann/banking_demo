package com.example.finalprojectaozcann.validator;

import com.example.finalprojectaozcann.exception.BaseValidationException;
import com.example.finalprojectaozcann.exception.ValidationOperationException;
import com.example.finalprojectaozcann.model.request.CreateDepositAccountRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CreateDepositAccountRequestValidator implements Validator<CreateDepositAccountRequest> {
    @Override
    public void validate(CreateDepositAccountRequest request) throws BaseValidationException {
        if (Objects.isNull(request)) {
            throw new ValidationOperationException.BankAccountNotValidException("Request can not be null or empty");
        }
        if (Objects.isNull(request.currency())) {
            throw new ValidationOperationException.BankAccountNotValidException("Account currency can not be null or empty");
        }
        if (Objects.isNull(request.maturity())) {
            throw new ValidationOperationException.BankAccountNotValidException("Maturity can not be null or empty");
        }
    }
}