package com.example.finalprojectaozcann.model.request;

import java.math.BigDecimal;

public record TransferCheckingAccountToDebitCardRequest(String senderIban,
                                                        String cardNumber,
                                                        BigDecimal amount,
                                                        String transferDate,
                                                        String description) {
}
