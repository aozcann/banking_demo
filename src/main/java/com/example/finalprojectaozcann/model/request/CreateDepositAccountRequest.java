package com.example.finalprojectaozcann.model.request;

import com.example.finalprojectaozcann.model.enums.Currency;
import com.example.finalprojectaozcann.model.enums.Maturity;

import java.math.BigDecimal;

public record CreateDepositAccountRequest(BigDecimal balance,
                                          Currency currency,
                                          Maturity maturity,
                                          Long UserId) {
}
