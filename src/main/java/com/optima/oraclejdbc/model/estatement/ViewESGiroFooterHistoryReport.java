package com.optima.oraclejdbc.model.estatement;

import com.optima.oraclejdbc.annotations.NISPEntitySerialize;

public class ViewESGiroFooterHistoryReport {

	@NISPEntitySerialize(field = "ACCOUNT_NUMBER")
	private String accountNumber;
	
	@NISPEntitySerialize(field = "PERIODE")
	private String periode;
	
	@NISPEntitySerialize(field = "DEBET_N")
	private Double debet;
	
	@NISPEntitySerialize(field = "CREDIT_N")
	private Double credit;
	
	@NISPEntitySerialize(field = "SALDO_N")
	private Double saldo;
	
	@NISPEntitySerialize(field = "SALDO_AWAL_N")
	private Double saldoAwal;
	
	@NISPEntitySerialize(field = "NO_FASILITAS")
	private String noFasilitas;
	
	@NISPEntitySerialize(field = "PLAFOND_N")
	private Double plafond;
	
	@NISPEntitySerialize(field = "PERSEN_BUNGA_N")
	private Double persenBunga;
	
	@NISPEntitySerialize(field = "TANGGAL_AWAL")
	private String tanggalAwal;
	
	@NISPEntitySerialize(field = "TANGGAL_AKHIR")
	private String tanggalAkhir;
	
	@NISPEntitySerialize(field = "TUNGGAKAN_BUNGA_N")
	private Double tunggakanBunga;
	
	@NISPEntitySerialize(field = "TUNGGAKAN_DENDA_N")
	private Double tunggakanDenda;
	
	@NISPEntitySerialize(field = "TUNGGAKAN_BIAYA_LAIN_N")
	private Double tunggakanBiayaLain;
	
	@NISPEntitySerialize(field = "TOTAL_TUNGGAKAN_N")
	private Double totalTunggakan;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getDebet() {
		return debet;
	}

	public void setDebet(Double debet) {
		this.debet = debet;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Double getSaldoAwal() {
		return saldoAwal;
	}

	public void setSaldoAwal(Double saldoAwal) {
		this.saldoAwal = saldoAwal;
	}

	public String getNoFasilitas() {
		return noFasilitas;
	}

	public void setNoFasilitas(String noFasilitas) {
		this.noFasilitas = noFasilitas;
	}

	public Double getPlafond() {
		return plafond;
	}

	public void setPlafond(Double plafond) {
		this.plafond = plafond;
	}

	public Double getPersenBunga() {
		return persenBunga;
	}

	public void setPersenBunga(Double persenBunga) {
		this.persenBunga = persenBunga;
	}

	public String getTanggalAwal() {
		return tanggalAwal;
	}

	public void setTanggalAwal(String tanggalAwal) {
		this.tanggalAwal = tanggalAwal;
	}

	public String getTanggalAkhir() {
		return tanggalAkhir;
	}

	public void setTanggalAkhir(String tanggalAkhir) {
		this.tanggalAkhir = tanggalAkhir;
	}

	public Double getTunggakanBunga() {
		return tunggakanBunga;
	}

	public void setTunggakanBunga(Double tunggakanBunga) {
		this.tunggakanBunga = tunggakanBunga;
	}

	public Double getTunggakanDenda() {
		return tunggakanDenda;
	}

	public void setTunggakanDenda(Double tunggakanDenda) {
		this.tunggakanDenda = tunggakanDenda;
	}

	public Double getTunggakanBiayaLain() {
		return tunggakanBiayaLain;
	}

	public void setTunggakanBiayaLain(Double tunggakanBiayaLain) {
		this.tunggakanBiayaLain = tunggakanBiayaLain;
	}

	public Double getTotalTunggakan() {
		return totalTunggakan;
	}

	public void setTotalTunggakan(Double totalTunggakan) {
		this.totalTunggakan = totalTunggakan;
	}

	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
	}
}
