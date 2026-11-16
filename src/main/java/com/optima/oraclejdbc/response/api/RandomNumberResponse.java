package com.optima.oraclejdbc.response.api;

import java.io.Serializable;

import com.ocbc.schemas.soa.accounts.service.wbst_admin_generaterandomnumber.RESConsumer_type0;

public class RandomNumberResponse implements Serializable{
	private static final long serialVersionUID = 1776242188446658116L;
	private String randomNumber;
	private String randomId;
	
	public RandomNumberResponse(RESConsumer_type0 resConsumer) {
		randomNumber = resConsumer.getRandomNumber();
		randomId = resConsumer.getRandomID();
	}
	public RandomNumberResponse(String randomNumber, String randomId) {
		this.randomNumber = randomNumber;
		this.randomId = randomId;
	}
	public RandomNumberResponse(){}
	public String getRandomNumber() {
		return randomNumber;
	}
	public void setRandomNumber(String randomNumber) {
		this.randomNumber = randomNumber;
	}
	public String getRandomId() {
		return randomId;
	}
	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}
	
	
	
}
