package com.cathay.currency.controller;

import com.cathay.currency.dto.ExchangeRateDTO;
import com.cathay.currency.service.ExchangeRateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchange-rates")
public class ExchangeRateController {
    private final ExchangeRateService service;

    public ExchangeRateController(ExchangeRateService service) {
        this.service = service;
    }

    @GetMapping
    public ExchangeRateDTO getRates(@RequestParam List<String> base) {
        return service.getExternalExchangeRates(base);
    }
}
