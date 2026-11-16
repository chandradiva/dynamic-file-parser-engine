package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityDefaultValue;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName="CC_LUCKY_DRAW")
public class CCLuckyDraw extends CCParentModel implements CCChildModel, CCCHChildModel, Serializable {

	private static final long serialVersionUID = -8406689351768617900L;

	@NISPEntity(columnName="ID")
	@NISPEntityId
	private Long id;
	
	@NISPEntity(columnName="CC_STATEMENT_ID")
	private Long ccStatementId;
	
	@NISPEntity(columnName="RECORD_TYPE")
	@Position(start = 0, length = 2)
	private String recordType;
	
	@NISPEntity(columnName="LUCKY_DRAW_DESC")
	@Position(start = 2, length = 25)
	private String luckyDrawDesc;
	
	@NISPEntity(columnName="LD_POINTS_EARNED_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 27, length = 1)
	private String ldPointsEarnedSign;
	
	@NISPEntity(columnName="POINTS_EARNED_THIS_MONTH")
	@Position(start = 28, length = 11)
	private String pointsEarnedThisMonth;
	
	@NISPEntity(columnName="LD_POINTS_BALANCE_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 39, length = 1)
	private String ldPointsBalanceSign;
	
	@NISPEntity(columnName="LD_POINTS_BALANCE")
	@Position(start = 40, length = 11)
	private String ldPointsBalance;
	
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

	public String getLuckyDrawDesc() {
		return luckyDrawDesc;
	}

	public void setLuckyDrawDesc(String luckyDrawDesc) {
		this.luckyDrawDesc = luckyDrawDesc;
	}

	public String getLdPointsEarnedSign() {
		return ldPointsEarnedSign;
	}

	public void setLdPointsEarnedSign(String ldPointsEarnedSign) {
		this.ldPointsEarnedSign = ldPointsEarnedSign;
	}

	public String getPointsEarnedThisMonth() {
		return pointsEarnedThisMonth;
	}

	public void setPointsEarnedThisMonth(String pointsEarnedThisMonth) {
		this.pointsEarnedThisMonth = pointsEarnedThisMonth;
	}

	public String getLdPointsBalanceSign() {
		return ldPointsBalanceSign;
	}

	public void setLdPointsBalanceSign(String ldPointsBalanceSign) {
		this.ldPointsBalanceSign = ldPointsBalanceSign;
	}

	public String getLdPointsBalance() {
		return ldPointsBalance;
	}

	public void setLdPointsBalance(String ldPointsBalance) {
		this.ldPointsBalance = ldPointsBalance;
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

	public void setChId(Long chId) {
		this.chId = chId;
	}
}
