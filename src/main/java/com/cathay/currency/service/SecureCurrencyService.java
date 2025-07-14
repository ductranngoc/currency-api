package com.cathay.currency.service;

import java.util.List;

import com.cathay.currency.entity.Currency;

public interface SecureCurrencyService {
    List<Currency> getAll();
    Currency save(Currency c);
    void delete(String code);
    Currency update(String code, Currency updated);
    Currency get(String code);
}
