package com.example.finalprojectaozcann.model.request;

import java.math.BigDecimal;

public record TransferToAccountRequest(String senderIban,
                                       String receiverIban,
                                       BigDecimal amount,
                                       String transferDate,
                                       String description
) {
}
