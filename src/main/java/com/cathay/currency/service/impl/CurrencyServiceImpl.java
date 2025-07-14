
package com.cathay.currency.service.impl;

import com.cathay.currency.entity.Currency;
import com.cathay.currency.repository.CurrencyRepository;
import com.cathay.currency.service.CurrencyService;
import com.cathay.currency.util.MessageUtil;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository repo;
    private final MessageUtil messageUtil;

    public CurrencyServiceImpl(CurrencyRepository repo, MessageUtil messageUtil) {
        this.repo = repo;
        this.messageUtil = messageUtil;
    }

    public List<Currency> getAll() {
        return repo.findAllByOrderByCodeAsc();
    }

    public Currency save(Currency c) {
        return repo.save(c);
    }

    public void delete(String code) {
        repo.deleteById(code);
    }

    public Currency update(String code, Currency updated) {
        return repo.findById(code).map(c -> {
            c.setName(updated.getName());
            return repo.save(c);
        }).orElseThrow(() -> new RuntimeException(messageUtil.get("currency.not.found", code)));
    }
}
