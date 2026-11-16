package com.optima.oraclejdbc.response.dailystatement;

import java.io.Serializable;

import com.ocbc.schemas.soa.emf.account.aggregates.DepositAccountBalanceDetails_type0;

public class DailyStatementDetailResponse implements Serializable {
	private static final long serialVersionUID = 8134381049384852371L;
	private Double availableBalance;
	private Double ledgerBalance;
	public DailyStatementDetailResponse(DepositAccountBalanceDetails_type0 depositAccountBalanceDetails) {
		availableBalance = depositAccountBalanceDetails.getAvailableBalance().doubleValue();
		ledgerBalance = depositAccountBalanceDetails.getLedgerBalance().doubleValue();
	}
	public Double getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}
	public Double getLedgerBalance() {
		return ledgerBalance;
	}
	public void setLedgerBalance(Double ledgerBalance) {
		this.ledgerBalance = ledgerBalance;
	}
	
	
}
