package com.cathay.currency.controller;

import com.cathay.currency.entity.Currency;
import com.cathay.currency.service.SecureCurrencyService;
import com.cathay.currency.util.MessageUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/secure-currencies")
public class SecureCurrencyController {

	private static final Logger log = LoggerFactory.getLogger(SecureCurrencyController.class);
	
    private final SecureCurrencyService secureCurrencyService;
    private final MessageUtil messageUtil;

    public SecureCurrencyController(SecureCurrencyService secureCurrencyService, MessageUtil messageUtil) {
        this.secureCurrencyService = secureCurrencyService;
        this.messageUtil = messageUtil;
    }

    @GetMapping
    public ResponseEntity<List<Currency>> getAll() {
    	log.info("API call: GET/api/secure-currencies");
        return ResponseEntity.ok(secureCurrencyService.getAll());
    }

    @GetMapping("/{code}")
    public ResponseEntity<Currency> getCurrency(@PathVariable String code) {
    	log.info("API call: GET /api/secure-currencies/{}", code);
        // Validate that code is not null or empty	
		if (code == null || code.isEmpty()) {
			throw new IllegalArgumentException(messageUtil.get("currency.not.empty"));
		}
    	
    	return ResponseEntity.ok(secureCurrencyService.get(code));
    }
    
    @PostMapping
    public ResponseEntity<Currency> create(@RequestBody Currency currency) {
		log.info("API call: POST /api/secure-currencies - {}", currency.getCode());
		// Validate that code is not null or empty	
		if (currency.getCode() == null || currency.getCode().isEmpty()) {
			throw new IllegalArgumentException(messageUtil.get("currency.not.empty"));
		}
    	return ResponseEntity.ok(secureCurrencyService.save(currency));
    }

    @PutMapping("/{code}")
    public ResponseEntity<Currency> update(@PathVariable String code, @RequestBody Currency currency) {
    	log.info("API call: PUT /api/secure-currencies/{}", code);
        // Validate that code is not null or empty
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException(messageUtil.get("currency.not.empty"));
        }
        return ResponseEntity.ok(secureCurrencyService.update(code, currency));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
		log.info("API call: DELETE /api/secure-currencies/{}", code);
		// Validate that code is not null or empty
		if (code == null || code.isEmpty()) {
			throw new IllegalArgumentException(messageUtil.get("currency.not.empty"));
		}
    	secureCurrencyService.delete(code);
		return ResponseEntity.noContent().build();
    }


}
