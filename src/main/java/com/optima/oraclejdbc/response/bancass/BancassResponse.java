package com.optima.oraclejdbc.response.bancass;

import java.math.BigDecimal;
import java.util.Date;

import com.ocbc.schemas.soa.emf.account.aggregates.NISPBancAssInfo_type0;
import com.optima.oraclejdbc.model.MappingProduct;
import com.optima.oraclejdbc.utils.DateUtility;

public class BancassResponse {

	private MappingProduct product;
	private String currency;
	private String accountNo;
	private String policyNo;
	private String status;
	private BigDecimal premium;
	private BigDecimal totalSumInsured;
	private Date lastPayment;
	private String lastPaymentStr;
	
	public BancassResponse(NISPBancAssInfo_type0 bancassInfo) {
		currency = bancassInfo.getCurrencyType();
		accountNo = bancassInfo.getSPAJNo();
		policyNo = bancassInfo.getPolicyNo();
		status = bancassInfo.getStatusDesc();
		premium = bancassInfo.getTotalPremi();
		totalSumInsured = bancassInfo.getTotalSumInsured();
		lastPayment = bancassInfo.getLastPaidDate();
		lastPaymentStr = DateUtility.formatDate(lastPayment);
	}
	
	public String getCurrency() {
		return currency;
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
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getPremium() {
		return premium;
	}
	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}
	public BigDecimal getTotalSumInsured() {
		return totalSumInsured;
	}
	public void setTotalSumInsured(BigDecimal totalSumInsured) {
		this.totalSumInsured = totalSumInsured;
	}
	public Date getLastPayment() {
		return lastPayment;
	}
	public void setLastPayment(Date lastPayment) {
		this.lastPayment = lastPayment;
	}
	public String getLastPaymentStr() {
		return lastPaymentStr;
	}
	public void setLastPaymentStr(String lastPaymentStr) {
		this.lastPaymentStr = lastPaymentStr;
	}

	public MappingProduct getProduct() {
		return product;
	}

	public void setProduct(MappingProduct product) {
		this.product = product;
	}
	
}
