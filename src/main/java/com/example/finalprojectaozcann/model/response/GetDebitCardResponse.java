package com.example.finalprojectaozcann.model.response;

import com.example.finalprojectaozcann.model.enums.CardType;

import java.math.BigDecimal;

public record GetDebitCardResponse(String name,
                                   String surname,
                                   CardType cardType,
                                   String cardNumber,
                                   String expiryDate,
                                   String ccv,
                                   BigDecimal cardLimit,
                                   String firstPassword) {
}
