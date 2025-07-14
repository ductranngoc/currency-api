package com.cathay.currency.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cathay.currency.dto.ExchangeRateDTO;
import com.cathay.currency.dto.RateInfo;
import com.cathay.currency.dto.RateInfoWapper;
import com.cathay.currency.repository.CurrencyRepository;
import com.cathay.currency.service.ExchangeRateService;
import com.cathay.currency.util.DateUtil;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService{
	
	private static final Logger log = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);
	
	private final RestTemplate restTemplate = new RestTemplate();
    private final CurrencyRepository repo;

    public ExchangeRateServiceImpl(CurrencyRepository repo) {
        this.repo = repo;
    }

	/**
	 * Fetches exchange rates for the given base currencies from an external API and stored into DB.
	 * 
	 * @param baseCurrencies List of base currencies to fetch rates for.
	 * @return ExchangeRateDTO containing the fetched rates and update time.
	 */
	
	@Override
    public ExchangeRateDTO getExternalExchangeRates(List<String> baseCurrencies) {
		log.info("Fetching exchange rates for base currencies: {}", baseCurrencies);
		
        ExchangeRateDTO dto = new ExchangeRateDTO();
        dto.setUpdateTime(DateUtil.nowFormatted());

        List<RateInfo> rateInfos = new ArrayList<>();

        for (String base : baseCurrencies) {
            log.info("Fetching rates for base currency: {}", base);
            String url = "https://fxds-public-exchange-rates-api.oanda.com/cc-api/currencies"
                    + "?base=" + base + "&quote=USD&data_type=chart"
                    + "&start_date=" + LocalDate.now().minusDays(1)
                    + "&end_date=" + LocalDate.now();

            log.debug("Request URL: {}", url);
            RateInfoWapper response = restTemplate.getForObject(url, RateInfoWapper.class);
            // Simplified parsing here
            // Add your parsing logic based on real response
            if (response != null && response.getResponse() != null) {
                for (RateInfo rate : response.getResponse()) {
                    log.debug("Processing rate: {}", rate);
					repo.findAllByOrderByCodeAsc().stream()
							.filter(currency -> currency.getCode().equals(rate.getBaseCurrency())).findFirst()
							.ifPresent(currency -> {
								    log.debug("Found currency in DB: {}", currency.getCode());
								 	// Update the currency rate with the fetched value
									currency.setQuote(rate.getQuoteCurrency());
									currency.setRate(rate.getAverageBid());
									currency.setDate(rate.getCloseTime());
								    // Save the currency with updated rate
									log.debug("Saving updated currency: {}", currency.getCode());
								    repo.save(currency);
								});

					// Add to the list of rates
                	rateInfos.add(rate);
                }
            }
        }

        dto.setRates(rateInfos);
        return dto;
    }


}
