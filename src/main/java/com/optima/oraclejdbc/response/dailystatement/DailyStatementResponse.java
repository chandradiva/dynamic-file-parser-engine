package com.optima.oraclejdbc.response.dailystatement;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ocbc.schemas.soa.emf.account.aggregates.TransactionStatementDetails_type0;
import com.optima.oraclejdbc.annotations.NISPEntitySerialize;
import com.optima.oraclejdbc.jsonserializer.DoubleSerializer;

public class DailyStatementResponse implements Serializable{
	
	private static final long serialVersionUID = -4101823216682072404L;
	
	@JsonFormat(pattern="dd/MM/yyyy", timezone="Asia/Jakarta")
	@NISPEntitySerialize(field="TRANSACTION_DATE")
	private Date transactionDate;
	
	@JsonFormat(pattern="dd/MM/yyyy", timezone="Asia/Jakarta")
	@NISPEntitySerialize(field="VALUE_DATE")
	private Date valueDate;
	
	@NISPEntitySerialize(field="TRAN_CODE")
	private String tranCode;
	
	@NISPEntitySerialize(field="TRAN_DESC")
	private String transDescription;
	
	@NISPEntitySerialize(field="TRX_DESC")
	private String trxDescription;
	
	@NISPEntitySerialize(field="DEBIT_OR_CREDIT")
	private Character sign;
	
	@JsonSerialize(using=DoubleSerializer.class)
	@NISPEntitySerialize(field="AMOUNT")
	private Double amount;
	
	@JsonSerialize(using=DoubleSerializer.class)
	@NISPEntitySerialize(field="BALANCE")
	private Double balance;
	
	@NISPEntitySerialize(field = "REMARK_1")
	private String remark1;
	
	@NISPEntitySerialize(field = "REMARK_2")
	private String remark2;
	
	@NISPEntitySerialize(field = "REMARK_3")
	private String remark3;
	
	@NISPEntitySerialize(field = "REMARK_4")
	private String remark4;
		
	@NISPEntitySerialize(field = "CHEQUE_NO")
	private String chequeNo;
	
	public DailyStatementResponse(){}
	
	public DailyStatementResponse(TransactionStatementDetails_type0 detail, Double currentBalance, boolean fromSummary) {
		transactionDate = detail.getTransactionDate();
		valueDate = detail.getPostDate();
		transDescription = detail.getDescription();
		sign = detail.getDebitCreditIndicator().charAt(0);
		amount = detail.getAmount().doubleValue();
		double amountFactor;
		if( fromSummary )
			amountFactor = -amount;
		else
			amountFactor = amount;
		if( sign == 'C' || sign == 'c' ){
			balance = currentBalance + amountFactor;
		}else{
			balance = currentBalance - amountFactor;
		}
	}

	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	public Character getSign() {
		return sign;
	}
	public void setSign(Character sign) {
		this.sign = sign;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getTransDescription() {
		return transDescription;
	}
	public void setTransDescription(String transDescription) {
		this.transDescription = transDescription;
	}
	public String getTrxDescription() {
		return trxDescription;
	}
	public void setTrxDescription(String trxDescription) {
		this.trxDescription = trxDescription;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getRemark4() {
		return remark4;
	}

	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	
	
}
