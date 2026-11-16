package com.optima.oraclejdbc.model.estatement;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName = "ES_ODTIER")
public class ESOdtier extends DDIModel implements Serializable {

	private static final long serialVersionUID = -1955537452900986001L;

	@NISPEntityId
	@NISPEntity(columnName = "ACCOUNT_NUMBER")
	@Position(start = 12, length = 19)
	private String accountNumber;
	
	@NISPEntity(columnName = "PERIOD")
	@Position(start = 0, length = 12)
	private String period;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
}
