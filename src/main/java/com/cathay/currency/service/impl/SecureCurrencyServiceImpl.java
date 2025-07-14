package com.cathay.currency.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cathay.currency.entity.Currency;
import com.cathay.currency.repository.CurrencyRepository;
import com.cathay.currency.service.SecureCurrencyService;
import com.cathay.currency.util.AESUtil;
import com.cathay.currency.util.MessageUtil;

@Service
public class SecureCurrencyServiceImpl implements SecureCurrencyService {
    private final CurrencyRepository repo;
    private final MessageUtil messageUtil;

    public SecureCurrencyServiceImpl(CurrencyRepository repo, MessageUtil messageUtil) {
        this.repo = repo;
        this.messageUtil = messageUtil;
    }

    public List<Currency> getAll() {
        return repo.findAllByOrderByCodeAsc().stream()
                .map(this::decryptCurrency)
                .collect(Collectors.toList());
    }

    public Currency save(Currency c) {
        return decryptCurrency(repo.save(encryptCurrency(c)));
    }

    public void delete(String code) {
        repo.deleteById(code);
    }

    public Currency update(String code, Currency updated) {
        Optional<Currency> opt = repo.findById(code);
        if (opt.isEmpty()) throw new RuntimeException(messageUtil.get("currency.not.found", code));
        Currency toUpdate = opt.get();
        toUpdate.setName(updated.getName());
        return decryptCurrency(repo.save(encryptCurrency(toUpdate)));
    }

    public Currency get(String code) {
        return repo.findById(code).map(this::decryptCurrency)
                .orElseThrow(() -> new RuntimeException(messageUtil.get("currency.not.found", code)));
    }

    private Currency encryptCurrency(Currency c) {
        try {
            return new Currency(c.getCode(), AESUtil.encrypt(c.getQuote()),
            		AESUtil.encrypt(c.getRate()), AESUtil.encrypt(c.getDate()), c.getName());
        } catch (Exception e) {
            throw new RuntimeException(messageUtil.get("encryption.error"), e);
        }
    }

    private Currency decryptCurrency(Currency c) {
        try {
            return new Currency(c.getCode(), AESUtil.decrypt(c.getQuote()),
            		AESUtil.decrypt(c.getRate()), AESUtil.decrypt(c.getDate()), c.getName());
        } catch (Exception e) {
            throw new RuntimeException(messageUtil.get("decryption.error"), e);
        }
    }
}

