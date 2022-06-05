package com.example.finalprojectaozcann.service;

import com.example.finalprojectaozcann.model.request.ShoppingWithCardRequest;
import com.example.finalprojectaozcann.model.request.TransferATMToCardRequest;
import com.example.finalprojectaozcann.model.request.TransferCheckingAccountToDebitCardRequest;
import com.example.finalprojectaozcann.model.request.TransferToAccountRequest;
import com.example.finalprojectaozcann.model.response.SuccessATMTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessAccountTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessCardTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessShoppingResponse;

import javax.servlet.http.HttpServletRequest;

public interface TransferService {
    SuccessAccountTransferResponse transferCheckingToDeposit(TransferToAccountRequest request, HttpServletRequest httpServletRequest);

    SuccessAccountTransferResponse transferDepositToChecking(TransferToAccountRequest request, HttpServletRequest httpServletRequest);

    SuccessAccountTransferResponse transferCheckingToChecking(TransferToAccountRequest request, HttpServletRequest httpServletRequest);

    SuccessCardTransferResponse transferCheckingToDebitCard(TransferCheckingAccountToDebitCardRequest request, HttpServletRequest httpServletRequest);

    SuccessATMTransferResponse transferATMToDebitCard(TransferATMToCardRequest request, HttpServletRequest httpServletRequest);

    SuccessATMTransferResponse transferATMToBankCard(TransferATMToCardRequest request, HttpServletRequest httpServletRequest);

    SuccessShoppingResponse shoppingWithDebitCard(ShoppingWithCardRequest request, HttpServletRequest httpServletRequest);

    SuccessShoppingResponse shoppingWithBankCard(ShoppingWithCardRequest request, HttpServletRequest httpServletRequest);
}
