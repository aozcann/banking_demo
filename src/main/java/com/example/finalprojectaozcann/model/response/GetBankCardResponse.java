package com.example.finalprojectaozcann.model.response;

import com.example.finalprojectaozcann.model.enums.CardType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GetBankCardResponse(String name,
                                  String surname,
                                  CardType cardType,
                                  String cardNumber,
                                  LocalDate expiryDate,
                                  String ccv,
                                  BigDecimal balance,
                                  String firstPassword) {
}
