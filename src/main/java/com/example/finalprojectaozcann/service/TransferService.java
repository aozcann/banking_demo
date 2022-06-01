package com.example.finalprojectaozcann.service;

import com.example.finalprojectaozcann.model.request.TransferCheckingAccountToDebitCardRequest;
import com.example.finalprojectaozcann.model.request.TransferToAccountRequest;
import com.example.finalprojectaozcann.model.response.SuccessAccountTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessCardTransferResponse;

import javax.servlet.http.HttpServletRequest;

public interface TransferService {
    SuccessAccountTransferResponse transferCheckingToDeposit(TransferToAccountRequest request, HttpServletRequest httpServletRequest);

    SuccessAccountTransferResponse transferDepositToChecking(TransferToAccountRequest request, HttpServletRequest httpServletRequest);

    SuccessAccountTransferResponse transferCheckingToChecking(TransferToAccountRequest request, HttpServletRequest httpServletRequest);

    SuccessCardTransferResponse transferCheckingToDebitCard(TransferCheckingAccountToDebitCardRequest request, HttpServletRequest httpServletRequest);
}
