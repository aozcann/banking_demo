package com.example.finalprojectaozcann.validator;

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
            throw new ValidationOperationException.BankCardNotValidException("Request can not be null or empty");
        }
        if (!(StringUtils.hasLength(request.debitCardNumber()))) {
            throw new ValidationOperationException.BankCardNotValidException("Debit card number can not be null or empty");
        }
    }
}

