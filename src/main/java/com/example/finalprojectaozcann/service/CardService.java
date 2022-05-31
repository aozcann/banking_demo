package com.example.finalprojectaozcann.service;

import com.example.finalprojectaozcann.model.request.CreateCardRequest;
import com.example.finalprojectaozcann.model.request.DebitCardDeptInquiryRequest;
import com.example.finalprojectaozcann.model.response.GetBankCardResponse;
import com.example.finalprojectaozcann.model.response.GetDebitCardResponse;
import com.example.finalprojectaozcann.model.response.GetDebitCardDeptInquiryResponse;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


public interface CardService {

    GetBankCardResponse createBankCard(CreateCardRequest request, HttpServletRequest httpServletRequest);

    GetDebitCardResponse createDebitCard(CreateCardRequest request, HttpServletRequest httpServletRequest);

    GetDebitCardDeptInquiryResponse getInquiryDebitCard(DebitCardDeptInquiryRequest request, HttpServletRequest httpServletRequest);
}
