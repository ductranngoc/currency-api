package com.cathay.currency.service;

import java.util.List;

import com.cathay.currency.dto.ExchangeRateDTO;

public interface ExchangeRateService {
	ExchangeRateDTO getExternalExchangeRates(List<String> baseCurrencies);
}
