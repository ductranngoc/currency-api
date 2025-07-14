package com.cathay.currency.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        ExchangeRateDTO dto = new ExchangeRateDTO();
        dto.setUpdateTime(DateUtil.nowFormatted());

        List<RateInfo> rateInfos = new ArrayList<>();

        for (String base : baseCurrencies) {
            String url = "https://fxds-public-exchange-rates-api.oanda.com/cc-api/currencies"
                    + "?base=" + base + "&quote=USD&data_type=chart"
                    + "&start_date=" + LocalDate.now().minusDays(1)
                    + "&end_date=" + LocalDate.now();

            RateInfoWapper response = restTemplate.getForObject(url, RateInfoWapper.class);
            // Simplified parsing here
            // Add your parsing logic based on real response
            if (response != null && response.getResponse() != null) {
                for (RateInfo rate : response.getResponse()) {
					repo.findAllByOrderByCodeAsc().stream()
							.filter(currency -> currency.getCode().equals(rate.getBaseCurrency())).findFirst()
							.ifPresent(currency -> {
								    // Update the currency rate with the fetched value
									currency.setQuote(rate.getQuoteCurrency());
									currency.setRate(rate.getAverageBid());
									currency.setDate(rate.getCloseTime());
								    // Save the currency with updated rate
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
