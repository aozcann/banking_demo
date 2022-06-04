package com.example.finalprojectaozcann.validator;

import com.example.finalprojectaozcann.config.Constants;
import com.example.finalprojectaozcann.exception.BaseValidationException;
import com.example.finalprojectaozcann.exception.ValidationOperationException;
import org.springframework.stereotype.Component;

@Component
public class IdValidator implements Validator<Long> {
    @Override
    public void validate(Long id) throws BaseValidationException {
        if (id < 0) {
            throw new ValidationOperationException.UserIDNotValidException(Constants.ValidationErrorMessage.ID_SHOULD_BE_GREATER_THAN_ZERO);
        }
    }
}
