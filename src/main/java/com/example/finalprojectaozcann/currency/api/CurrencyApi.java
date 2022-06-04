package com.example.finalprojectaozcann.currency.api;

import com.example.finalprojectaozcann.config.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CurrencyApi {

    private final RestTemplate restTemplate;

    public BigDecimal getCurrencyRate(String base, String symbol) {

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(Constants.CurrencyApi.LOCATION_URL)
                .queryParam(Constants.CurrencyApi.SYMBOLS, symbol)
                .queryParam(Constants.CurrencyApi.BASE, base)
                .build();

        HttpEntity<String> requestEntity = new HttpEntity<>(null, getHeaders().getHeaders());

        ResponseEntity<CurrencyApiResponseModel> currencyResponse = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                CurrencyApiResponseModel.class,
                new HashMap<>());

        return Objects.requireNonNull(currencyResponse.getBody()).getRates().get(symbol);
    }

    private HttpEntity<?> getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(Constants.CurrencyApi.API_KEY_TEXT, Constants.CurrencyApi.API_KEY);
        return new HttpEntity<>(headers);
    }


}