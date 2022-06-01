package com.example.finalprojectaozcann.model.response;

import java.math.BigDecimal;

public record GetDebitCardDeptInquiryResponse(String cardNumber,
                                              BigDecimal cardLimit,
                                              BigDecimal totalDept,
                                              BigDecimal expendableAmount) {
}
