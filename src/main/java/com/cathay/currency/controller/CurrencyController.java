package com.cathay.currency.controller;

import com.cathay.currency.entity.Currency;
import com.cathay.currency.service.CurrencyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {
    private final CurrencyService service;

    public CurrencyController(CurrencyService service) {
        this.service = service;
    }

    @GetMapping
    public List<Currency> list() {
        return service.getAll();
    }

    @PostMapping
    public Currency create(@RequestBody Currency c) {
        return service.save(c);
    }

    @PutMapping("/{code}")
    public Currency update(@PathVariable String code, @RequestBody Currency c) {
        return service.update(code, c);
    }

    @DeleteMapping("/{code}")
    public void delete(@PathVariable String code) {
        service.delete(code);
    }
}
