package com.example.finalprojectaozcann.service;

import com.example.finalprojectaozcann.model.request.CreateCheckingAccountRequest;
import com.example.finalprojectaozcann.model.request.CreateDepositAccountRequest;
import com.example.finalprojectaozcann.model.response.GetBankAccountResponse;

public interface BankAccountService {


    GetBankAccountResponse createChecking(CreateCheckingAccountRequest request);

    GetBankAccountResponse createDeposit(CreateDepositAccountRequest request);
}
