package com.sertac.binance.currency.util;

import com.sertac.binance.currency.client.CurrencyClient;
import com.sertac.binance.currency.model.CurrencyResult;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class CurrencyUtil {


    private static Map<String,BigDecimal> currencyRateMap = new HashMap<>();
    public static void updateCurrencyRate(Currency firstCurrency, Currency secondCurrency){
        CurrencyClient currencyClient = new CurrencyClient();
        CurrencyResult currencyResult = currencyClient.getCurrencyResult(firstCurrency,secondCurrency);

        currencyRateMap.put(getSymbol(firstCurrency, secondCurrency),currencyResult.getRate());
        System.out.println("updateCurrencyRate executed!");
    }

    private static String getSymbol(Currency firstCurrency, Currency secondCurrency){
        String symbol = firstCurrency.getCurrencyCode().toLowerCase()+"/"+secondCurrency.getCurrencyCode().toLowerCase();
        return symbol;
    }

    public static BigDecimal getCurrencyRate(Currency firstCurrency, Currency secondCurrency){
        return currencyRateMap.get(getSymbol(firstCurrency, secondCurrency));
    }
}
