package com.example.finalprojectaozcann.validator;

import com.example.finalprojectaozcann.config.Constants;
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
            throw new ValidationOperationException.BankAccountNotValidException(Constants.ValidationErrorMessage.REQUEST_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (Objects.isNull(request.currency())) {
            throw new ValidationOperationException.BankAccountNotValidException(Constants.ValidationErrorMessage.ACCOUNT_CURRENCY_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (Objects.isNull(request.maturity())) {
            throw new ValidationOperationException.BankAccountNotValidException(Constants.ValidationErrorMessage.MATURITY_CAN_NOT_BE_NULL_OR_EMPTY);
        }
    }
}