package com.example.finalprojectaozcann.service.impl;

import com.example.finalprojectaozcann.currency.api.CurrencyApi;
import com.example.finalprojectaozcann.currency.api.CurrencyApiResponseModel;
import com.example.finalprojectaozcann.model.enums.Currency;
import com.example.finalprojectaozcann.model.request.TransferToAccountRequest;
import com.example.finalprojectaozcann.model.response.TransferSuccessResponse;
import com.example.finalprojectaozcann.repository.CheckingAccountRepository;
import com.example.finalprojectaozcann.repository.DepositAccountRepository;
import com.example.finalprojectaozcann.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final CheckingAccountRepository checkingAccountRepository;
    private final DepositAccountRepository depositAccountRepository;
    private final CurrencyApi currencyApi;

    @Override
    public TransferSuccessResponse transferCheckingToDeposit(TransferToAccountRequest request, HttpServletRequest httpServletRequest) {


        BigDecimal currency = currencyApi.getCurrencyRate(Currency.TRY.toString(), Currency.EUR.toString());

        return null;
    }
}
