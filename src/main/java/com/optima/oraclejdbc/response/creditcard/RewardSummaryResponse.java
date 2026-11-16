package com.optima.oraclejdbc.response.creditcard;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.optima.oraclejdbc.annotations.NISPEntitySerialize;

public class RewardSummaryResponse implements Serializable{

	private static final long serialVersionUID = 3669374439128610124L;

	@NISPEntitySerialize(field="CARD_HOLDER_NO")
	private String cardNumber;
	
	@NISPEntitySerialize(field="EARNED_THIS_MONTH_SIGN")
	private Character thisMonthSign;
	
	@NISPEntitySerialize(field="EARNED_THIS_MONTH")
	private String thisMonthPoints;
	
	@NISPEntitySerialize(field="PREVIOUS_MONTH_SIGN")
	private Character previousSign;
	
	@NISPEntitySerialize(field="PREVIOUS_MONTH")
	private String totalPreviousPoints;
	
	@NISPEntitySerialize(field="USED_SIGN")
	private Character usedSign;
	
	@NISPEntitySerialize(field="USED")
	private String usedPoints;
	
	@NISPEntitySerialize(field="AVAILABLE_SIGN")
	private Character availableSign;
	
	@NISPEntitySerialize(field="AVAILABLE")
	private String available;
	
	@NISPEntitySerialize(field="AVAILABLE_EXPIRE_DATE")
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date availableExpire;
	
	@NISPEntitySerialize(field="EXTENDED")
	private String extended;
	
	@NISPEntitySerialize(field="EXTENDED_EXPIRE_DATE")
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date extendedExpire;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Character getThisMonthSign() {
		return thisMonthSign;
	}

	public void setThisMonthSign(Character thisMonthSign) {
		this.thisMonthSign = thisMonthSign;
	}

	public String getThisMonthPoints() {
		return thisMonthPoints;
	}

	public void setThisMonthPoints(String thisMonthPoints) {
		this.thisMonthPoints = thisMonthPoints;
	}

	public Character getPreviousSign() {
		return previousSign;
	}

	public void setPreviousSign(Character previousSign) {
		this.previousSign = previousSign;
	}

	public String getTotalPreviousPoints() {
		return totalPreviousPoints;
	}

	public void setTotalPreviousPoints(String totalPreviousPoints) {
		this.totalPreviousPoints = totalPreviousPoints;
	}

	public Character getUsedSign() {
		return usedSign;
	}

	public void setUsedSign(Character usedSign) {
		this.usedSign = usedSign;
	}

	public String getUsedPoints() {
		return usedPoints;
	}

	public void setUsedPoints(String usedPoints) {
		this.usedPoints = usedPoints;
	}

	public Character getAvailableSign() {
		return availableSign;
	}

	public void setAvailableSign(Character availableSign) {
		this.availableSign = availableSign;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public Date getAvailableExpire() {
		return availableExpire;
	}

	public void setAvailableExpire(Date availableExpire) {
		this.availableExpire = availableExpire;
	}

	public String getExtended() {
		return extended;
	}

	public void setExtended(String extended) {
		this.extended = extended;
	}

	public Date getExtendedExpire() {
		return extendedExpire;
	}

	public void setExtendedExpire(Date extendedExpire) {
		this.extendedExpire = extendedExpire;
	}
	
	
}
