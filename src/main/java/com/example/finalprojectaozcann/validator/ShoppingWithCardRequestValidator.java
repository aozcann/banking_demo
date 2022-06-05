package com.example.finalprojectaozcann.validator;

import com.example.finalprojectaozcann.config.Constants;
import com.example.finalprojectaozcann.exception.BaseValidationException;
import com.example.finalprojectaozcann.exception.ValidationOperationException;
import com.example.finalprojectaozcann.model.request.ShoppingWithCardRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static java.math.BigDecimal.ZERO;

@Component
public class ShoppingWithCardRequestValidator implements Validator<ShoppingWithCardRequest> {
    @Override
    public void validate(ShoppingWithCardRequest request) throws BaseValidationException {
        if (Objects.isNull(request)) {
            throw new ValidationOperationException.ShoppingRequestNotValid(Constants.ValidationErrorMessage.REQUEST_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (!(StringUtils.hasLength(request.cardNumber()))) {
            throw new ValidationOperationException.ShoppingRequestNotValid(Constants.ValidationErrorMessage.CARD_NUMBER_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (!(StringUtils.hasLength(request.expiryDate()))) {
            throw new ValidationOperationException.ShoppingRequestNotValid(Constants.ValidationErrorMessage.EXPIRY_DATE_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (!(StringUtils.hasLength(request.ccv()))) {
            throw new ValidationOperationException.ShoppingRequestNotValid(Constants.ValidationErrorMessage.CCV_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (!(StringUtils.hasLength(request.password()))) {
            throw new ValidationOperationException.ShoppingRequestNotValid(Constants.ValidationErrorMessage.CARD_PASSWORD_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (!(StringUtils.hasLength(request.payeeIBAN()))) {
            throw new ValidationOperationException.ShoppingRequestNotValid(Constants.ValidationErrorMessage.IBAN_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (request.price().compareTo(ZERO) <= 0) {
            throw new ValidationOperationException.ShoppingRequestNotValid(Constants.ValidationErrorMessage.PRICE_CAN_NOT_BE_LESS_THAN_ZERO);
        }
    }
}
