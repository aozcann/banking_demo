package com.example.finalprojectaozcann.exception;

import com.example.finalprojectaozcann.config.Constants;
import com.example.finalprojectaozcann.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ErrorResponse> handleAccessDeniedException() {
        ErrorResponse response = new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                Constants.ErrorMessage.ACCESS_DENIED);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleAuthenticationCredentialsNotFoundException() {
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Constants.ErrorMessage.AN_AUTHENTICATION_OBJECT_WAS_NOT_FOUND_IN_THE_SECURITY_CONTEXT);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(BaseException.class)
    public final ResponseEntity<ErrorResponse> businessServiceOperationException(BaseException message) {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                message.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public final ResponseEntity<ErrorResponse> handleDateTimeParseException() {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                Constants.ErrorMessage.BIRTH_DAY_FORMAT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}