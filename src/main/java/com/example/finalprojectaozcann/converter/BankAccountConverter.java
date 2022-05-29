package com.example.finalprojectaozcann.converter;

import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.entity.DepositAccount;
import com.example.finalprojectaozcann.model.enums.AccountStatus;
import com.example.finalprojectaozcann.model.enums.AccountType;
import com.example.finalprojectaozcann.model.request.CreateCheckingAccountRequest;
import com.example.finalprojectaozcann.model.request.CreateDepositAccountRequest;
import org.springframework.stereotype.Component;

@Component
public class BankAccountConverter {


    public CheckingAccount toCreateCheckingAccount(String accountNumber, String iban, CreateCheckingAccountRequest request, User user) {

        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountType(AccountType.CHECKING);
        checkingAccount.setAccountNumber(accountNumber);
        checkingAccount.setIban(iban);
        checkingAccount.setBalance(request.balance());
        checkingAccount.setCurrency(request.currency());
        checkingAccount.setAccountStatus(AccountStatus.ACTIVE);
        checkingAccount.setUser(user);

        return checkingAccount;
    }

    public DepositAccount toCreateDepositAccount(String accountNumber, String iban, CreateDepositAccountRequest request, User user) {

        DepositAccount depositAccount = new DepositAccount();
        depositAccount.setAccountType(AccountType.DEPOSIT);
        depositAccount.setAccountNumber(accountNumber);
        depositAccount.setIban(iban);
        depositAccount.setBalance(request.balance());
        depositAccount.setCurrency(request.currency());
        depositAccount.setAccountStatus(AccountStatus.ACTIVE);
        depositAccount.setUser(user);
        depositAccount.setMaturity(request.maturity());
        depositAccount.setInterestRate(request.interestRate());

        return depositAccount;


    }
}
