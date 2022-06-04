package com.example.finalprojectaozcann.validator;

import com.example.finalprojectaozcann.config.Constants;
import com.example.finalprojectaozcann.exception.BaseValidationException;
import com.example.finalprojectaozcann.exception.ValidationOperationException;
import com.example.finalprojectaozcann.utils.DateUtil;
import org.springframework.stereotype.Component;

@Component
public class DateRequestValidator implements Validator<String> {
    @Override
    public void validate(String date) throws BaseValidationException {
        if (DateUtil.checkTransferDate(date) < 0) {
            throw new ValidationOperationException.TransferDateValidException(Constants.ValidationErrorMessage.TRANSFER_DATE_CAN_NOT_BE_PAST_DAY);
        }
    }
}
