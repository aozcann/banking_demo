package com.example.finalprojectaozcann.service.impl;

import com.example.finalprojectaozcann.converter.BankAccountConverter;
import com.example.finalprojectaozcann.exception.BusinessServiceOperationException;
import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.entity.DepositAccount;
import com.example.finalprojectaozcann.model.enums.UserStatus;
import com.example.finalprojectaozcann.model.request.CreateCheckingAccountRequest;
import com.example.finalprojectaozcann.model.request.CreateDepositAccountRequest;
import com.example.finalprojectaozcann.model.response.GetBankAccountResponse;
import com.example.finalprojectaozcann.repository.CheckingAccountRepository;
import com.example.finalprojectaozcann.repository.UserRepository;
import com.example.finalprojectaozcann.repository.DepositAccountRepository;
import com.example.finalprojectaozcann.service.BankAccountService;
import com.example.finalprojectaozcann.utils.UserAccountUtil;
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
    private final UserRepository userRepository;

    @Override
    public GetBankAccountResponse createChecking(CreateCheckingAccountRequest request) {

        //TODO?? user null
        CheckingAccount checkingAccount = bankAccountConverter.toCreateCheckingAccount(request.currency(), null );
        checkingAccountRepository.save(checkingAccount);

        return new GetBankAccountResponse(checkingAccount.getId());
    }

    @Override
    public GetBankAccountResponse createDeposit(CreateDepositAccountRequest request) {

        User user = findByIdAndIsDeletedAndStatus(request.UserId());
        String accountNumber = UserAccountUtil.createAccountNumber();
        String iban = UserAccountUtil.createIban("TR", accountNumber);
        DepositAccount depositAccount = bankAccountConverter.toCreateDepositAccount(accountNumber, iban, request, user);
        depositAccount.setCreatedBy("AhmetOzcan");
        depositAccount.setCreatedAt(new Date());
        depositAccountRepository.save(depositAccount);

        return new GetBankAccountResponse(depositAccount.getId());
    }




    private User findByIdAndIsDeletedAndStatus(Long id) {
        return userRepository
                .findByIdAndIsDeletedAndStatus(id, false, UserStatus.ACTIVE)
                .orElseThrow(() -> new BusinessServiceOperationException.UserNotFoundException("User not found"));
    }

}
