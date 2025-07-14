package com.cathay.currency.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cathay.currency.entity.Currency;
import com.cathay.currency.repository.CurrencyRepository;
import com.cathay.currency.service.impl.SecureCurrencyServiceImpl;
import com.cathay.currency.util.AESUtil;
import com.cathay.currency.util.MessageUtil;

public class SecureCurrencyServiceTest {
    private final MessageUtil messageUtil = mock(MessageUtil.class);
    private final CurrencyRepository repo = mock(CurrencyRepository.class);

    private final SecureCurrencyServiceImpl service = new SecureCurrencyServiceImpl(repo, messageUtil);

    private Currency plainCurrency;
    private Currency encryptedCurrency;

    @BeforeEach
    void setUp() throws Exception {
        plainCurrency = new Currency(
                "EUR",
                "USD",
                "1.07764",
                "2025/03/27 00:00:00",
                "Euro"
        );

        encryptedCurrency = new Currency(
                "EUR",
                AESUtil.encrypt("USD"),
                AESUtil.encrypt("1.07764"),
                AESUtil.encrypt("2025/03/27 00:00:00"),
                "Euro"
        );
    }

    @Test
    public void testGetAll() {
    	when(repo.findAllByOrderByCodeAsc()).thenReturn(List.of(encryptedCurrency));        
        List<Currency> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals("EUR", result.get(0).getCode());
        assertEquals("USD", result.get(0).getQuote());
        assertEquals("1.07764", result.get(0).getRate());
        assertEquals("2025/03/27 00:00:00", result.get(0).getDate());
        assertEquals("Euro", result.get(0).getName());
    }

    @Test
    void testGetSuccess() {
        when(repo.findById("EUR")).thenReturn(Optional.of(encryptedCurrency));

        Currency result = service.get("EUR");

        assertEquals("EUR", result.getCode());
        assertEquals("USD", result.getQuote());
        assertEquals("1.07764", result.getRate());
        assertEquals("2025/03/27 00:00:00", result.getDate());
        assertEquals("Euro", result.getName());
    }

    @Test
    void testGetNotFound() {
        when(repo.findById("XYZ")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.get("XYZ"));

        assertEquals(messageUtil.get("currency.not.found", "XYZ"), exception.getMessage());
    }   
    
    @Test
    void testSave() throws Exception {
        when(repo.save(any())).thenReturn(encryptedCurrency);

        Currency saved = service.save(plainCurrency);

        assertEquals("EUR", saved.getCode());
        assertEquals("USD", saved.getQuote());
        assertEquals("1.07764", saved.getRate());
        assertEquals("2025/03/27 00:00:00", saved.getDate());
        assertEquals("Euro", saved.getName());

    }

    @Test
    void testUpdateSuccess() throws Exception {
    	
        when(repo.findById("EUR")).thenReturn(Optional.of(encryptedCurrency));
        when(repo.save(any())).thenReturn(encryptedCurrency);

        Currency updated = new Currency("EUR", "USD", "1.07764", "2025/03/27 00:00:00", "Euro Updated");

        Currency result = service.update("EUR", updated);

        assertEquals("EUR", result.getCode());
        assertEquals("USD", result.getQuote());
        assertEquals("1.07764", result.getRate());
        assertEquals("2025/03/27 00:00:00", result.getDate());
        assertEquals("Euro Updated", result.getName());
    }
    
    @Test
    void testUpdateNotFound() {
        when(repo.findById("XYZ")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.update("XYZ", plainCurrency));

        assertEquals(messageUtil.get("currency.not.found", "XYZ"), exception.getMessage());
    }

    @Test
    public void testDelete() {
        service.delete("EUR");
        verify(repo, times(1)).deleteById("EUR");
    }
    
}
