package com.optima.oraclejdbc.model.estatement;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntitySerialize;

public class ViewESMultiCurrencyKurs implements Serializable {

	private static final long serialVersionUID = -9047522678972822001L;

	@NISPEntitySerialize(field = "CURRENCY")
	private String currency;
	
	@NISPEntitySerialize(field = "KURS_VALAS_IDR")
	private String kursValasIdr;
	
	@NISPEntitySerialize(field = "SALDO")
	private String saldo;
	
	@NISPEntitySerialize(field = "SALDO_IDR")
	private String saldoIdr;
	
	@NISPEntitySerialize(field = "SALDO_IDR_N")
	private Double saldoIdrN;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getKursValasIdr() {
		return kursValasIdr;
	}

	public void setKursValasIdr(String kursValasIdr) {
		this.kursValasIdr = kursValasIdr;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public String getSaldoIdr() {
		return saldoIdr;
	}

	public void setSaldoIdr(String saldoIdr) {
		this.saldoIdr = saldoIdr;
	}

	public Double getSaldoIdrN() {
		return saldoIdrN;
	}

	public void setSaldoIdrN(Double saldoIdrN) {
		this.saldoIdrN = saldoIdrN;
	}
}
