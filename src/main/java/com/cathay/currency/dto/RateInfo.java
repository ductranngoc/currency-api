package com.cathay.currency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public final class RateInfo {
	@JsonProperty("base_currency")
    private String baseCurrency;
	@JsonProperty("quote_currency")
    private String quoteCurrency;
	@JsonProperty("close_time")
    private String closeTime;
    @JsonProperty("average_bid")
    private String averageBid;
    @JsonProperty("average_ask")
    private String averageAsk;
    @JsonProperty("high_bid")
    private String highBid;
    @JsonProperty("high_ask")
    private String highAsk;
    @JsonProperty("low_bid")
    private String lowBid;
    @JsonProperty("low_ask")
    private String lowAsk;

}
