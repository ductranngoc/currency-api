package com.cathay.currency.repository;

import com.cathay.currency.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, String> {
    List<Currency> findAllByOrderByCodeAsc();
}
