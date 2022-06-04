package com.example.finalprojectaozcann.validator;

import com.example.finalprojectaozcann.config.Constants;
import com.example.finalprojectaozcann.exception.BaseValidationException;
import com.example.finalprojectaozcann.exception.ValidationOperationException;
import com.example.finalprojectaozcann.model.request.DebitCardDeptInquiryRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component
public class DebitCardDeptInquiryRequestValidator implements Validator<DebitCardDeptInquiryRequest> {
    @Override
    public void validate(DebitCardDeptInquiryRequest request) throws BaseValidationException {
        if (Objects.isNull(request)) {
            throw new ValidationOperationException.BankCardNotValidException(Constants.ValidationErrorMessage.REQUEST_CAN_NOT_BE_NULL_OR_EMPTY);
        }
        if (!(StringUtils.hasLength(request.debitCardNumber()))) {
            throw new ValidationOperationException.BankCardNotValidException(Constants.ValidationErrorMessage.DEBIT_CARD_NUMBER_CAN_NOT_BE_NULL_OR_EMPTY);
        }
    }
}

