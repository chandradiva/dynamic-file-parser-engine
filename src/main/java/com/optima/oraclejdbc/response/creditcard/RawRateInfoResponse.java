package com.optima.oraclejdbc.response.creditcard;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntitySerialize;

public class RawRateInfoResponse implements Serializable {

	private static final long serialVersionUID = 3780139851168484899L;

	@NISPEntitySerialize(field="YEARLY_RETAIL_PERCENTAGE")
	private String yearlyRetail;
	
	@NISPEntitySerialize(field="MONTHLY_RETAIL_PERCENTAGE")
	private String monthlyRetail;
	
	@NISPEntitySerialize(field="YEARLY_CASH_ADVANCE")
	private String yearlyAdvanced;
	
	@NISPEntitySerialize(field="MONTHLY_CASH_ADVENCE")
	private String monthlyAdvanced;

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
