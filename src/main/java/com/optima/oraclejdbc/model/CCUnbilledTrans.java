package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;

@NISPEntity(tableName = "CC_UNBILLED_TRANS_TEMP")
public class CCUnbilledTrans implements Serializable {

	private static final long serialVersionUID = -891729296533891866L;

	@NISPEntityId
	@NISPEntity(columnName = "ID")
	private Long id;
	
	@NISPEntity(columnName = "CREDIT_CARD_NUMBER")
	private String creditCardNumber;
	
	@NISPEntity(columnName = "CREDIT_CARD_NAME")
	private String creditCardName;
	
	@NISPEntity(columnName = "TRANS_DATE")
	private Date transDate;
	
	@NISPEntity(columnName = "TRANS_DESC")
	private String transDesc;
	
	@NISPEntity(columnName = "AMOUNT_SIGN")
	private String amountSign;
	
	@NISPEntity(columnName = "AMOUNT")
	private Double amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCreditCardName() {
		return creditCardName;
	}

	public void setCreditCardName(String creditCardName) {
		this.creditCardName = creditCardName;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getTransDesc() {
		return transDesc;
	}

	public void setTransDesc(String transDesc) {
		this.transDesc = transDesc;
	}

	public String getAmountSign() {
		return amountSign;
	}

	public void setAmountSign(String amountSign) {
		this.amountSign = amountSign;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
