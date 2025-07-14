package com.cathay.currency.service;

import java.util.List;

import com.cathay.currency.entity.Currency;

public interface CurrencyService {
	
    List<Currency> getAll();
    Currency getByCode(String code);
    Currency save(Currency c);
    void delete(String code);
    Currency update(String code, Currency updated);
}
