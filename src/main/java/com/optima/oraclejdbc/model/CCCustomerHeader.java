package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityDateFomat;
import com.optima.oraclejdbc.annotations.NISPEntityDefaultValue;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName="CC_CUSTOMER_HEADER")
public class CCCustomerHeader extends CCParentModel implements CCChildModel, Serializable {

	private static final long serialVersionUID = 130752351940683192L;

	@NISPEntity(columnName="ID")
	@NISPEntityId
	private Long id;
	
	@NISPEntity(columnName="CC_STATEMENT_ID")
	private Long ccStatementId;
	
	@NISPEntity(columnName="RECORD_TYPE")
	@Position(start = 0, length = 2)
	private String recordType; //CH
	
	@NISPEntity(columnName="SEQUENCE_NUMBER")
	@Position(start = 2, length = 10)
	private String sequenceNumber;
	
	@NISPEntity(columnName="CUSTOMER_NUMBER")
	@Position(start = 12, length = 23)
	private String customerNumber;
	
	@NISPEntity(columnName="CUSTOMER_FULLNAME")
	@Position(start = 35, length = 50)
	private String customerFullname;
	
	@NISPEntity(columnName="COMPANY_NAME")
	@Position(start = 85, length = 50)
	private String companyName;
	
	@NISPEntity(columnName="MAILING_ADDRESS_LINE_1")
	@Position(start = 135, length = 40)
	private String mailingAddressLine1;
	
	@NISPEntity(columnName="MAILING_ADDRESS_LINE_2")
	@Position(start = 175, length = 40)
	private String mailingAddressLine2;
	
	@NISPEntity(columnName="MAILING_ADDRESS_LINE_3")
	@Position(start = 215, length = 40)
	private String mailingAddressLine3;
	
	@NISPEntity(columnName="MAILING_ADDRESS_LINE_4")
	@Position(start = 255, length = 40)
	private String mailingAddressLine4;
	
	@NISPEntity(columnName="MAILING_ADDRESS_POSTAL_CODE")
	@Position(start = 295, length = 9)
	private String mailingAddressPostalCode;
	
	@NISPEntity(columnName="GIRO_INDICATOR")
	@Position(start = 304, length = 3)
	private String giroIndicator;
	
	@NISPEntity(columnName="GIRO_ACCOUNT_NUMBER")
	@Position(start = 307, length = 19)
	private String giroAccountNumber;
	
	@NISPEntity(columnName="STATEMENT_DATE")
	@NISPEntityDateFomat(format="ddMMyyyy")
	@Position(start = 326, length = 8)
	private Date statementDate;
	
	@NISPEntity(columnName="PAYMENT_DUE_DATE")
	@NISPEntityDateFomat(format="ddMMyyyy")
	@Position(start = 334, length = 8)
	private Date paymentDueDate;
	
	@NISPEntity(columnName="CREDIT_LIMIT")
	@Position(start = 342, length = 11)
	private String creditLimit;
	
	@NISPEntity(columnName="AVAILABLE_LIMIT")
	@Position(start = 353, length = 14)
	private String availableLimit;
	
	@NISPEntity(columnName="TOTAL_OUTSTANDING_BALANCE_SIGN")
	@NISPEntityDefaultValue(defaultValue = "+-")
	@Position(start = 367, length = 1)
	private String totalOutstandingBalanceSign;
	
	@NISPEntity(columnName="TOTAL_OUTSTANDING_BALANCE")
	@Position(start = 368, length = 14)
	private String totalOutstandingBalance;
	
	@NISPEntity(columnName="TOTAL_MINIMUM_PAYMENT")
	@Position(start = 382, length = 14)
	private String totalMinimumPayment;
	
	@NISPEntity(columnName="TEMPORARY_LIMIT_INCREASED")
	@Position(start = 396, length = 12)
	private String temporaryLimitIncreased;
	
	@NISPEntity(columnName="BILLING_TYPE")
	@NISPEntityDefaultValue(defaultValue = "IG")
	@Position(start = 408, length = 1)
	private String billingType;
	
	@NISPEntity(columnName="STATEMENT_HOLD_CODE")
	@NISPEntityDefaultValue(defaultValue = "YN")
	@Position(start = 409, length = 1)
	private String statementHoldCode;
	
	@NISPEntity(columnName="TERMINATED_CARD_STATUS")
	@NISPEntityDefaultValue(defaultValue = "YN")
	@Position(start = 410, length = 1)
	private String terminatedCardStatus;
	
	@NISPEntity(columnName="COMBINED_STATEMENT_INDICATOR")
	@Position(start = 411, length = 1)
	private String combinedStatementIndicator;
	
	@NISPEntity(columnName="CIF_NAME")
	@Position(start = 412, length = 40)
	private String cifName;
	
	@NISPEntity(columnName="AFFINITY_NAME")
	@Position(start = 452, length = 40)
	private String affinityName;
	
	@NISPEntity(columnName="DELINQUENT_CODE")
	@Position(start = 492, length = 2)
	private String delinquentCode;
	
	@NISPEntity(columnName="DATE_OF_BIRTH")
	@NISPEntityDateFomat(format="ddMMyyyy")
	@Position(start = 494, length = 8)
	private Date dateOfBirth;
	
	@NISPEntity(columnName="PRODUCT_CODE")
	@Position(start = 502, length = 6)
	private String productCode;
	
	@NISPEntity(columnName="GROUP_ACCOUNT_NUMBER")
	@Position(start = 508, length = 19)
	private String groupAccountNumber;
	
	@NISPEntity(columnName="CORPORATE_ACCOUNT_NAME")
	@Position(start = 527, length = 19)
	private String corporateAccountNumber;
	
	@NISPEntity(columnName="ADDRESS_TYPE_INDICATOR")
	@NISPEntityDefaultValue(defaultValue = "FL")
	@Position(start = 546, length = 1)
	private String addressTypeIndicator;
	
	@NISPEntity(columnName="PRODUCT_DESCRIPTION")
	@Position(start = 547, length = 25)
	private String productDescription;
	
	@NISPEntity(columnName="FORM_TYPE")
	@Position(start = 572, length = 3)
	private String formType;
	
	@NISPEntity(columnName="CO_NAME")
	@Position(start = 575, length = 50)
	private String coName;
	
	@NISPEntity(columnName="MAILING_ADDRESS_CITY")
	@Position(start = 625, length = 25)
	private String mailingAddressCity;
	
	@NISPEntity(columnName="SKIP_PAYMENT_INDICATOR")
	@Position(start = 650, length = 1)
	private String skipPaymentIndicator;
	
	@NISPEntity(columnName = "CAR_CODE")
	@Position(start = 651, length = 25)
	private String carCode;
	
	@NISPEntity(columnName="EMAIL_ADDRESS")
	@Position(start = 676, length = 59, variable=true) //old value 40
	private String emailAddress;
	
//	@Position(start = 612, length = 875)
//	private String filler;
	
	@NISPEntity(columnName = "CREATED_DATE", create = true)
	private Date createdDate;
	
	@NISPEntity(columnName = "UPDATED_DATE", update = true)
	private Date updatedDate;

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

	public String getCustomerFullname() {
		return customerFullname;
	}

	public void setCustomerFullname(String customerFullname) {
		this.customerFullname = customerFullname;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMailingAddressLine1() {
		return mailingAddressLine1;
	}

	public void setMailingAddressLine1(String mailingAddressLine1) {
		this.mailingAddressLine1 = mailingAddressLine1;
	}

	public String getMailingAddressLine2() {
		return mailingAddressLine2;
	}

	public void setMailingAddressLine2(String mailingAddressLine2) {
		this.mailingAddressLine2 = mailingAddressLine2;
	}

	public String getMailingAddressLine3() {
		return mailingAddressLine3;
	}

	public void setMailingAddressLine3(String mailingAddressLine3) {
		this.mailingAddressLine3 = mailingAddressLine3;
	}

	public String getMailingAddressLine4() {
		return mailingAddressLine4;
	}

	public void setMailingAddressLine4(String mailingAddressLine4) {
		this.mailingAddressLine4 = mailingAddressLine4;
	}

	public String getMailingAddressPostalCode() {
		return mailingAddressPostalCode;
	}

	public void setMailingAddressPostalCode(String mailingAddressPostalCode) {
		this.mailingAddressPostalCode = mailingAddressPostalCode;
	}

	public String getGiroIndicator() {
		return giroIndicator;
	}

	public void setGiroIndicator(String giroIndicator) {
		this.giroIndicator = giroIndicator;
	}

	public String getGiroAccountNumber() {
		return giroAccountNumber;
	}

	public void setGiroAccountNumber(String giroAccountNumber) {
		this.giroAccountNumber = giroAccountNumber;
	}

	public Date getStatementDate() {
		return statementDate;
	}

	public void setStatementDate(Date statementDate) {
		this.statementDate = statementDate;
	}

	public Date getPaymentDueDate() {
		return paymentDueDate;
	}

	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	public String getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getAvailableLimit() {
		return availableLimit;
	}

	public void setAvailableLimit(String availableLimit) {
		this.availableLimit = availableLimit;
	}

	public String getTotalOutstandingBalanceSign() {
		return totalOutstandingBalanceSign;
	}

	public void setTotalOutstandingBalanceSign(String totalOutstandingBalanceSign) {
		this.totalOutstandingBalanceSign = totalOutstandingBalanceSign;
	}

	public String getTotalOutstandingBalance() {
		return totalOutstandingBalance;
	}

	public void setTotalOutstandingBalance(String totalOutstandingBalance) {
		this.totalOutstandingBalance = totalOutstandingBalance;
	}

	public String getTotalMinimumPayment() {
		return totalMinimumPayment;
	}

	public void setTotalMinimumPayment(String totalMinimumPayment) {
		this.totalMinimumPayment = totalMinimumPayment;
	}

	public String getTemporaryLimitIncreased() {
		return temporaryLimitIncreased;
	}

	public void setTemporaryLimitIncreased(String temporaryLimitIncreased) {
		this.temporaryLimitIncreased = temporaryLimitIncreased;
	}

	public String getBillingType() {
		return billingType;
	}

	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	public String getStatementHoldCode() {
		return statementHoldCode;
	}

	public void setStatementHoldCode(String statementHoldCode) {
		this.statementHoldCode = statementHoldCode;
	}

	public String getTerminatedCardStatus() {
		return terminatedCardStatus;
	}

	public void setTerminatedCardStatus(String terminatedCardStatus) {
		this.terminatedCardStatus = terminatedCardStatus;
	}

	public String getCombinedStatementIndicator() {
		return combinedStatementIndicator;
	}

	public void setCombinedStatementIndicator(String combinedStatementIndicator) {
		this.combinedStatementIndicator = combinedStatementIndicator;
	}

	public String getCifName() {
		return cifName;
	}

	public void setCifName(String cifName) {
		this.cifName = cifName;
	}

	public String getAffinityName() {
		return affinityName;
	}

	public void setAffinityName(String affinityName) {
		this.affinityName = affinityName;
	}

	public String getDelinquentCode() {
		return delinquentCode;
	}

	public void setDelinquentCode(String delinquentCode) {
		this.delinquentCode = delinquentCode;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getGroupAccountNumber() {
		return groupAccountNumber;
	}

	public void setGroupAccountNumber(String groupAccountNumber) {
		this.groupAccountNumber = groupAccountNumber;
	}

	public String getCorporateAccountNumber() {
		return corporateAccountNumber;
	}

	public void setCorporateAccountNumber(String corporateAccountNumber) {
		this.corporateAccountNumber = corporateAccountNumber;
	}

	public String getAddressTypeIndicator() {
		return addressTypeIndicator;
	}

	public void setAddressTypeIndicator(String addressTypeIndicator) {
		this.addressTypeIndicator = addressTypeIndicator;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

//	public String getFiller() {
//		return filler;
//	}
//
//	public void setFiller(String filler) {
//		this.filler = filler;
//	}

	public String getMailingAddressCity() {
		return mailingAddressCity;
	}

	public void setMailingAddressCity(String mailingAddressCity) {
		this.mailingAddressCity = mailingAddressCity;
	}

	public String getSkipPaymentIndicator() {
		return skipPaymentIndicator;
	}

	public void setSkipPaymentIndicator(String skipPaymentIndicator) {
		this.skipPaymentIndicator = skipPaymentIndicator;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
}
