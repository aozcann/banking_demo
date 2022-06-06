package com.example.finalprojectaozcann.config;

public final class Constants {

    public static class ErrorMessage {
        public static final String ACCESS_DENIED = "Access denied";
        public static final String USER_NOT_FOUND = "User not found";
        public static final String USER_ALREADY_DELETED = "User already deleted";
        public static final String ADMIN_ALREADY_GENERATED = "Admin already generated.";
        public static final String ONE_CHECKING_ACCOUNT_CAN_ONLY_HAVE_A_BANK_CARD = "One checking account can only have a bank card";
        public static final String DEBIT_CARD_NOT_FOUND = "Debit card not found";
        public static final String BANK_CARD_NOT_FOUND = "Bank card not found";
        public static final String ACCOUNT_NOT_FOUND = "ACCOUNT not found";
        public static final String CHECKING_ACCOUNT_NOT_FOUND = "Checking account not found";
        public static final String DEPOSIT_ACCOUNT_NOT_FOUND = "Deposit account not found";
        public static final String CARD_PASSWORD_IS_WRONG = "card password is wrong";
        public static final String DEPOSIT_ACCOUNT_CAN_ONLY_BE_TRANSFERRED_TO_THE_SAME_ACCOUNT_OWNED_BY_THE_USER = "Deposit account can only be transferred to the same account owned by the user";
        public static final String USER_ONLY_CAN_BE_SENT_TRANSFER_WITH_OWN_ACCOUNT = "User only can be sent transfer with own account";
        public static final String AMOUNT_CAN_NOT_BE_BIGGER_THAN_SENDER_ACCOUNT_BALANCE = "Amount can not be bigger than sender account balance";
        public static final String USER_CAN_NOT_DELETE_CHECK_CHECKING_ACCOUNT_BALANCE = "User can not delete check checking account balance";
        public static final String USER_CAN_NOT_DELETE_CHECK_DEPOSIT_ACCOUNT_BALANCE = "User can not delete check deposit account balance";
        public static final String USER_CAN_NOT_DELETE_CHECK_DEBIT_CARD_DEBT = "User can not delete check debit card debt";
        public static final String ACCOUNT_CAN_NOT_DELETE_CHECK_ACCOUNT_BALANCE = "Account can not delete check account balance";
        public static final String LOGGER_CAN_ONLY_DELETE_OWN_ACCOUNT = "Logger can only delete own account";
        public static final String CARD_CCV_IS_WRONG = "Card ccv is wrong";
        public static final String CARD_EXPIRY_DATE_IS_WRONG = "Card expiry date is wrong";
        public static final String AN_AUTHENTICATION_OBJECT_WAS_NOT_FOUND_IN_THE_SECURITY_CONTEXT = "An Authentication object was not found in the SecurityContext";
        public static final String USER_CAN_ONLY_QUERY_WITH_OWN_CARD = "User can only query with own card.";
        public static final String BIRTH_DAY_FORMAT = "birth day format: 'dd/MM/yyyy' ";
    }

    public static class Message {
        public static final String SCHEDULED_TRANSFER = "Scheduled transfer on the list successfully. If you have money in your account, your transaction will be processed.";
    }

    public static class ValidationErrorMessage {
        public static final String REQUEST_CAN_NOT_BE_NULL_OR_EMPTY = "Request can not be null or empty";
        public static final String ACCOUNT_NUMBER_CAN_NOT_BE_NULL_OR_EMPTY = "Account number can not be null or empty";
        public static final String ACCOUNT_CURRENCY_CAN_NOT_BE_NULL_OR_EMPTY = "Account currency can not be null or empty";
        public static final String MATURITY_CAN_NOT_BE_NULL_OR_EMPTY = "Maturity can not be null or empty";
        public static final String USER_CAN_NOT_BE_NULL_OR_EMPTY = "User can not be null or empty";
        public static final String USER_NAME_CAN_NOT_BE_NULL_OR_EMPTY = "User name can not be null or empty";
        public static final String USER_SURNAME_CAN_NOT_BE_NULL_OR_EMPTY = "User surname can not be null or empty";
        public static final String USER_IDENTITY_NUMBER_CAN_NOT_BE_NULL_OR_EMPTY = "User identity number can not be null or empty";
        public static final String USER_BIRTHDAY_CAN_NOT_BE_NULL_OR_EMPTY = "User birthday can not be null or empty";
        public static final String USER_PHONE_NUMBER_CAN_NOT_BE_NULL_OR_EMPTY = "User phone number can not be null or empty";
        public static final String USER_ADDRESS_CAN_NOT_BE_NULL_OR_EMPTY = "User address can not be null or empty";
        public static final String USER_EMAIL_CAN_NOT_BE_NULL_OR_EMPTY = "User email can not be null or empty";
        public static final String USER_PASSWORD_CAN_NOT_BE_NULL_OR_EMPTY = "User password can not be null or empty";
        public static final String USER_ROLES_CAN_NOT_BE_NULL_OR_EMPTY = "User roles can not be null or empty";
        public static final String DEBIT_CARD_NUMBER_CAN_NOT_BE_NULL_OR_EMPTY = "Debit card number can not be null or empty";
        public static final String CARD_NUMBER_CAN_NOT_BE_NULL_OR_EMPTY = "Card number can not be null or empty";
        public static final String EXPIRY_DATE_CAN_NOT_BE_NULL_OR_EMPTY = "Expiry date can not be null or empty";
        public static final String CCV_CAN_NOT_BE_NULL_OR_EMPTY = "CCV can not be null or empty";
        public static final String CARD_PASSWORD_CAN_NOT_BE_NULL_OR_EMPTY = "Card password can not be null or empty";
        public static final String SENDER_IBAN_CAN_NOT_BE_NULL_OR_EMPTY = "Sender iban can not be null or empty";
        public static final String RECEIVER_IBAN_CAN_NOT_BE_NULL_OR_EMPTY = "Receiver iban can not be null or empty";
        public static final String TRANSFER_DATE_CAN_NOT_BE_NULL_OR_EMPTY = "Transfer date can not be null or empty";
        public static final String AMOUNT_CAN_NOT_BE_LESS_THAN_ZERO = "Amount can not be less than zero";
        public static final String PRICE_CAN_NOT_BE_LESS_THAN_ZERO = "Price can not be less than zero";
        public static final String IBAN_CAN_NOT_BE_NULL_OR_EMPTY = "IBAN can not be null or empty";
        public static final String TRANSFER_DATE_CAN_NOT_BE_PAST_DAY = "Transfer date can not be past day";
        public static final String ID_SHOULD_BE_GREATER_THAN_ZERO = "ID should be greater than zero.";
    }

    public static class CurrencyApi {
        public static final String API_KEY_TEXT = "apikey";
        public static final String API_KEY = "DuwdZMlFCvCNpFzHrIzjVUVXeF6CEfgZ";
        public static final String LOCATION_URL = "https://api.apilayer.com/exchangerates_data/latest";
        public static final String SYMBOLS = "symbols";
        public static final String BASE = "base";
    }

    public static class SecurityErrorMessage {
        public static final String TOKEN_CAN_NOT_BE_NULL_OR_EMPTY = "Token can not be null or empty";
    }

    public static class Security {
        public static final String BEARER = "Bearer ";
        public static final String IDENTITY_NUMBER = "identityNumber";
        public static final String USER_ID = "userId";

    }

}
