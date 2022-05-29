package com.example.finalprojectaozcann.utils;

import java.util.Random;

public class UserAccountUtil {

    public static String createAccountNumber() {
        long accountNumber = 10_000_000_000_000_000L + new Random().nextLong(89_999_999_999_999_999L);
        return Long.toString(accountNumber);
    }

    public static String createIban(String countryCode, String accountNumber) {

        String checkNumber = "33";
        String bankCode = "00061";

        return countryCode +
                checkNumber +
                bankCode +
                accountNumber;
    }
}
