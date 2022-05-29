package com.example.finalprojectaozcann.service.impl;

import com.example.finalprojectaozcann.converter.BankAccountConverter;
import com.example.finalprojectaozcann.exception.BusinessServiceOperationException;
import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import com.example.finalprojectaozcann.model.entity.Customer;
import com.example.finalprojectaozcann.model.entity.DepositAccount;
import com.example.finalprojectaozcann.model.enums.CustomerStatus;
import com.example.finalprojectaozcann.model.request.CreateCheckingAccountRequest;
import com.example.finalprojectaozcann.model.request.CreateDepositAccountRequest;
import com.example.finalprojectaozcann.model.response.GetBankAccountResponse;
import com.example.finalprojectaozcann.repository.CheckingAccountRepository;
import com.example.finalprojectaozcann.repository.CustomerRepository;
import com.example.finalprojectaozcann.repository.DepositAccountRepository;
import com.example.finalprojectaozcann.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {


    private final CheckingAccountRepository checkingAccountRepository;
    private final DepositAccountRepository depositAccountRepository;
    private final BankAccountConverter bankAccountConverter;
    private final CustomerRepository customerRepository;

    @Override
    public GetBankAccountResponse createChecking(CreateCheckingAccountRequest request) {

        Customer customer = findByIdAndIsDeletedAndStatus(request.CustomerId());
        String accountNumber = createAccountNumber();
        String iban = createIban("TR", accountNumber);
        CheckingAccount checkingAccount = bankAccountConverter.toCreateCheckingAccount(accountNumber, iban, request, customer);
        checkingAccount.setCreatedBy("AhmetOzcan");
        checkingAccount.setCreatedAt(new Date());
        checkingAccountRepository.save(checkingAccount);

        return new GetBankAccountResponse(checkingAccount.getId());
    }

    @Override
    public GetBankAccountResponse createDeposit(CreateDepositAccountRequest request) {

        Customer customer = findByIdAndIsDeletedAndStatus(request.CustomerId());
        String accountNumber = createAccountNumber();
        String iban = createIban("TR", accountNumber);
        DepositAccount depositAccount = bankAccountConverter.toCreateDepositAccount(accountNumber, iban, request, customer);
        depositAccount.setCreatedBy("AhmetOzcan");
        depositAccount.setCreatedAt(new Date());
        depositAccountRepository.save(depositAccount);

        return new GetBankAccountResponse(depositAccount.getId());
    }


    private String createAccountNumber() {
        Random random = new Random();

        Long accountNumber = 10_000_000_000_000_000L + random.nextLong(89_999_999_999_999_999L);
        return Long.toString(accountNumber);
    }

    private String createIban(String countryCode, String accountNumber) {

        String checkNumber = "33";
        String bankCode = "00061";

        return countryCode +
                checkNumber +
                bankCode +
                accountNumber;
    }

    private Customer findByIdAndIsDeletedAndStatus(Long id) {
        return customerRepository
                .findByIdAndIsDeletedAndStatus(id, false, CustomerStatus.ACTIVE)
                .orElseThrow(() -> new BusinessServiceOperationException.CustomerNotFoundException("Customer not found"));
    }

}
