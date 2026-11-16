package com.optima.oraclejdbc.response.login;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ocbc.schemas.soa.accounts.service.wbst_admin_loginchannelbanking.RESConsumer_type0;
import com.optima.oraclejdbc.model.AccountList;
import com.optima.oraclejdbc.model.ConfigValues;
import com.optima.oraclejdbc.model.CreditCardInfo;
import com.optima.oraclejdbc.utils.CommonUtils;

public class LoginResponse implements Serializable{

	private static final long serialVersionUID = -8374574744649540535L;
	
	private String shortName;
	private String cifKey;
	private List<AccountList> accountList;
	private List<CreditCardInfo> cardList;
	private Integer readAgreement;
	private Integer failedLogin;
	@JsonFormat(pattern="MMMM dd, yyyy - hh:mm:ss aa", timezone="Asia/Jakarta")
	private Date lastLogin;
	private ConfigValues configValues;
	
	public Integer getReadAgreement() {
		return readAgreement;
	}

	public void setReadAgreement(Integer readAgreement) {
		this.readAgreement = readAgreement;
	}

	public LoginResponse(RESConsumer_type0 data){
		if( data.isShortNameSpecified() ){
			shortName = data.getShortName();
		}
		
		if( data.isCIFKeySpecified() ){
			cifKey = data.getCIFKey();
		}		
	}
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getCifKey() {
		return cifKey;
	}
	public void setCifKey(String cifKey) {
		this.cifKey = CommonUtils.normalizeCifKey(cifKey);
	}

	public List<AccountList> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<AccountList> accountList) {
		this.accountList = accountList;
	}

	public List<CreditCardInfo> getCardList() {
		return cardList;
	}

	public void setCardList(List<CreditCardInfo> cardList) {
		this.cardList = cardList;
	}

	public Integer getFailedLogin() {
		return failedLogin;
	}

	public void setFailedLogin(Integer failedLogin) {
		this.failedLogin = failedLogin;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public ConfigValues getConfigValues() {
		return configValues;
	}

	public void setConfigValues(ConfigValues configValues) {
		this.configValues = configValues;
	}	
	
	
}
