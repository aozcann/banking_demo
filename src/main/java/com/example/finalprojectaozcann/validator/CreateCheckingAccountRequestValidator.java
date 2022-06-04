package com.example.finalprojectaozcann.validator;

import com.example.finalprojectaozcann.config.Constants;
import com.example.finalprojectaozcann.exception.BaseValidationException;
import com.example.finalprojectaozcann.exception.ValidationOperationException;
import com.example.finalprojectaozcann.model.request.CreateCheckingAccountRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CreateCheckingAccountRequestValidator implements Validator<CreateCheckingAccountRequest> {
    @Override
    public void validate(CreateCheckingAccountRequest request) throws BaseValidationException {

        if (Objects.isNull(request)) {
            throw new ValidationOperationException.BankAccountNotValidException(Constants.ValidationErrorMessage.REQUEST_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (Objects.isNull(request.currency())) {
            throw new ValidationOperationException.BankAccountNotValidException(Constants.ValidationErrorMessage.ACCOUNT_CURRENCY_CAN_NOT_BE_NULL_OR_EMPTY);
        }
    }

}
