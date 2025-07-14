package com.cathay.currency.controller;

import com.cathay.currency.entity.Currency;
import com.cathay.currency.service.SecureCurrencyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/secure-currencies")
public class SecureCurrencyController {

    private final SecureCurrencyService service;

    public SecureCurrencyController(SecureCurrencyService service) {
        this.service = service;
    }

    @GetMapping
    public List<Currency> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Currency create(@RequestBody Currency currency) {
        return service.save(currency);
    }

    @PutMapping("/{code}")
    public Currency update(@PathVariable String code, @RequestBody Currency currency) {
        return service.update(code, currency);
    }

    @DeleteMapping("/{code}")
    public void delete(@PathVariable String code) {
        service.delete(code);
    }

    @GetMapping("/{code}")
    public Currency get(@PathVariable String code) {
        return service.get(code);
    }
}
