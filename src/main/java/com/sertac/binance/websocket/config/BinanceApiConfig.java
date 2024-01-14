package com.sertac.binance.websocket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BinanceApiConfig {

    @Value("${binance.api.key}")
    private String apiKey;

    @Value("${binance.api.secret}")
    private String apiSecret;

    @Value("${binance.api.baseWsUrl}")
    private String baseWsUrl;

    public String getBaseWsUrl() {
        return baseWsUrl;
    }

    public void setBaseWsUrl(String baseWsUrl) {
        this.baseWsUrl = baseWsUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }


}