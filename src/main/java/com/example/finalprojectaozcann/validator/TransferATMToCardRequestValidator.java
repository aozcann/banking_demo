package com.example.finalprojectaozcann.validator;

import com.example.finalprojectaozcann.config.Constants;
import com.example.finalprojectaozcann.exception.BaseValidationException;
import com.example.finalprojectaozcann.exception.ValidationOperationException;
import com.example.finalprojectaozcann.model.request.TransferATMToCardRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static java.math.BigDecimal.ZERO;

@Component
public class TransferATMToCardRequestValidator implements Validator<TransferATMToCardRequest> {
    @Override
    public void validate(TransferATMToCardRequest request) throws BaseValidationException {
        if (Objects.isNull(request)) {
            throw new ValidationOperationException.TransferRequestNotValid(Constants.ValidationErrorMessage.REQUEST_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (!(StringUtils.hasLength(request.cardNumber()))) {
            throw new ValidationOperationException.TransferRequestNotValid(Constants.ValidationErrorMessage.CARD_NUMBER_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (!(StringUtils.hasLength(request.password()))) {
            throw new ValidationOperationException.TransferRequestNotValid(Constants.ValidationErrorMessage.CARD_PASSWORD_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (request.amount().compareTo(ZERO) <= 0) {
            throw new ValidationOperationException.TransferRequestNotValid(Constants.ValidationErrorMessage.AMOUNT_CAN_NOT_BE_LESS_THAN_ZERO);
        }
    }
}
