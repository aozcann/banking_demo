package com.example.finalprojectaozcann.converter;

import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import com.example.finalprojectaozcann.model.entity.DepositAccount;
import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.enums.AccountStatus;
import com.example.finalprojectaozcann.model.enums.AccountType;
import com.example.finalprojectaozcann.model.enums.Currency;
import com.example.finalprojectaozcann.model.request.CreateDepositAccountRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

@Component
public class BankAccountConverter {

    public CheckingAccount toCreateCheckingAccount(Currency currency, User user) {

        String accountNumber = createAccountNumber();
        String iban = createIban("TR", accountNumber);

        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountType(AccountType.CHECKING);
        checkingAccount.setAccountNumber(accountNumber);
        checkingAccount.setIban(iban);
        checkingAccount.setCurrency(currency);
        checkingAccount.setAccountStatus(AccountStatus.ACTIVE);
        checkingAccount.setUser(user);
        checkingAccount.setCreatedBy(user.getId().toString());
        checkingAccount.setCreatedAt(new Date());

        return checkingAccount;
    }

    public DepositAccount toCreateDepositAccount(CreateDepositAccountRequest request, User user) {

        String accountNumber = createAccountNumber();
        String iban = createIban("TR", accountNumber);

        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setAccountType(AccountType.DEPOSIT);
        depositAccount.setAccountNumber(accountNumber);
        depositAccount.setIban(iban);
        depositAccount.setCurrency(request.currency());
        depositAccount.setAccountStatus(AccountStatus.ACTIVE);
        depositAccount.setUser(user);
        depositAccount.setMaturity(request.maturity());
        depositAccount.setCreatedBy(user.getId().toString());
        depositAccount.setCreatedAt(new Date());

        return depositAccount;
    }

    public String createAccountNumber() {
        long accountNumber = 10_000_000_000_000_000L + new Random().nextLong(89_999_999_999_999_999L);
        return Long.toString(accountNumber);
    }

    public String createIban(String countryCode, String accountNumber) {

        String checkNumber = "33";
        String bankCode = "00061";

        return countryCode +
                checkNumber +
                bankCode +
                accountNumber;
    }

}
