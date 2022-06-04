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

    public static class AmountCanNotBiggerThanBalanceException extends BaseException {
        public AmountCanNotBiggerThanBalanceException(String message) {
            super(message);
        }
    }

    public static class UserCanNotTransferException extends BaseException {
        public UserCanNotTransferException(String message) {
            super(message);
        }
    }

    public static class CardPasswordIsWrongException extends BaseException {
        public CardPasswordIsWrongException(String message) {
            super(message);
        }
    }

    public static class BankCardNotFoundException extends BaseException {
        public BankCardNotFoundException(String message) {
            super(message);
        }
    }

    public static class UserCanNotDeletedException extends BaseException {
        public UserCanNotDeletedException(String message) {
            super(message);
        }

    }

    public static class AccountCanNotDeleteException extends BaseException {
        public AccountCanNotDeleteException(String message) {
            super(message);
        }
    }

    public static class LoggerCanOnlyDeleteOwnAccountException extends BaseException {
        public LoggerCanOnlyDeleteOwnAccountException(String message) {
            super(message);
        }
    }

    public static class CardCcvIsWrongException extends BaseException {
        public CardCcvIsWrongException(String message) {
            super(message);
        }
    }

    public static class CardExpiryDateIsWrongException extends BaseException {
        public CardExpiryDateIsWrongException(String message) {
            super(message);
        }
    }
}


