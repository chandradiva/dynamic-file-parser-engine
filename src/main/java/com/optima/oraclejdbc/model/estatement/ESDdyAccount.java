package com.optima.oraclejdbc.model.estatement;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName = "ES_DDY_ACCOUNT")
public class ESDdyAccount extends DDIModel implements Serializable {

	private static final long serialVersionUID = -5188995110534720546L;

	@NISPEntityId
	@NISPEntity(columnName = "ID")
	private Long id;
	
	@NISPEntity(columnName = "PERIODE")
	@Position(start = 0, length = 11)
	private String periode;
	
	@NISPEntity(columnName = "ACCOUNT_NUMBER")
	@Position(start = 11, length = 20)
	private String accountNumber;
	
	@NISPEntity(columnName = "ACCOUNT_TYPE")
	@Position(start = 31, length = 1)
	private String accountType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
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
}
