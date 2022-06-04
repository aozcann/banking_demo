package com.example.finalprojectaozcann.service;

import com.example.finalprojectaozcann.model.request.CreateCheckingAccountRequest;
import com.example.finalprojectaozcann.model.request.CreateDepositAccountRequest;
import com.example.finalprojectaozcann.model.response.GetBankAccountResponse;

import javax.servlet.http.HttpServletRequest;

public interface BankAccountService {

    GetBankAccountResponse createChecking(CreateCheckingAccountRequest request, HttpServletRequest httpServletRequest);

    GetBankAccountResponse createDeposit(CreateDepositAccountRequest request, HttpServletRequest httpServletRequest);

    boolean deleteCheckingAccountById(Long id, HttpServletRequest httpServletRequest);

    Object deleteDepositAccountById(Long id, HttpServletRequest httpServletRequest);
}
