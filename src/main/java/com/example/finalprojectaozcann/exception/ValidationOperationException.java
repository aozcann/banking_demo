package com.example.finalprojectaozcann.exception;

public final class ValidationOperationException {

    private ValidationOperationException() {
    }

    public static class CustomerNotValidException extends BaseException {
        public CustomerNotValidException(String message) {
            super(message);
        }
    }

    public static class CustomerIDNotValidException extends BaseException {
        public CustomerIDNotValidException(String message) {
            super(message);
        }
    }

    public static class BankAccountNotValidException extends BaseException {
        public BankAccountNotValidException(String message) {
            super(message);
        }
    }
}
