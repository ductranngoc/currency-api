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

import org.junit.jupiter.api.Test;

import com.cathay.currency.entity.Currency;
import com.cathay.currency.repository.CurrencyRepository;
import com.cathay.currency.service.impl.CurrencyServiceImpl;
import com.cathay.currency.util.MessageUtil;

public class CurrencyServiceTest {
    private final MessageUtil messageUtil = mock(MessageUtil.class);
    private final CurrencyRepository repo = mock(CurrencyRepository.class);

    private final CurrencyService service = new CurrencyServiceImpl(repo, messageUtil);

    @Test
    public void testGetAll() {
        when(repo.findAllByOrderByCodeAsc()).thenReturn(List.of(new Currency("USD", "US Dollar")));
        List<Currency> result = service.getAll();
        assertEquals(1, result.size());
        assertEquals("USD", result.get(0).getCode());
    }

    @Test
    public void testSave() {
        Currency currency = new Currency("JPY", "Japanese Yen");
        when(repo.save(currency)).thenReturn(currency);
        Currency result = service.save(currency);
        assertEquals("JPY", result.getCode());
    }

    @Test
    public void testUpdateSuccess() {
        Currency existing = new Currency("EUR", "Euro");
        Currency updated = new Currency("EUR", "Euro Updated");

        when(repo.findById("EUR")).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenReturn(updated);

        Currency result = service.update("EUR", updated);
        assertEquals("Euro Updated", result.getName());
    }
    
    @Test
    void testUpdateNotFound() {
    	Currency updated = new Currency("XYZ", "XYZ Updated");
    	
        when(repo.findById("XYZ")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.update("XYZ", updated));

        assertEquals(messageUtil.get("currency.not.found", "XYZ"), exception.getMessage());
    }
    
    @Test
    public void testDelete() {
        service.delete("USD");
        verify(repo, times(1)).deleteById("USD");
    }
}
