package com.optima.oraclejdbc.response.dailystatement;

import java.math.BigDecimal;

import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_accountbalance.ConsDepositBalance_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_accountbalance.RESConsumerSequence_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_accountbalance.RESConsumer_type0;

public class DepositAccountResponse {

	private String productCode;
	private String productName;
	private String currency;
	private String accountNo;
	private BigDecimal balance;
	private BigDecimal available;
	private BigDecimal hold;
	private BigDecimal overdraft;
	
	public DepositAccountResponse(RESConsumer_type0 data, int type) {
		RESConsumerSequence_type0 resConsumerSequence_type0[] = data.getRESConsumerSequence_type0();
		ConsDepositBalance_type0 consDepositBalance_type0 = resConsumerSequence_type0[0].getConsDepositBalance();
		
		productCode = consDepositBalance_type0.getAccountBalancelnquiryKey().getProductType();
		productName = consDepositBalance_type0.getAccountBalancelnquiryKey().getProductType();
		currency = consDepositBalance_type0.getAccountBalancelnquiryKey().getAlternateAccountKeyDetail().getCurrencyType();
		accountNo = consDepositBalance_type0.getAccountBalancelnquiryKey().getAlternateAccountKeyDetail().getAccountNo1();
		balance = consDepositBalance_type0.getDepositAccountBalanceDetails().getLedgerBalance();
		available = consDepositBalance_type0.getDepositAccountBalanceDetails().getAvailableBalance();
		hold = consDepositBalance_type0.getDepositAccountBalanceDetails().getHoldBalance();
		if (type == 2) {
			//current account
			overdraft = consDepositBalance_type0.getDepositAccountBalanceDetails().getOverDraftAmount();
		}
	}
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getAvailable() {
		return available;
	}
	public void setAvailable(BigDecimal available) {
		this.available = available;
	}
	public BigDecimal getHold() {
		return hold;
	}
	public void setHold(BigDecimal hold) {
		this.hold = hold;
	}
	public BigDecimal getOverdraft() {
		return overdraft;
	}
	public void setOverdraft(BigDecimal overdraft) {
		this.overdraft = overdraft;
	}
}
