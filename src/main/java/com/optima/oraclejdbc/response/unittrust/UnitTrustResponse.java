package com.optima.oraclejdbc.response.unittrust;

import java.io.Serializable;

import com.ocbc.schemas.soa.emf.investment.aggregates.InvestmentAccountDetail_type0;

public class UnitTrustResponse implements Serializable{

	private static final long serialVersionUID = 4308340815800469131L;
	private String productName;
	private String currency;
	private String accountNo;
	private Double outstandingUnits;
	private Double nav;
	private Double estimatedAmount;
	
	public UnitTrustResponse(InvestmentAccountDetail_type0 detail) {
		productName = detail.getProductName();
		currency = detail.getProductCurrency();
		accountNo = detail.getClientCode();
		outstandingUnits = detail.getUnitBalance().doubleValue();
		nav = detail.getNAV().doubleValue();
		estimatedAmount = detail.getAmountBalance().doubleValue();
	}

	public String getCurrency() {
		return currency;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Double getOutstandingUnits() {
		return outstandingUnits;
	}

	public void setOutstandingUnits(Double outstandingUnits) {
		this.outstandingUnits = outstandingUnits;
	}

	public Double getNav() {
		return nav;
	}

	public void setNav(Double nav) {
		this.nav = nav;
	}

	public Double getEstimatedAmount() {
		return estimatedAmount;
	}

	public void setEstimatedAmount(Double estimatedAmount) {
		this.estimatedAmount = estimatedAmount;
	}
	
}
