package com.example.finalprojectaozcann.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public final class BusinessServiceOperationException {

    private BusinessServiceOperationException() {
    }

    public static class BankCardAlreadyExist extends BaseException {
        public BankCardAlreadyExist(String message) {
            super(message);
        }
    }

//    @ExceptionHandler(BusinessServiceOperationException.BankCardAlreadyExist.class)
//    public final ResponseEntity<?> BusinessServiceOperationException() {
//        return new ResponseEntity<>("deneme", HttpStatus.FORBIDDEN);
//    }

    public static class UserNotFoundException extends BaseException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class UserAlreadyDeletedException extends BaseException {
        public UserAlreadyDeletedException(String message) {
            super(message);
        }
    }

    public static class AdminAlreadyGeneratedException extends BaseException {
        public AdminAlreadyGeneratedException(String message) {
            super(message);
        }
    }

    public static class AccountNotFoundException extends BaseException {
        public AccountNotFoundException(String message) {
            super(message);
        }
    }

    public static class DebitCardNotFoundException extends BaseException {
        public DebitCardNotFoundException(String message) {
            super(message);
        }

    }
}