package com.example.finalprojectaozcann.model.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SuccessAccountTransferResponse(String receiverIban,
                                             BigDecimal transactionAmount,
                                             LocalDate date,
                                             String receiverName,
                                             String senderName,
                                             BigDecimal currencyRate,
                                             String message) {
}
