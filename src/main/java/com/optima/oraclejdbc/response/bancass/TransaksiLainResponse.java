package com.optima.oraclejdbc.response.bancass;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.optima.oraclejdbc.annotations.NISPEntitySerialize;

public class TransaksiLainResponse implements Serializable {

	private static final long serialVersionUID = 7735976619836596310L;

	@NISPEntitySerialize(field = "ID")
	private Long id;
	
	@NISPEntitySerialize(field = "DETAIL_ID")
	private int detailId;
	
	@NISPEntitySerialize(field = "TRANSAKSI_LAIN")
	private String transaksiLain;
	
	@NISPEntitySerialize(field = "TANGGAL")
	private String tanggal;
	
	@NISPEntitySerialize(field = "JUMLAH_DANA")
	private double jumlahDana;
	
	@NISPEntitySerialize(field = "HARGA_UNIT")
	private double hargaUnit;
	
	@NISPEntitySerialize(field = "TOTAL_UNIT")
	private double totalUnit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getDetailId() {
		return detailId;
	}

	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}

	public String getTransaksiLain() {
		return transaksiLain;
	}

	public void setTransaksiLain(String transaksiLain) {
		this.transaksiLain = transaksiLain;
	}

	public String getTanggal() {
		return tanggal;
	}

	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
	}

	public double getJumlahDana() {
		return jumlahDana;
	}

	public void setJumlahDana(double jumlahDana) {
		this.jumlahDana = jumlahDana;
	}

	public double getHargaUnit() {
		return hargaUnit;
	}

	public void setHargaUnit(double hargaUnit) {
		this.hargaUnit = hargaUnit;
	}

	public double getTotalUnit() {
		return totalUnit;
	}

	public void setTotalUnit(double totalUnit) {
		this.totalUnit = totalUnit;
	}
}
