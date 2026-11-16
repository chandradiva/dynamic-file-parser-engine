package com.optima.oraclejdbc.requestinput.creditcard;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BillingStatementInput implements Serializable{
	
	private static final long serialVersionUID = 185944642521194307L;

	@Size.List({
		@Size(min=1, message="{general.notempty}"),
		@Size(max=23, message="{general.size.max}")
	})
	private String customerNumber;
	
	@Size.List({
		@Size(min=1, message="{general.notempty}"),
		@Size(max=23, message="{general.size.max}")
	})
	private String accountNumber;
	
	@NotNull(message="{general.notnull}")
	@Size.List({
		@Size(min=4, message="{general.length}"),
		@Size(max=4, message="{general.length}")
	})
	private String year;
	
	@NotNull(message="{general.notnull}")
	@Size.List({
		@Size(min=2, message="{general.length}"),
		@Size(max=2, message="{general.length}")
	})
	private String month;

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}
