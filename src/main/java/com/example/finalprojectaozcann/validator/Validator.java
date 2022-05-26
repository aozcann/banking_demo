package com.example.finalprojectaozcann.validator;

import com.example.finalprojectaozcann.exception.BaseValidationException;

public interface Validator<T> {

    void validate(T input) throws BaseValidationException;
}
