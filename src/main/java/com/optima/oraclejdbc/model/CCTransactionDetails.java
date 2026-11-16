package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityDateFomat;
import com.optima.oraclejdbc.annotations.NISPEntityDefaultValue;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName="CC_TRANSACTION_DETAILS")
public class CCTransactionDetails extends CCParentModel implements CCChildModel, CCAHChildModel, Serializable {

	private static final long serialVersionUID = 8224420848776682833L;

	@NISPEntity(columnName="ID")
	@NISPEntityId
	private Long id;
	
	@NISPEntity(columnName="CC_STATEMENT_ID")
	private Long ccStatementId;
	
	@NISPEntity(columnName="RECORD_TYPE")
	@Position(start = 0, length = 2)
	private String recordType;
	
	@NISPEntity(columnName="DETAIL_RECORD_TAG")
	@Position(start = 2, length = 1)
	private String detailRecordTag;
	
	@NISPEntity(columnName="CARDHOLDER_NUMBER")
	@Position(start = 3, length = 23)
	private String cardholderNumber;
	
	@NISPEntity(columnName="CARDHOLDER_NAME")
	@Position(start = 26, length = 50)
	private String cardholderName;
	
	@NISPEntity(columnName="BASIC_SUPP_CARD_INDICATOR")
	@NISPEntityDefaultValue(defaultValue = "BE")
	@Position(start = 76, length = 1)
	private String basicSupplementaryCardIndicator;
	
	@NISPEntity(columnName="TRANSACTION_DATE")
	@NISPEntityDateFomat(format="ddMM")
	@Position(start = 77, length = 4)
	private Date transactionDate;
	
	@NISPEntity(columnName="DESCRIPTION_1")
	@Position(start = 81, length = 50)
	private String description1;
	
	@NISPEntity(columnName="DESCRIPTION_2")
	@Position(start = 131, length = 50)
	private String description2;
	
	@NISPEntity(columnName="AMOUNT_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 181, length = 1)
	private String amountSign;
	
	@NISPEntity(columnName="AMOUNT")
	@Position(start = 182, length = 14)
	private String amount;
	
	@NISPEntity(columnName="POSTING_DATE")
	@NISPEntityDateFomat(format="ddMM")
	@Position(start = 196, length = 4)
	private Date postingDate;
	
	@NISPEntity(columnName="BALANCE_TRANSFER_VALIDITY_FROM")
	@NISPEntityDateFomat(format="ddMMyyyy")
	@Position(start = 200, length = 8)
	private Date balanceTransferValidityPeriodFrom;
	
	@NISPEntity(columnName="BALANCE_TRANSFER_VALIDITY_TO")
	@NISPEntityDateFomat(format="ddMMyyyy")
	@Position(start = 208, length = 8)
	private Date balanceTransferValidityPeriodTo;
	
	@NISPEntity(columnName="CORPORATE_INTEREST_INDICATOR")
	@NISPEntityDefaultValue(defaultValue = "CR")
	@Position(start = 216, length = 1)
	private String corporateInterestIndicator;
	
	@NISPEntity(columnName="TRANSACTION_CODE")
	@Position(start = 217, length = 3)
	private String transactionCode;
	
	@NISPEntity(columnName="TRANSACTION_SOURCE")
	@Position(start = 220, length = 1)
	private String transactionSource;
	
	@NISPEntity(columnName="RETAIL_ACCT_NO")
	@Position(start = 221, length = 19)
	private String retailAcctNo;
	
//	@NISPEntity(columnName="DUAL_CURRENCY_TRANSACTION_DESC")
//	@Position(start = 240, length = 20) //HILANG
	private String dualCurrencyTransactionDescription;
	
	private String filler;
	
	@NISPEntity(columnName = "CREATED_DATE", create = true)
	private Date createdDate;
	
	@NISPEntity(columnName = "UPDATED_DATE", update = true)
	private Date updatedDate;
	
	@NISPEntity(columnName = "AH_ID")
	private Long ahId;

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

	public String getDetailRecordTag() {
		return detailRecordTag;
	}

	public void setDetailRecordTag(String detailRecordTag) {
		this.detailRecordTag = detailRecordTag;
	}

	public String getCardholderNumber() {
		return cardholderNumber;
	}

	public void setCardholderNumber(String cardholderNumber) {
		this.cardholderNumber = cardholderNumber;
	}

	public String getCardholderName() {
		return cardholderName;
	}

	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}

	public String getBasicSupplementaryCardIndicator() {
		return basicSupplementaryCardIndicator;
	}

	public void setBasicSupplementaryCardIndicator(String basicSupplementaryCardIndicator) {
		this.basicSupplementaryCardIndicator = basicSupplementaryCardIndicator;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getDescription1() {
		return description1;
	}

	public void setDescription1(String description1) {
		this.description1 = description1;
	}

	public String getDescription2() {
		return description2;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	public String getAmountSign() {
		return amountSign;
	}

	public void setAmountSign(String amountSign) {
		this.amountSign = amountSign;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Date getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}

	public Date getBalanceTransferValidityPeriodFrom() {
		return balanceTransferValidityPeriodFrom;
	}

	public void setBalanceTransferValidityPeriodFrom(Date balanceTransferValidityPeriodFrom) {
		this.balanceTransferValidityPeriodFrom = balanceTransferValidityPeriodFrom;
	}

	public Date getBalanceTransferValidityPeriodTo() {
		return balanceTransferValidityPeriodTo;
	}

	public void setBalanceTransferValidityPeriodTo(Date balanceTransferValidityPeriodTo) {
		this.balanceTransferValidityPeriodTo = balanceTransferValidityPeriodTo;
	}

	public String getCorporateInterestIndicator() {
		return corporateInterestIndicator;
	}

	public void setCorporateInterestIndicator(String corporateInterestIndicator) {
		this.corporateInterestIndicator = corporateInterestIndicator;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getTransactionSource() {
		return transactionSource;
	}

	public void setTransactionSource(String transactionSource) {
		this.transactionSource = transactionSource;
	}

	public String getRetailAcctNo() {
		return retailAcctNo;
	}

	public void setRetailAcctNo(String retailAcctNo) {
		this.retailAcctNo = retailAcctNo;
	}

	public String getDualCurrencyTransactionDescription() {
		return dualCurrencyTransactionDescription;
	}

	public void setDualCurrencyTransactionDescription(String dualCurrencyTransactionDescription) {
		this.dualCurrencyTransactionDescription = dualCurrencyTransactionDescription;
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

	public Long getAhId() {
		return ahId;
	}

	@Override
	public void setAhId(Long ahId) {
		this.ahId = ahId;
	}
}
