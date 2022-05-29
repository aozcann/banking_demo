package com.example.finalprojectaozcann.validator;

import com.example.finalprojectaozcann.exception.BaseValidationException;
import com.example.finalprojectaozcann.exception.ValidationOperationException;
import com.example.finalprojectaozcann.model.request.CreateCheckingAccountRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class CreateCheckingAccountRequestValidator implements Validator<CreateCheckingAccountRequest> {
    @Override
    public void validate(CreateCheckingAccountRequest request) throws BaseValidationException {

        if (Objects.isNull(request)) {
            throw new ValidationOperationException.BankAccountNotValidException("BankAccount can not be null or empty");
        }
        if (Objects.isNull(request.currency())) {
            throw new ValidationOperationException.BankAccountNotValidException("Account currency can not be null or empty");
        }
        if (request.balance().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationOperationException.BankAccountNotValidException("Account balance can not be less than 0");
        }
    }

}
