package com.example.finalprojectaozcann.model.request;

import java.math.BigDecimal;

public record TransferATMToCardRequest(String cardNumber,
                                       String password,
                                       BigDecimal amount
) {
}
