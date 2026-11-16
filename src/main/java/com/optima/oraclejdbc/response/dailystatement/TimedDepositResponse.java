package com.optima.oraclejdbc.response.dailystatement;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailtimeddeposit.TDDetailInfo_type0;
import com.optima.oraclejdbc.model.MappingProduct;

public class TimedDepositResponse implements Serializable{

	private static final long serialVersionUID = -8581244680469263292L;
	private MappingProduct product;
	private String currency;
	private String accountNo;
	private String receiptNo;
	@JsonFormat(pattern="dd/MM/yyyy", timezone="Asia/Jakarta")
	private Date effectiveDate;
	@JsonFormat(pattern="dd/MM/yyyy", timezone="Asia/Jakarta")
	private Date maturityDate;
	private Double principal;
	private Double interestAmount;
	private Integer tenor;
	private String tenorTerm;
	private Double interest;
	private Integer ibFlag;
	
	public TimedDepositResponse(TDDetailInfo_type0 detail) {
		currency = detail.getCurrencyType();
		accountNo = detail.getAccountNo1();
		receiptNo = detail.getNoBilyet();
		effectiveDate = detail.getEffectiveDate();
		maturityDate = detail.getMaturityDate();
		principal = detail.getPrincipalAmount().doubleValue();
		interestAmount = detail.getInterestAvailAmount().doubleValue();
		tenor = detail.getTDTerm().intValue();
		String tdTermCode = detail.getTDTermCode();
		if( tdTermCode.equalsIgnoreCase("M") ){
			tenorTerm = "Bulan";
		}else{
			tenorTerm = "Tahun";
		}
		interest = detail.getInterestRate().doubleValue()*100;
		ibFlag = detail.getIBFlag();
	}
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Date getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}
	
	public MappingProduct getProduct() {
		return product;
	}
	public void setProduct(MappingProduct product) {
		this.product = product;
	}
	public Double getPrincipal() {
		return principal;
	}
	public void setPrincipal(Double principal) {
		this.principal = principal;
	}
	public Double getInterestAmount() {
		return interestAmount;
	}
	public void setInterestAmount(Double interestAmount) {
		this.interestAmount = interestAmount;
	}

	public Integer getTenor() {
		return tenor;
	}

	public void setTenor(Integer tenor) {
		this.tenor = tenor;
	}

	public String getTenorTerm() {
		return tenorTerm;
	}

	public void setTenorTerm(String tenorTerm) {
		this.tenorTerm = tenorTerm;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Integer getIbFlag() {
		return ibFlag;
	}

	public void setIbFlag(Integer ibFlag) {
		this.ibFlag = ibFlag;
	}
	
}
