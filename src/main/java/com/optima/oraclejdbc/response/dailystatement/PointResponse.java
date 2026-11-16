package com.optima.oraclejdbc.response.dailystatement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_accountbalance.ConsDepositBalance_type0;
import com.optima.oraclejdbc.model.MappingProduct;

public class PointResponse implements Serializable{

	private static final long serialVersionUID = 3137462991935274266L;
	private MappingProduct product;
	private String accountNo;
	private BigDecimal balance;
	@JsonFormat(pattern="dd/MM/yyyy", timezone="Asia/Jakarta")
	private Date expiredDate;
	
	
	public PointResponse(ConsDepositBalance_type0 data) {
		accountNo = data.getAccountBalancelnquiryKey().getAlternateAccountKeyDetail().getAccountNo1();
		balance = data.getDepositAccountBalanceDetails().getAvailableBalance();
		expiredDate = new Date();
	}
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public Date getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public MappingProduct getProduct() {
		return product;
	}

	public void setProduct(MappingProduct product) {
		this.product = product;
	}
	
}
