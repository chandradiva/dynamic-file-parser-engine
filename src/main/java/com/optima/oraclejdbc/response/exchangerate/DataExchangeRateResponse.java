package com.optima.oraclejdbc.response.exchangerate;

import java.io.Serializable;

import com.ocbc.schemas.soa.fx.aggregates.FXDetails_type1;

public class DataExchangeRateResponse implements Serializable{

	private static final long serialVersionUID = -7966644810425117320L;
	private String currency;
	private Double bnBuy;
	private Double bnSell;
	private Double ttBuy;
	private Double ttSell;
	public DataExchangeRateResponse(FXDetails_type1 fxDetails) {
		currency = fxDetails.getCurrencyCode();
		bnBuy = fxDetails.getBNBuyRate().doubleValue();
		bnSell = fxDetails.getBNSellRate().doubleValue();
		ttBuy = fxDetails.getTTBuyRate().doubleValue();
		ttSell = fxDetails.getTTSellRate().doubleValue();
				
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getBnBuy() {
		return bnBuy;
	}
	public void setBnBuy(Double bnBuy) {
		this.bnBuy = bnBuy;
	}
	public Double getBnSell() {
		return bnSell;
	}
	public void setBnSell(Double bnSell) {
		this.bnSell = bnSell;
	}
	public Double getTtBuy() {
		return ttBuy;
	}
	public void setTtBuy(Double ttBuy) {
		this.ttBuy = ttBuy;
	}
	public Double getTtSell() {
		return ttSell;
	}
	public void setTtSell(Double ttSell) {
		this.ttSell = ttSell;
	}
}
