package com.optima.oraclejdbc.response.creditcard;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntitySerialize;

public class UserLimitResponse implements Serializable {

	private static final long serialVersionUID = -4942736131171086741L;

	@NISPEntitySerialize(field="CREDIT_LIMIT")
	private String creditLimit;
	
	@NISPEntitySerialize(field="AVAILABLE_LIMIT")
	private String availableLimit;

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
	
	
}
