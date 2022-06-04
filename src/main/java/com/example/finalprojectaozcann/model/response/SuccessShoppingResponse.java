package com.example.finalprojectaozcann.model.response;

public record SuccessShoppingResponse(String carNumber,
                                      String buyerName,
                                      String sellerName,
                                      String price,
                                      String date) {
}
