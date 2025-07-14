package com.cathay.currency.dto;

import java.util.List;

import lombok.Data;

@Data
public class ExchangeRateDTO {
    private String updateTime;
    private List<RateInfo> rates;
}
