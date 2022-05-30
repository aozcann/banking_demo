package com.example.finalprojectaozcann.service.impl;

import com.example.finalprojectaozcann.converter.BankAccountConverter;
import com.example.finalprojectaozcann.exception.BusinessServiceOperationException;
import com.example.finalprojectaozcann.model.entity.CheckingAccount;
import com.example.finalprojectaozcann.model.entity.DepositAccount;
import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.request.CreateCheckingAccountRequest;
import com.example.finalprojectaozcann.model.request.CreateDepositAccountRequest;
import com.example.finalprojectaozcann.model.response.GetBankAccountResponse;
import com.example.finalprojectaozcann.repository.CheckingAccountRepository;
import com.example.finalprojectaozcann.repository.DepositAccountRepository;
import com.example.finalprojectaozcann.repository.UserRepository;
import com.example.finalprojectaozcann.security.CustomJWTAuthenticationFilter;
import com.example.finalprojectaozcann.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {


    private final CheckingAccountRepository checkingAccountRepository;
    private final DepositAccountRepository depositAccountRepository;
    private final BankAccountConverter bankAccountConverter;
    private final UserRepository userRepository;
    private final CustomJWTAuthenticationFilter customJWTAuthenticationFilter;

    @Override
    public GetBankAccountResponse createChecking(CreateCheckingAccountRequest request, HttpServletRequest httpServletRequest) {

        User user = findByIdAndIsDeletedAndStatus(httpServletRequest);
        CheckingAccount checkingAccount = bankAccountConverter.toCreateCheckingAccount(request.currency(), user);
        checkingAccountRepository.save(checkingAccount);
        log.info("Checking account created by id -> {}", checkingAccount.getId());
        return new GetBankAccountResponse(checkingAccount.getId());
    }

    @Override
    public GetBankAccountResponse createDeposit(CreateDepositAccountRequest request, HttpServletRequest httpServletRequest) {

        User user = findByIdAndIsDeletedAndStatus(httpServletRequest);
        DepositAccount depositAccount = bankAccountConverter.toCreateDepositAccount(request, user);
        depositAccountRepository.save(depositAccount);
        log.info("Deposit account created by id -> {}", depositAccount.getId());
        return new GetBankAccountResponse(depositAccount.getId());
    }


    private User findByIdAndIsDeletedAndStatus(HttpServletRequest httpServletRequest) {

        Long userId = customJWTAuthenticationFilter
                .findUserId(customJWTAuthenticationFilter.findToken(httpServletRequest));
        return userRepository
                .findByIdAndIsDeleted(userId, false)
                .orElseThrow(() -> new BusinessServiceOperationException.UserNotFoundException("User not found"));
    }

}
