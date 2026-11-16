package com.optima.oraclejdbc.model.estatement;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;
import com.optima.oraclejdbc.utils.CommonUtils;

@NISPEntity(tableName = "ES_DDMAST")
public class ESDdmast extends DDIModel implements Serializable {

	private static final long serialVersionUID = -3620491349661788096L;

	@NISPEntityId
	@NISPEntity(columnName = "CIF_NO")
	@Position(start = 0, length = 20)
	private String cifNo;
	
	@NISPEntityId
	@NISPEntity(columnName = "ACCOUNT_NUMBER")
	@Position(start = 20, length = 20)
	private String accountNumber;
	
	@NISPEntity(columnName = "ACCOUNT_TYPE")
	@Position(start = 40, length = 1)
	private String accountType;
	
	@NISPEntity(columnName = "CURRENCY_TYPE")
	@Position(start = 41, length = 4)
	private String currencyType;
	
	@NISPEntity(columnName = "ACCOUNT_GROUP_NUMBER")
	@Position(start = 45, length = 20)
	private String accountGroupNumber;

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

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getAccountGroupNumber() {
		return accountGroupNumber;
	}

	public void setAccountGroupNumber(String accountGroupNumber) {
		this.accountGroupNumber = accountGroupNumber;
	}
}
