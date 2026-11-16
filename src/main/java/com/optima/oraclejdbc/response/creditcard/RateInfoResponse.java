package com.optima.oraclejdbc.response.creditcard;

import java.io.Serializable;

public class RateInfoResponse implements Serializable{

	private static final long serialVersionUID = 5016696140691913451L;

	private String creditLimit;
	
	private String availableLimit;
	
	private String yearlyRetail;
	
	private String monthlyRetail;
	
	private String yearlyAdvanced;
	
	private String monthlyAdvanced;

	public String getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getAvailableLimit() {
		return availableLimit;
	}

	public void setAvailableLimit(String availableLimit) {
		this.availableLimit = availableLimit;
	}

	public String getYearlyRetail() {
		return yearlyRetail;
	}

	public void setYearlyRetail(String yearlyRetail) {
		this.yearlyRetail = yearlyRetail;
	}

	public String getMonthlyRetail() {
		return monthlyRetail;
	}

	public void setMonthlyRetail(String monthlyRetail) {
		this.monthlyRetail = monthlyRetail;
	}

	public String getYearlyAdvanced() {
		return yearlyAdvanced;
	}

	public void setYearlyAdvanced(String yearlyAdvanced) {
		this.yearlyAdvanced = yearlyAdvanced;
	}

	public String getMonthlyAdvanced() {
		return monthlyAdvanced;
	}

	public void setMonthlyAdvanced(String monthlyAdvanced) {
		this.monthlyAdvanced = monthlyAdvanced;
	}
}
