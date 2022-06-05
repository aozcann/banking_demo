package com.example.finalprojectaozcann.service;

import com.example.finalprojectaozcann.model.request.CardExtractRequest;
import com.example.finalprojectaozcann.model.request.CreateCardRequest;
import com.example.finalprojectaozcann.model.request.DebitCardDeptInquiryRequest;
import com.example.finalprojectaozcann.model.response.GetBankCardResponse;
import com.example.finalprojectaozcann.model.response.GetCardExtractResponse;
import com.example.finalprojectaozcann.model.response.GetDebitCardDeptInquiryResponse;
import com.example.finalprojectaozcann.model.response.GetDebitCardResponse;

import javax.servlet.http.HttpServletRequest;


public interface CardService {

    GetBankCardResponse createBankCard(CreateCardRequest request, HttpServletRequest httpServletRequest);

    GetDebitCardResponse createDebitCard(CreateCardRequest request, HttpServletRequest httpServletRequest);

    GetDebitCardDeptInquiryResponse getInquiryDebitCard(DebitCardDeptInquiryRequest request, HttpServletRequest httpServletRequest);

    GetCardExtractResponse getExtractOfDebitCard(CardExtractRequest request, HttpServletRequest httpServletRequest);

    GetCardExtractResponse getExtractOfBankCard(CardExtractRequest request, HttpServletRequest httpServletRequest);
}
