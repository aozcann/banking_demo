package com.example.finalprojectaozcann.model.request;

import com.example.finalprojectaozcann.model.enums.Currency;

import java.math.BigDecimal;

public record CreateCheckingAccountRequest(BigDecimal balance,
                                           Currency currency,
                                           Long CustomerId) {
}
