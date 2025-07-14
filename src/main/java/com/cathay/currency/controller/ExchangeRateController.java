package com.cathay.currency.controller;

import com.cathay.currency.dto.ExchangeRateDTO;
import com.cathay.currency.service.ExchangeRateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchange-rates")
public class ExchangeRateController {
	
	private static final Logger log = LoggerFactory.getLogger(ExchangeRateController.class);
	
    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping
    public ResponseEntity<ExchangeRateDTO> getRates(@RequestParam List<String> base) {
    	log.info("API call: GET /api/exchange-rates with base currencies: {}", base);
        return ResponseEntity.ok(exchangeRateService.getExternalExchangeRates(base));
    }
}
