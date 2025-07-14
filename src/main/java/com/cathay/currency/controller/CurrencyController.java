package com.cathay.currency.controller;

import com.cathay.currency.entity.Currency;
import com.cathay.currency.service.CurrencyService;
import com.cathay.currency.util.MessageUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {
	
	private static final Logger log = LoggerFactory.getLogger(CurrencyController.class);
	
    private final CurrencyService currencyService;
    private final MessageUtil messageUtil;

    public CurrencyController(CurrencyService currencyService, MessageUtil messageUtil) {
        this.currencyService = currencyService;
        this.messageUtil = messageUtil;
    }

    @GetMapping
    public ResponseEntity<List<Currency>> getAll() {
        log.info("API call: GET /api/currencies");
        List<Currency> currencies = currencyService.getAll();
        return ResponseEntity.ok(currencies);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Currency> getCurrency(@PathVariable String code) {
        log.info("API call: GET /api/currencies/{}", code);
        // Validate that code is not null or empty
		if (code == null || code.isEmpty()) {
			throw new IllegalArgumentException(messageUtil.get("currency.not.empty"));
		}
        return ResponseEntity.ok(currencyService.getByCode(code));
    }

    @PostMapping
    public ResponseEntity<Currency> createCurrency(@RequestBody Currency currency) {
        log.info("API call: POST /api/currencies - {}", currency.getCode());
        // Validate that code is not null or empty
		if (currency.getCode() == null || currency.getCode().isEmpty()) {
			throw new IllegalArgumentException(messageUtil.get("currency.not.empty"));
		}
        return ResponseEntity.ok(currencyService.save(currency));
    }

    @PutMapping("/{code}")
    public ResponseEntity<Currency> updateCurrency(@PathVariable String code, @RequestBody Currency updated) {
        log.info("API call: PUT /api/currencies/{}", code);
        // Validate that code is not null or empty
		if (code == null || code.isEmpty()) {
			throw new IllegalArgumentException(messageUtil.get("currency.not.empty"));
		}
        return ResponseEntity.ok(currencyService.update(code, updated));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable String code) {
        log.info("API call: DELETE /api/currencies/{}", code);
        // Validate that code is not null or empty
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException(messageUtil.get("currency.not.empty"));
        }
        currencyService.delete(code);
        return ResponseEntity.noContent().build();
    }
}
