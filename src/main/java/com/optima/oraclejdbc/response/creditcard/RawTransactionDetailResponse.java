package com.optima.oraclejdbc.response.creditcard;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntitySerialize;

public class RawTransactionDetailResponse implements Serializable{

	private static final long serialVersionUID = -9103475794223482199L;

	@NISPEntitySerialize(field="DETAIL_RECORD_TAG")
	private Character tag;
	
	@NISPEntitySerialize(field="CARDHOLDER_NAME")
	private String cardholderName;
	
	@NISPEntitySerialize(field="CARDHOLDER_NUMBER")
	private String cardholderNumber;
	
	
	@NISPEntitySerialize(field="TRANSACTION_DATE")
	private Date transactionDate;
	
	@NISPEntitySerialize(field="POSTING_DATE")
	private Date postingDate;
	
	@NISPEntitySerialize(field="DESCRIPTION_1")
	private String description;
	
	@NISPEntitySerialize(field="AMOUNT_SIGN")
	private Character amountSign;
	
	@NISPEntitySerialize(field="AMOUNT")
	private String amount;

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Date getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Character getAmountSign() {
		return amountSign;
	}

	public void setAmountSign(Character amountSign) {
		this.amountSign = amountSign;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Character getTag() {
		return tag;
	}

	public void setTag(Character tag) {
		this.tag = tag;
	}

	public String getCardholderName() {
		return cardholderName;
	}

	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}

	public String getCardholderNumber() {
		return cardholderNumber;
	}

	public void setCardholderNumber(String cardholderNumber) {
		this.cardholderNumber = cardholderNumber;
	}
	
	
}
