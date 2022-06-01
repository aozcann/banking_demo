package com.example.finalprojectaozcann.validator;

import com.example.finalprojectaozcann.exception.BaseValidationException;
import com.example.finalprojectaozcann.exception.ValidationOperationException;
import com.example.finalprojectaozcann.model.request.TransferCheckingAccountToDebitCardRequest;
import com.example.finalprojectaozcann.model.request.TransferToAccountRequest;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static java.math.BigDecimal.ZERO;

public class transferCheckingAccountToDebitCardRequestValidator implements Validator<TransferCheckingAccountToDebitCardRequest> {
    @Override
    public void validate(TransferCheckingAccountToDebitCardRequest request) throws BaseValidationException {
        if (Objects.isNull(request)) {
            throw new ValidationOperationException.TransferRequestNotValid("Request can not be null or empty");
        }
        if (!(StringUtils.hasLength(request.senderIban()))) {
            throw new ValidationOperationException.TransferRequestNotValid("Sender iban can not be null or empty");
        }
        if (!(StringUtils.hasLength(request.cardNumber()))) {
            throw new ValidationOperationException.TransferRequestNotValid("Card number can not be null or empty");
        }
        if (request.amount().compareTo(ZERO) <= 0) {
            throw new ValidationOperationException.TransferRequestNotValid("Amount can not be less than 0");
        }
        //TODO transferdate valid açılacak
//        if (!(StringUtils.hasLength(request.transferDate()))) {
//            throw new ValidationOperationException.TransferRequestNotValid("Transfer date can not be null or empty");
//        } {
    }
}
