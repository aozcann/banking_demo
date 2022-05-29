package com.example.finalprojectaozcann.exception;

public final class BusinessServiceOperationException {

    private BusinessServiceOperationException() {
    }

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


}