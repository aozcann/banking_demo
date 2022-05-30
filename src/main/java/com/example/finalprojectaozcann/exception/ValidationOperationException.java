package com.example.finalprojectaozcann.exception;

public final class ValidationOperationException {

    private ValidationOperationException() {
    }

    public static class UserNotValidException extends BaseException {
        public UserNotValidException(String message) {
            super(message);
        }
    }

    public static class UserIDNotValidException extends BaseException {
        public UserIDNotValidException(String message) {
            super(message);
        }
    }

    public static class BankAccountNotValidException extends BaseException {
        public BankAccountNotValidException(String message) {
            super(message);
        }
    }

    public static class BankCardNotValidException extends BaseException {
        public BankCardNotValidException(String message) {
            super(message);
        }
    }
}
