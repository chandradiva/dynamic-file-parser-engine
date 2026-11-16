package com.optima.oraclejdbc.model;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName = "BANK_ONLINE")
public class BankOnline implements Serializable {

	private static final long serialVersionUID = -1703896407212573758L;

	@Position(start = 0, length = 6)
	@NISPEntity(columnName = "BANK_ID")
	@NISPEntityId
	private String bankId;
	
	@Position(start = 6, length = 50)
	@NISPEntity(columnName = "BANK_NAME")
	private String bankName;
	
	@Position(start = 56, length = 1)
	@NISPEntity(columnName = "MAINTENANCE_FLAG")
	private String maintenanceFlag;

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getMaintenanceFlag() {
		return maintenanceFlag;
	}

	public void setMaintenanceFlag(String maintenanceFlag) {
		this.maintenanceFlag = maintenanceFlag;
	}
}
