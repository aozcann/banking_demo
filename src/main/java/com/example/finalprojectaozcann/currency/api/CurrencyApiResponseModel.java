package com.example.finalprojectaozcann.currency.api;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;

@Getter
@Setter
public class CurrencyApiResponseModel {

    private String base;
    private HashMap<String, BigDecimal> rates = new HashMap<>();

}
