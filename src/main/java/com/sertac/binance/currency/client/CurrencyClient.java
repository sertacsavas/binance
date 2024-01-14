package com.sertac.binance.currency.client;

import com.sertac.binance.currency.model.CurrencyResult;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Currency;

public class CurrencyClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_BASE_URL = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/";

    public CurrencyResult getCurrencyResult(Currency firstCurrency, Currency secondCurrency) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<CurrencyResult> response =
                restTemplate.exchange(prepareUrl(firstCurrency, secondCurrency),
                        HttpMethod.GET, entity, CurrencyResult.class);

        return response.getBody();
    }

    private String prepareUrl(Currency firstCurrency, Currency secondCurrency) {
        StringBuilder url = new StringBuilder();
        url.append(API_BASE_URL);
        url.append(firstCurrency.getCurrencyCode().toLowerCase());
        url.append("/");
        url.append(secondCurrency.getCurrencyCode().toLowerCase());
        url.append(".json");

        return url.toString();
    }
}
