package com.example.finalprojectaozcann.converter;

import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import com.example.finalprojectaozcann.model.entity.DepositAccount;
import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.enums.AccountStatus;
import com.example.finalprojectaozcann.model.enums.AccountType;
import com.example.finalprojectaozcann.model.enums.Currency;
import com.example.finalprojectaozcann.model.request.CreateDepositAccountRequest;
import com.example.finalprojectaozcann.utils.UserAccountUtil;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BankAccountConverter {


    public CheckingAccount toCreateCheckingAccount(Currency currency, User user) {

        String accountNumber = UserAccountUtil.createAccountNumber();
        String iban = UserAccountUtil.createIban("TR", accountNumber);

        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountType(AccountType.CHECKING);
        checkingAccount.setAccountNumber(accountNumber);
        checkingAccount.setIban(iban);
        checkingAccount.setCurrency(currency);
        checkingAccount.setAccountStatus(AccountStatus.ACTIVE);
        checkingAccount.setUser(user);
        checkingAccount.setCreatedBy("AhmetOzcan");
        checkingAccount.setCreatedAt(new Date());

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

        return depositAccount;


    }
}
