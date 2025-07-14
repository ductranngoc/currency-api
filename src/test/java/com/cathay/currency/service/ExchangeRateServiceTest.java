package com.cathay.currency.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.cathay.currency.dto.ExchangeRateDTO;
import com.cathay.currency.repository.CurrencyRepository;
import com.cathay.currency.service.impl.ExchangeRateServiceImpl;

public class ExchangeRateServiceTest {

    private final CurrencyRepository repo = mock(CurrencyRepository.class);
    private final ExchangeRateService service = new ExchangeRateServiceImpl(repo);

    @Test
    public void testGetExchangeRates() {
        ExchangeRateDTO dto = service.getExternalExchangeRates(List.of("USD"));
        assertNotNull(dto.getUpdateTime());
        assertFalse(dto.getRates().isEmpty());
        assertEquals("USD", dto.getRates().get(0).getBaseCurrency());
    }
}
