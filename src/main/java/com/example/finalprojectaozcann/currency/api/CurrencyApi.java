package com.example.finalprojectaozcann.currency.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class CurrencyApi {

    private final RestTemplate restTemplate;

    public BigDecimal getCurrencyRate(String base, String symbol) {

        String locationUrl = "https://api.apilayer.com/exchangerates_data/latest";

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(locationUrl)
                .queryParam("symbols", symbol)
                .queryParam("base", base)
                .build();

        HttpEntity<String> requestEntity = new HttpEntity<>(null, getHeaders().getHeaders());

        ResponseEntity<CurrencyApiResponseModel> currencyResponse = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                CurrencyApiResponseModel.class,
                new HashMap<>());

        return currencyResponse.getBody().getRates().get(symbol);

    }

    private HttpEntity<?> getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add("apikey", "DuwdZMlFCvCNpFzHrIzjVUVXeF6CEfgZ");
        return new HttpEntity<>(headers);
    }


}