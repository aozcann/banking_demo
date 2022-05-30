package com.example.finalprojectaozcann.model.request;

import com.example.finalprojectaozcann.model.enums.Currency;
import com.example.finalprojectaozcann.model.enums.Maturity;

public record CreateDepositAccountRequest(Currency currency,
                                          Maturity maturity
) {
}
