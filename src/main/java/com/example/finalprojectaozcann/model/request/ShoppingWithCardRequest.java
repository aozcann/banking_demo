package com.example.finalprojectaozcann.model.request;

import java.math.BigDecimal;

public record ShoppingWithCardRequest(String cardNumber,
                                      String expiryDate,
                                      String ccv,
                                      String password,
                                      String payeeIBAN,
                                      BigDecimal price) {
}
