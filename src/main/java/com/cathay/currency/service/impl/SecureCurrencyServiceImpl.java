package com.cathay.currency.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cathay.currency.entity.Currency;
import com.cathay.currency.repository.CurrencyRepository;
import com.cathay.currency.service.SecureCurrencyService;
import com.cathay.currency.util.AESUtil;
import com.cathay.currency.util.MessageUtil;

@Service
public class SecureCurrencyServiceImpl implements SecureCurrencyService {
	
	private static final Logger log = LoggerFactory.getLogger(SecureCurrencyServiceImpl.class);
	
    private final CurrencyRepository repo;
    private final MessageUtil messageUtil;

    public SecureCurrencyServiceImpl(CurrencyRepository repo, MessageUtil messageUtil) {
        this.repo = repo;
        this.messageUtil = messageUtil;
    }

    public List<Currency> getAll() {
    	log.info("Fetching all currencies sorted by code");
        return repo.findAllByOrderByCodeAsc().stream()
                .map(this::decryptCurrency)
                .collect(Collectors.toList());
    }

    public Currency save(Currency c) {
        log.info("Saving currency: {}", c.getCode());
        Currency encrypted = encryptCurrency(c);
        Currency saved = repo.save(encrypted);
        log.debug("Currency saved (encrypted): {}", saved);
        return decryptCurrency(saved);
    }

    public void delete(String code) {
    	log.info("Deleting currency by code: {}", code);
        repo.deleteById(code);
    }

    public Currency update(String code, Currency updated) {
    	log.info("Updating currency: {}", code);
        Optional<Currency> opt = repo.findById(code);
        if (opt.isEmpty()) {
        	log.warn("Currency not found for update: {}", code);
        	throw new RuntimeException(messageUtil.get("currency.not.found", code));
        }
        Currency toUpdate = opt.get();
        toUpdate.setName(updated.getName());
        return decryptCurrency(repo.save(encryptCurrency(toUpdate)));
    }

    public Currency get(String code) {
    	log.info("Getting currency: {}", code);
        return repo.findById(code)
                .map(this::decryptCurrency)
                .orElseThrow(() -> {
                    log.warn("Currency not found: {}", code);
                    return new RuntimeException(messageUtil.get("currency.not.found", code));
                });
    }

    private Currency encryptCurrency(Currency c) {
        try {
        	log.debug("Encrypting currency: {}", c.getCode());
            return new Currency(c.getCode(), AESUtil.encrypt(c.getQuote()),
            		AESUtil.encrypt(c.getRate()), AESUtil.encrypt(c.getDate()), c.getName());
        } catch (Exception e) {
        	log.error("Error encrypting currency: {}", c.getCode(), e);
            throw new RuntimeException(messageUtil.get("encryption.error"), e);
        }
    }

    private Currency decryptCurrency(Currency c) {
        try {
        	log.debug("Decrypting currency: {}", c.getCode());
            return new Currency(c.getCode(), AESUtil.decrypt(c.getQuote()),
            		AESUtil.decrypt(c.getRate()), AESUtil.decrypt(c.getDate()), c.getName());
        } catch (Exception e) {
        	log.error("Error decrypting currency: {}", c.getCode(), e);
            throw new RuntimeException(messageUtil.get("decryption.error"), e);
        }
    }
}

