
package com.cathay.currency.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cathay.currency.entity.Currency;
import com.cathay.currency.repository.CurrencyRepository;
import com.cathay.currency.service.CurrencyService;
import com.cathay.currency.util.MessageUtil;

@Service
public class CurrencyServiceImpl implements CurrencyService {
	
	private static final Logger log = LoggerFactory.getLogger(CurrencyServiceImpl.class);
	 
    private final CurrencyRepository repo;
    private final MessageUtil messageUtil;

    public CurrencyServiceImpl(CurrencyRepository repo, MessageUtil messageUtil) {
        this.repo = repo;
        this.messageUtil = messageUtil;
    }

    public List<Currency> getAll() {
        log.info("Fetching all currencies sorted by code");
        return repo.findAllByOrderByCodeAsc();
    }

    public Currency getByCode(String code) {
        log.info("Fetching currency by code: {}", code);
        return repo.findById(code)
                .orElseThrow(() -> {
                    return new RuntimeException(messageUtil.get("currency.not.found", code));
                });
    }
    
    public Currency save(Currency c) {
        log.info("Saving currency: {}", c.getCode());
        return repo.save(c);
    }

    public void delete(String code) {
        log.info("Deleting currency by code: {}", code);
        repo.deleteById(code);
    }

    public Currency update(String code, Currency updated) {
        log.info("Updating currency: {}", code);
        return repo.findById(code).map(c -> {
            c.setName(updated.getName());
            return repo.save(c);
        }).orElseThrow(() -> new RuntimeException(messageUtil.get("currency.not.found", code)));
    }
}
