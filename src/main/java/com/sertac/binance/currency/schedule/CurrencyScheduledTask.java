package com.sertac.binance.currency.schedule;

import com.sertac.binance.currency.util.CurrencyUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Currency;

@Component
public class CurrencyScheduledTask {
    @Scheduled(fixedRate = 60000) // Run every 1 minute (60,000 milliseconds)
    public void updateCurrencies() {
        CurrencyUtil.updateCurrencyRate(Currency.getInstance("USD"),Currency.getInstance("TRY"));
        System.out.println("updateCurrencies Scheduled task executed!");
    }
}
