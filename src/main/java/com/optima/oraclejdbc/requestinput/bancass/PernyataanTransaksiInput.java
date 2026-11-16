package com.optima.oraclejdbc.requestinput.bancass;

import java.io.Serializable;

import javax.validation.constraints.Size;

import com.optima.oraclejdbc.utils.CommonUtils;

public class PernyataanTransaksiInput implements Serializable {

	private static final long serialVersionUID = 7878036079726187261L;

	@Size.List({
		@Size(min=1, message="{general.notempty}"),
		@Size(max=23, message="{general.size.max}")
	})
	private String cifKey;
	
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
	
	@Size.List({
		@Size(min=1, message="{general.notempty}"),
		@Size(max=30, message="{general.size.max}")
	})
	private String policyNo;

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getCifKey() {
		return cifKey;
	}

	public void setCifKey(String cifKey) {
		this.cifKey = CommonUtils.normalizeCifKey(cifKey);
	}
	
	
}
