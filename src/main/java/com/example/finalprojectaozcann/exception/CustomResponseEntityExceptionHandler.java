package com.example.finalprojectaozcann.exception;

import com.example.finalprojectaozcann.config.Constants;
import com.example.finalprojectaozcann.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ErrorResponse> handleAccessDeniedException() {
        ErrorResponse response = new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                Constants.ErrorMessage.ACCESS_DENIED);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(BaseException.class)
    public final ResponseEntity<ErrorResponse> BusinessServiceOperationException(BaseException message) {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                message.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}