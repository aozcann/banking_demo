package com.example.finalprojectaozcann.model.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SuccessATMTransferResponse(String cardNumber,
                                         BigDecimal amount,
                                         LocalDate date) {
}
