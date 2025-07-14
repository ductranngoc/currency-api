package com.cathay.currency.scheduler;

import com.cathay.currency.service.ExchangeRateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExchangeRateScheduler {
    private final ExchangeRateService service;

    public ExchangeRateScheduler(ExchangeRateService service) {
        this.service = service;
    }

    @Scheduled(cron = "0 0 8 * * *") // Every day at 8am
    public void syncRates() {
    	System.out.println("Syncing exchange rates...1111111111111111111111111");
        service.getExternalExchangeRates(List.of("USD", "EUR", "JPY"));
    }
}
