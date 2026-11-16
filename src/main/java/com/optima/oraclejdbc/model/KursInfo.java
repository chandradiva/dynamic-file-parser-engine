package com.optima.oraclejdbc.model;

import java.io.Serializable;

import com.ocbc.schemas.soa.fx.aggregates.FXDetails_type1;
import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntitySerialize;
import com.optima.oraclejdbc.cons.CommonCons;

@NISPEntity(storedProcedure="SP_SAVE_KURS_INFO", dbSchema=CommonCons.DB_SCHEMA)
public class KursInfo implements Serializable{
	private static final long serialVersionUID = -7194916221084343006L;
	@NISPEntitySerialize(field="ID")
	private Integer id;
	@NISPEntitySerialize(field="CURRENCY")
	private String currency;
	@NISPEntitySerialize(field="BN_BUY")
	private Double bnBuy;
	@NISPEntitySerialize(field="BN_SELL")
	private Double bnSell;
	@NISPEntitySerialize(field="TT_BUY")
	private Double ttBuy;
	@NISPEntitySerialize(field="TT_SELL")
	private Double ttSell;
	public KursInfo(){};
	public KursInfo(FXDetails_type1 fxDetails) {
		currency = fxDetails.getCurrencyCode();
		bnBuy = fxDetails.getBNBuyRate().doubleValue();
		bnSell = fxDetails.getBNSellRate().doubleValue();
		ttBuy = fxDetails.getTTBuyRate().doubleValue();
		ttSell = fxDetails.getTTSellRate().doubleValue();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
}
