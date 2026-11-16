package com.optima.oraclejdbc.model.estatement;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntitySerialize;
import com.optima.oraclejdbc.utils.CommonUtils;

public class ViewESAccountInfo implements Serializable {

	private static final long serialVersionUID = -700662367281099484L;

	@NISPEntitySerialize(field = "CIF_NO")
	private String cifNo;
	
	@NISPEntitySerialize(field = "ACCOUNT_NUMBER")
	private String accountNumber;
	
	@NISPEntitySerialize(field = "ACCOUNT_TYPE")
	private String accountType;
	
	@NISPEntitySerialize(field = "EMAIL")
	private String email;
	
	@NISPEntitySerialize(field = "DOB")
	private Date dob;
	
	@NISPEntitySerialize(field = "PERIODE")
	private String periode;
	
	@NISPEntitySerialize(field = "BLOCK_HC")
	private Integer blockHc;
	
	@NISPEntitySerialize(field = "IS_MONTHLY")
	private Integer isMonthly;
	
	public String getCifNo() {
		return cifNo;
	}

	public void setCifNo(String cifNo) {
		this.cifNo = CommonUtils.normalizeCifKey(cifNo);
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
	}

	public Integer getBlockHc() {
		return blockHc;
	}

	public void setBlockHc(Integer blockHc) {
		this.blockHc = blockHc;
	}

	public Integer getIsMonthly() {
		return isMonthly;
	}

	public void setIsMonthly(Integer isMonthly) {
		this.isMonthly = isMonthly;
	}
}
