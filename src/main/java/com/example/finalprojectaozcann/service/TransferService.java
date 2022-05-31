package com.example.finalprojectaozcann.service;

import com.example.finalprojectaozcann.model.request.TransferToAccountRequest;
import com.example.finalprojectaozcann.model.response.TransferSuccessResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface TransferService {
    TransferSuccessResponse transferCheckingToDeposit(TransferToAccountRequest request, HttpServletRequest httpServletRequest);
}
