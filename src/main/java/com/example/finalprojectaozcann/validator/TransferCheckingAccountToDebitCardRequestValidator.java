package com.example.finalprojectaozcann.validator;

import com.example.finalprojectaozcann.config.Constants;
import com.example.finalprojectaozcann.exception.BaseValidationException;
import com.example.finalprojectaozcann.exception.ValidationOperationException;
import com.example.finalprojectaozcann.model.request.TransferCheckingAccountToDebitCardRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static java.math.BigDecimal.ZERO;

@Component
public class TransferCheckingAccountToDebitCardRequestValidator implements Validator<TransferCheckingAccountToDebitCardRequest> {
    @Override
    public void validate(TransferCheckingAccountToDebitCardRequest request) throws BaseValidationException {
        if (Objects.isNull(request)) {
            throw new ValidationOperationException.TransferRequestNotValid(Constants.ValidationErrorMessage.REQUEST_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (!(StringUtils.hasLength(request.senderIban()))) {
            throw new ValidationOperationException.TransferRequestNotValid(Constants.ValidationErrorMessage.SENDER_IBAN_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (!(StringUtils.hasLength(request.cardNumber()))) {
            throw new ValidationOperationException.TransferRequestNotValid(Constants.ValidationErrorMessage.CARD_NUMBER_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (request.amount().compareTo(ZERO) <= 0) {
            throw new ValidationOperationException.TransferRequestNotValid(Constants.ValidationErrorMessage.AMOUNT_CAN_NOT_BE_LESS_THAN_ZERO);
        }
        if (!(StringUtils.hasLength(request.transferDate()))) {
            throw new ValidationOperationException.TransferRequestNotValid(Constants.ValidationErrorMessage.TRANSFER_DATE_CAN_NOT_BE_NULL_OR_EMPTY);
        }
    }
}
