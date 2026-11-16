package com.optima.oraclejdbc.response.creditcard;

import java.io.Serializable;
import java.util.List;

public class BillingStatementResponse implements Serializable {

	private static final long serialVersionUID = 823667100852089954L;
	
	private CustomerHeaderResponse customerHeader;
	private AccountHeaderResponse accountHeader;
	private List<TransactionDetailResponse> transactionDetails;
	private AccountBalanceResponse accountBalance;
	private RewardSummaryResponse rewardSummary;
	private RateInfoResponse rateInfo;
	public CustomerHeaderResponse getCustomerHeader() {
		return customerHeader;
	}
	public void setCustomerHeader(CustomerHeaderResponse customerHeader) {
		this.customerHeader = customerHeader;
	}
	public AccountHeaderResponse getAccountHeader() {
		return accountHeader;
	}
	public void setAccountHeader(AccountHeaderResponse accountHeader) {
		this.accountHeader = accountHeader;
	}
	
	public AccountBalanceResponse getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(AccountBalanceResponse accountBalance) {
		this.accountBalance = accountBalance;
	}
	public RewardSummaryResponse getRewardSummary() {
		return rewardSummary;
	}
	public void setRewardSummary(RewardSummaryResponse rewardSummary) {
		this.rewardSummary = rewardSummary;
	}
	public RateInfoResponse getRateInfo() {
		return rateInfo;
	}
	public void setRateInfo(RateInfoResponse rateInfo) {
		this.rateInfo = rateInfo;
	}
	public List<TransactionDetailResponse> getTransactionDetails() {
		return transactionDetails;
	}
	public void setTransactionDetails(List<TransactionDetailResponse> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}
	
	
}
