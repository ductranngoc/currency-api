package com.cathay.currency.scheduler;

import com.cathay.currency.service.ExchangeRateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExchangeRateScheduler {
	
	private static final Logger log = LoggerFactory.getLogger(ExchangeRateScheduler.class);
	 
    private final ExchangeRateService service;

    public ExchangeRateScheduler(ExchangeRateService service) {
        this.service = service;
    }

    @Scheduled(cron = "0 0 8 * * *") // Every day at 8am
    public void syncRates() {
    	log.info("Scheduled task to sync exchange rates started");
        service.getExternalExchangeRates(List.of("USD", "EUR", "JPY"));
    }
}
