package com.optima.oraclejdbc.model;

import java.io.Serializable;

import com.ocbc.schemas.soa.emf.creditcard.aggregates.CreditCardDetail_type0;
import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;

@NISPEntity(tableName="TBL_CREDIT_CARD_INFO")
public class CreditCardInfo implements Serializable {
	private static final long serialVersionUID = 6822785280703928976L;

	@NISPEntityId
	@NISPEntity(columnName="ID")
	private Long id;
	
	@NISPEntity(columnName="CUSTOMER_INFO_ID")
	private Long customerInfoId;
	
	@NISPEntity(columnName="CREDIT_CARD_NUMBER")
	private String creditCardNumber;
	
	@NISPEntity(columnName="PRODUCT_ID")
	private Long productId;
	
	@NISPEntity(columnName="ACCOUNT_NUMBER")
	private String accountNumber;

	private MappingProduct product;
	
	public CreditCardInfo(Long customerInfoId, String creditCardAccountNumber, CreditCardDetail_type0 creditCardDetail) {
		this.customerInfoId = customerInfoId;
		this.accountNumber = creditCardAccountNumber;
		creditCardNumber = creditCardDetail.getCreditCardNumber();
	}

	public CreditCardInfo(){}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerInfoId() {
		return customerInfoId;
	}

	public void setCustomerInfoId(Long customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public MappingProduct getProduct() {
		return product;
	}

	public void setProduct(MappingProduct product) {
		this.product = product;
	}
	
}
