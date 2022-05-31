package com.example.finalprojectaozcann.model.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransferSuccessResponse(String receiverIban,
                                      BigDecimal transactionAmount,
                                      LocalDate date,
                                      String receiverName,
                                      String senderName) {
}
