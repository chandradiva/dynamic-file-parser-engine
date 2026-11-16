package com.optima.oraclejdbc.response.bancass;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntitySerialize;

public class TransactionSummaryResponse implements Serializable {

	private static final long serialVersionUID = -3899145656109393145L;
	
	@NISPEntitySerialize(field = "ID")
	private Long id;
	
	@NISPEntitySerialize(field = "NO_POLIS")
	private String noPolis;
	
	@NISPEntitySerialize(field = "NAMA_NASABAH")
	private String namaNasabah;
	
	@NISPEntitySerialize(field = "ALAMAT_NASABAH_1")
	private String alamatNasabah1;
	
	@NISPEntitySerialize(field = "ALAMAT_NASABAH_2")
	private String alamatNasabah2;
	
	@NISPEntitySerialize(field = "ALAMAT_NASABAH_3")
	private String alamatNasabah3;
	
	@NISPEntitySerialize(field = "ALAMAT_NASABAH_4")
	private String alamatNasabah4;
	
	@NISPEntitySerialize(field = "TGL_MULAI_POLIS")
	private String tglMulaiPolis;
	
	@NISPEntitySerialize(field = "MATA_UANG")
	private String mataUang;
	
	@NISPEntitySerialize(field = "PRODUK")
	private String produk;
	
	@NISPEntitySerialize(field = "JUMLAH_PREMI")
	private double jumlahPremi;
	
	@NISPEntitySerialize(field = "FREKUENSI_PEMBAYARAN")
	private String frekuensiPembayaran;
	
	@NISPEntitySerialize(field = "NAMA_TERTANGGUNG")
	private String namaTertanggung;
	
	@NISPEntitySerialize(field = "UANG_PERTANGGUNGAN")
	private double uangPertanggungan;
	
	@NISPEntitySerialize(field = "STATUS_POLIS")
	private String statusPolis;
	
	@NISPEntitySerialize(field = "JENIS_DANA_INVESTASI")
	private String jenisDanaInvestasi;
	
	@NISPEntitySerialize(field = "ALOKASI")
	private int alokasi;
	
	@NISPEntitySerialize(field = "TOTAL_UNIT")
	private double totalUnit;
	
	@NISPEntitySerialize(field = "HARGA_UNIT")
	private double hargaUnit;
	
	@NISPEntitySerialize(field = "TANGGAL_UNIT")
	private String tanggalUnit;
	
	@NISPEntitySerialize(field = "NILAI_UNIT")
	private double nilaiUnit;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNoPolis() {
		return noPolis;
	}
	public void setNoPolis(String noPolis) {
		this.noPolis = noPolis;
	}
	public String getNamaNasabah() {
		return namaNasabah;
	}
	public void setNamaNasabah(String namaNasabah) {
		this.namaNasabah = namaNasabah;
	}
	public String getAlamatNasabah1() {
		return alamatNasabah1;
	}
	public void setAlamatNasabah1(String alamatNasabah1) {
		this.alamatNasabah1 = alamatNasabah1;
	}
	public String getAlamatNasabah2() {
		return alamatNasabah2;
	}
	public void setAlamatNasabah2(String alamatNasabah2) {
		this.alamatNasabah2 = alamatNasabah2;
	}
	public String getAlamatNasabah3() {
		return alamatNasabah3;
	}
	public void setAlamatNasabah3(String alamatNasabah3) {
		this.alamatNasabah3 = alamatNasabah3;
	}
	public String getAlamatNasabah4() {
		return alamatNasabah4;
	}
	public void setAlamatNasabah4(String alamatNasabah4) {
		this.alamatNasabah4 = alamatNasabah4;
	}
	public String getTglMulaiPolis() {
		return tglMulaiPolis;
	}
	public void setTglMulaiPolis(String tglMulaiPolis) {
		this.tglMulaiPolis = tglMulaiPolis;
	}
	public String getMataUang() {
		return mataUang;
	}
	public void setMataUang(String mataUang) {
		this.mataUang = mataUang;
	}
	public String getProduk() {
		return produk;
	}
	public void setProduk(String produk) {
		this.produk = produk;
	}
	public double getJumlahPremi() {
		return jumlahPremi;
	}
	public void setJumlahPremi(double jumlahPremi) {
		this.jumlahPremi = jumlahPremi;
	}
	public String getFrekuensiPembayaran() {
		return frekuensiPembayaran;
	}
	public void setFrekuensiPembayaran(String frekuensiPembayaran) {
		this.frekuensiPembayaran = frekuensiPembayaran;
	}
	public String getNamaTertanggung() {
		return namaTertanggung;
	}
	public void setNamaTertanggung(String namaTertanggung) {
		this.namaTertanggung = namaTertanggung;
	}
	public double getUangPertanggungan() {
		return uangPertanggungan;
	}
	public void setUangPertanggungan(double uangPertanggungan) {
		this.uangPertanggungan = uangPertanggungan;
	}
	public String getStatusPolis() {
		return statusPolis;
	}
	public void setStatusPolis(String statusPolis) {
		this.statusPolis = statusPolis;
	}
	public String getJenisDanaInvestasi() {
		return jenisDanaInvestasi;
	}
	public void setJenisDanaInvestasi(String jenisDanaInvestasi) {
		this.jenisDanaInvestasi = jenisDanaInvestasi;
	}
	public int getAlokasi() {
		return alokasi;
	}
	public void setAlokasi(int alokasi) {
		this.alokasi = alokasi;
	}
	public double getTotalUnit() {
		return totalUnit;
	}
	public void setTotalUnit(double totalUnit) {
		this.totalUnit = totalUnit;
	}
	public double getHargaUnit() {
		return hargaUnit;
	}
	public void setHargaUnit(double hargaUnit) {
		this.hargaUnit = hargaUnit;
	}
	public String getTanggalUnit() {
		return tanggalUnit;
	}
	public void setTanggalUnit(String tanggalUnit) {
		this.tanggalUnit = tanggalUnit;
	}
	public double getNilaiUnit() {
		return nilaiUnit;
	}
	public void setNilaiUnit(double nilaiUnit) {
		this.nilaiUnit = nilaiUnit;
	}
}
