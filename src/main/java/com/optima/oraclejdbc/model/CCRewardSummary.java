package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityDateFomat;
import com.optima.oraclejdbc.annotations.NISPEntityDefaultValue;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName="CC_REWARD_SUMMARY")
public class CCRewardSummary extends CCParentModel implements CCChildModel, CCCHChildModel, Serializable {

	private static final long serialVersionUID = 299262171503655014L;

	@NISPEntity(columnName="ID")
	@NISPEntityId
	private Long id;
	
	@NISPEntity(columnName="CC_STATEMENT_ID")
	private Long ccStatementId;
	
	@NISPEntity(columnName="RECORD_TYPE")
	@Position(start = 0, length = 2)
	private String recordType;
	
	@NISPEntity(columnName="CARD_HOLDER_NO")
	@Position(start = 2, length = 23)
	private String cardHolderNo;
	
	@NISPEntity(columnName="EARNED_THIS_MONTH_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 25, length = 1)
	private String earnedThisMonthSign;
	
	@NISPEntity(columnName="EARNED_THIS_MONTH")
	@Position(start = 26, length = 11)
	private String earnedThisMonth;
	
	@NISPEntity(columnName="PREVIOUS_MONTH_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 37, length = 1)
	private String previousMonthSign;
	
	@NISPEntity(columnName="PREVIOUS_MONTH")
	@Position(start = 38, length = 11)
	private String previousMonth;
	
	@NISPEntity(columnName="USED_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 49, length = 1)
	private String usedSign;
	
	@NISPEntity(columnName="USED")
	@Position(start = 50, length = 11)
	private String used;
	
	@NISPEntity(columnName="AVAILABLE_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 61, length = 1)
	private String availableSign;
	
	@NISPEntity(columnName="AVAILABLE")
	@Position(start = 62, length = 11)
	private String available;
	
	@NISPEntity(columnName="AVAILABLE_EXPIRE_DATE")
	@NISPEntityDateFomat(format="ddMMyyyy")
	@Position(start = 73, length = 8)
	private Date availableExpireDate;
	
	@NISPEntity(columnName="EXTENDED")
	@Position(start = 81, length = 11)
	private String extended;
	
	@NISPEntity(columnName="EXTENDED_EXPIRE_DATE")
	@NISPEntityDateFomat(format="ddMMyyyy")
	@Position(start = 92, length = 8)
	private Date extendedExpireDate;
	
	@NISPEntity(columnName="REWARD_DESCRIPTION_TYPE")
	@Position(start = 100, length = 2)
	private String rewardDescriptionType;
	
	@NISPEntity(columnName="REFUNDED_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 102, length = 1)
	private String refundedSign;
	
	@NISPEntity(columnName="REFUNDED")
	@Position(start = 103, length = 11)
	private String refunded;
	
	@NISPEntity(columnName="AVAILABLE_POINTS_1_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 114, length = 1)
	private String availablePoints1Sign;
	
	@NISPEntity(columnName="AVAILABLE_POINTS_1")
	@Position(start = 115, length = 11)
	private String availablePoints1;
	
	@NISPEntity(columnName="AVAILABLE_POINTS_1_EXP_DATE")
	@NISPEntityDateFomat(format="ddMMyyyy")
	@Position(start = 126, length = 8)
	private Date avalablePoints1ExpirationDate;
	
	@NISPEntity(columnName="AVAILABLE_POINTS_2_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 134, length = 1)
	private String availablePoints2Sign;
	
	@NISPEntity(columnName="AVAILABLE_POINTS_2")
	@Position(start = 135, length = 11)
	private String availablePoints2;
	
	@NISPEntity(columnName="AVAILABLE_POINTS_EXP_DATE")
	@Position(start = 146, length = 8)
	private Date avalablePoints2ExpirationDate;
	
	@NISPEntity(columnName="BONUS_PROGRAM_DESCRIPTION")
	@Position(start = 154, length = 5)
	private String bonusProgramDescription;
	
	private String filler;
	
	@NISPEntity(columnName = "CREATED_DATE", create = true)
	private Date createdDate;
	
	@NISPEntity(columnName = "UPDATED_DATE", update = true)
	private Date updatedDate;
	
	@NISPEntity(columnName = "CH_ID")
	private Long chId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCcStatementId() {
		return ccStatementId;
	}

	@Override
	public void setCcStatementId(Long ccStatementId) {
		this.ccStatementId = ccStatementId;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getCardHolderNo() {
		return cardHolderNo;
	}

	public void setCardHolderNo(String cardHolderNo) {
		this.cardHolderNo = cardHolderNo;
	}

	public String getEarnedThisMonthSign() {
		return earnedThisMonthSign;
	}

	public void setEarnedThisMonthSign(String earnedThisMonthSign) {
		this.earnedThisMonthSign = earnedThisMonthSign;
	}

	public String getEarnedThisMonth() {
		return earnedThisMonth;
	}

	public void setEarnedThisMonth(String earnedThisMonth) {
		this.earnedThisMonth = earnedThisMonth;
	}

	public String getPreviousMonthSign() {
		return previousMonthSign;
	}

	public void setPreviousMonthSign(String previousMonthSign) {
		this.previousMonthSign = previousMonthSign;
	}

	public String getPreviousMonth() {
		return previousMonth;
	}

	public void setPreviousMonth(String previousMonth) {
		this.previousMonth = previousMonth;
	}

	public String getUsedSign() {
		return usedSign;
	}

	public void setUsedSign(String usedSign) {
		this.usedSign = usedSign;
	}

	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}

	public String getAvailableSign() {
		return availableSign;
	}

	public void setAvailableSign(String availableSign) {
		this.availableSign = availableSign;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public Date getAvailableExpireDate() {
		return availableExpireDate;
	}

	public void setAvailableExpireDate(Date availableExpireDate) {
		this.availableExpireDate = availableExpireDate;
	}

	public String getExtended() {
		return extended;
	}

	public void setExtended(String extended) {
		this.extended = extended;
	}

	public Date getExtendedExpireDate() {
		return extendedExpireDate;
	}

	public void setExtendedExpireDate(Date extendedExpireDate) {
		this.extendedExpireDate = extendedExpireDate;
	}

	public String getRewardDescriptionType() {
		return rewardDescriptionType;
	}

	public void setRewardDescriptionType(String rewardDescriptionType) {
		this.rewardDescriptionType = rewardDescriptionType;
	}

	public String getRefundedSign() {
		return refundedSign;
	}

	public void setRefundedSign(String refundedSign) {
		this.refundedSign = refundedSign;
	}

	public String getRefunded() {
		return refunded;
	}

	public void setRefunded(String refunded) {
		this.refunded = refunded;
	}

	public String getAvailablePoints1Sign() {
		return availablePoints1Sign;
	}

	public void setAvailablePoints1Sign(String availablePoints1Sign) {
		this.availablePoints1Sign = availablePoints1Sign;
	}

	public String getAvailablePoints1() {
		return availablePoints1;
	}

	public void setAvailablePoints1(String availablePoints1) {
		this.availablePoints1 = availablePoints1;
	}

	public Date getAvalablePoints1ExpirationDate() {
		return avalablePoints1ExpirationDate;
	}

	public void setAvalablePoints1ExpirationDate(Date avalablePoints1ExpirationDate) {
		this.avalablePoints1ExpirationDate = avalablePoints1ExpirationDate;
	}

	public String getAvailablePoints2Sign() {
		return availablePoints2Sign;
	}

	public void setAvailablePoints2Sign(String availablePoints2Sign) {
		this.availablePoints2Sign = availablePoints2Sign;
	}

	public String getAvailablePoints2() {
		return availablePoints2;
	}

	public void setAvailablePoints2(String availablePoints2) {
		this.availablePoints2 = availablePoints2;
	}

	public Date getAvalablePoints2ExpirationDate() {
		return avalablePoints2ExpirationDate;
	}

	public void setAvalablePoints2ExpirationDate(Date avalablePoints2ExpirationDate) {
		this.avalablePoints2ExpirationDate = avalablePoints2ExpirationDate;
	}

	public String getBonusProgramDescription() {
		return bonusProgramDescription;
	}

	public void setBonusProgramDescription(String bonusProgramDescription) {
		this.bonusProgramDescription = bonusProgramDescription;
	}

	public String getFiller() {
		return filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getChId() {
		return chId;
	}

	@Override
	public void setChId(Long chId) {
		this.chId = chId;
	}
}
