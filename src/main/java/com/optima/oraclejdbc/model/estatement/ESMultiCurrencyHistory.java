package com.optima.oraclejdbc.model.estatement;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntity;

@NISPEntity(tableName = "ES_MULTI_CURRENCY_HISTORY")
public class ESMultiCurrencyHistory extends ESMultiCurrency implements Serializable {

	private static final long serialVersionUID = 9084496859126426021L;

//	@NISPEntityId
//	@NISPEntity(columnName = "ID")
//	private Long id; //DDI236MTXS
//	
//	@NISPEntity(columnName = "ACCOUNT_NUMBER")
//	private String accountNumber;
//	
//	@NISPEntity(columnName = "KODE_CABANG_AMBIL")
//	private String kodeCabangAmbil;
//	
//	@NISPEntity(columnName = "NAMA_REKENING")
//	private String namaRekening;
//	
//	@NISPEntity(columnName = "ALAMAT_1")
//	private String alamat1;
//	
//	@NISPEntity(columnName = "ALAMAT_2")
//	private String alamat2;
//	
//	@NISPEntity(columnName = "ALAMAT_3")
//	private String alamat3;
//	
//	@NISPEntity(columnName = "ALAMAT_4")
//	private String alamat4;
//	
//	@NISPEntity(columnName = "TANGGAL_CETAK")
//	private Date tanggalCetak;
//	
//	@NISPEntity(columnName = "KODE_CABANG_REK")
//	private String kodeCabangRek;
//	
//	@NISPEntity(columnName = "NAMA_CABANG_REK")
//	private String namaCabangRek;
//	
//	@NISPEntity(columnName = "PRODUCT_TYPE_DESC")
//	private String productTypeDesc;
//	
//	@NISPEntity(columnName = "CURRENCY_TYPE")
//	private String currencyType;
//	
//	@NISPEntity(columnName = "HALAMAN")
//	private String halaman;
//	
//	@NISPEntity(columnName = "TANGGAL")
//	private Date tanggal;
//	
//	@NISPEntity(columnName = "TANGGAL_VALUTA")
//	private Date tanggalValuta;
//	
//	@NISPEntity(columnName = "URAIAN")
//	private String uraian;
//	
//	@NISPEntity(columnName = "AMOUNT_DEBIT")
//	private Double amountDebit;
//	
//	@NISPEntity(columnName = "AMOUNT_CREDIT")
//	private Double amountCredit;
//	
//	@NISPEntity(columnName = "SALDO")
//	private Double saldo;
//	
//	@NISPEntity(columnName = "SALDO_AWAL")
//	private Double saldoAwal;
//	
//	@NISPEntity(columnName = "TOTAL_TRANS_DR_BULAN")
//	private Double totalTransDrBulan;
//	
//	@NISPEntity(columnName = "TOTAL_TRANS_CR_BULAN")
//	private Double totalTransCrBulan;
//	
//	@NISPEntity(columnName = "STATEMENT_MESSAGE_1")
//	private String statementMessage1;
//	
//	@NISPEntity(columnName = "STATEMENT_MESSAGE_2")
//	private String statementMessage2;
//	
//	@NISPEntity(columnName = "STATEMENT_MESSAGE_3")
//	private String statementMessage3;
//	
//	@NISPEntity(columnName = "STATEMENT_MESSAGE_4")
//	private String statementMessage4;
//	
//	@NISPEntity(columnName = "STATEMENT_MESSAGE_5")
//	private String statementMessage5;
//	
//	@NISPEntity(columnName = "STATEMENT_MESSAGE_6")
//	private String statementMessage6;
//	
//	@NISPEntity(columnName = "STATEMENT_MESSAGE_7")
//	private String statementMessage7;
//	
//	@NISPEntity(columnName = "STATEMENT_MESSAGE_8")
//	private String statementMessage8;
//	
//	@NISPEntity(columnName = "MATA_UANG_RINGKASAN")
//	private String mataUangRingkasan;
//	
//	@NISPEntity(columnName = "KURS_VALAS_IDR")
//	private Double kursValasIDR;
//	
//	@NISPEntity(columnName = "SALDO_RINGKASAN")
//	private Double saldoRingkasan;
//	
//	@NISPEntity(columnName = "SALDO_IDR")
//	private Double saldoIDR;
//	
//	@NISPEntity(columnName = "TOTAL_SALDO_IDR")
//	private Double totalSaldoIDR;
//	
//	@NISPEntity(columnName = "CATATAN")
//	private String catatan;
//	
//	@NISPEntity(columnName = "TUNGGAKAN_BUNGA")
//	private Double tunggakanBunga;
//	
//	@NISPEntity(columnName = "TUNGGAKAN_DENDA")
//	private Double tunggakanDenda;
//	
//	@NISPEntity(columnName = "TUNGGAKAN_BIAYA_LAIN")
//	private Double tunggakanBiayaLain;
//	
//	@NISPEntity(columnName = "TOTAL_TUNGGAKAN")
//	private Double totalTunggakan;
//	
//	@NISPEntity(columnName = "POINT_BALANCE")
//	private Double pointBalance;
//	
//	@NISPEntity(columnName = "ELEC_ADDR_TYPE_DESC")	
//	private String elecAddrTypeDesc; //email
//	
//	@NISPEntity(columnName = "ELEC_ADDR_CODE")
//	private String elecAddrCode;
	
	@NISPEntity(columnName = "PERIODE")
	private String periode;
	
	@NISPEntity(columnName = "TYPE")
	private int type;
	
	@NISPEntity(columnName = "TGL_CETAK_STR")
	private String tglCetakStr;

//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getAccountNumber() {
//		return accountNumber;
//	}
//
//	public void setAccountNumber(String accountNumber) {
//		this.accountNumber = accountNumber;
//	}
//
//	public String getKodeCabangAmbil() {
//		return kodeCabangAmbil;
//	}
//
//	public void setKodeCabangAmbil(String kodeCabangAmbil) {
//		this.kodeCabangAmbil = kodeCabangAmbil;
//	}
//
//	public String getNamaRekening() {
//		return namaRekening;
//	}
//
//	public void setNamaRekening(String namaRekening) {
//		this.namaRekening = namaRekening;
//	}
//
//	public String getAlamat1() {
//		return alamat1;
//	}
//
//	public void setAlamat1(String alamat1) {
//		this.alamat1 = alamat1;
//	}
//
//	public String getAlamat2() {
//		return alamat2;
//	}
//
//	public void setAlamat2(String alamat2) {
//		this.alamat2 = alamat2;
//	}
//
//	public String getAlamat3() {
//		return alamat3;
//	}
//
//	public void setAlamat3(String alamat3) {
//		this.alamat3 = alamat3;
//	}
//
//	public String getAlamat4() {
//		return alamat4;
//	}
//
//	public void setAlamat4(String alamat4) {
//		this.alamat4 = alamat4;
//	}
//
//	public Date getTanggalCetak() {
//		return tanggalCetak;
//	}
//
//	public void setTanggalCetak(Date tanggalCetak) {
//		this.tanggalCetak = tanggalCetak;
//	}
//
//	public String getKodeCabangRek() {
//		return kodeCabangRek;
//	}
//
//	public void setKodeCabangRek(String kodeCabangRek) {
//		this.kodeCabangRek = kodeCabangRek;
//	}
//
//	public String getNamaCabangRek() {
//		return namaCabangRek;
//	}
//
//	public void setNamaCabangRek(String namaCabangRek) {
//		this.namaCabangRek = namaCabangRek;
//	}
//
//	public String getProductTypeDesc() {
//		return productTypeDesc;
//	}
//
//	public void setProductTypeDesc(String productTypeDesc) {
//		this.productTypeDesc = productTypeDesc;
//	}
//
//	public String getCurrencyType() {
//		return currencyType;
//	}
//
//	public void setCurrencyType(String currencyType) {
//		this.currencyType = currencyType;
//	}
//
//	public String getHalaman() {
//		return halaman;
//	}
//
//	public void setHalaman(String halaman) {
//		this.halaman = halaman;
//	}
//
//	public Date getTanggal() {
//		return tanggal;
//	}
//
//	public void setTanggal(Date tanggal) {
//		this.tanggal = tanggal;
//	}
//
//	public Date getTanggalValuta() {
//		return tanggalValuta;
//	}
//
//	public void setTanggalValuta(Date tanggalValuta) {
//		this.tanggalValuta = tanggalValuta;
//	}
//
//	public String getUraian() {
//		return uraian;
//	}
//
//	public void setUraian(String uraian) {
//		this.uraian = uraian;
//	}
//
//	public Double getAmountDebit() {
//		return amountDebit;
//	}
//
//	public void setAmountDebit(Double amountDebit) {
//		this.amountDebit = amountDebit;
//	}
//
//	public Double getAmountCredit() {
//		return amountCredit;
//	}
//
//	public void setAmountCredit(Double amountCredit) {
//		this.amountCredit = amountCredit;
//	}
//
//	public Double getSaldo() {
//		return saldo;
//	}
//
//	public void setSaldo(Double saldo) {
//		this.saldo = saldo;
//	}
//
//	public Double getSaldoAwal() {
//		return saldoAwal;
//	}
//
//	public void setSaldoAwal(Double saldoAwal) {
//		this.saldoAwal = saldoAwal;
//	}
//
//	public Double getTotalTransDrBulan() {
//		return totalTransDrBulan;
//	}
//
//	public void setTotalTransDrBulan(Double totalTransDrBulan) {
//		this.totalTransDrBulan = totalTransDrBulan;
//	}
//
//	public Double getTotalTransCrBulan() {
//		return totalTransCrBulan;
//	}
//
//	public void setTotalTransCrBulan(Double totalTransCrBulan) {
//		this.totalTransCrBulan = totalTransCrBulan;
//	}
//
//	public String getStatementMessage1() {
//		return statementMessage1;
//	}
//
//	public void setStatementMessage1(String statementMessage1) {
//		this.statementMessage1 = statementMessage1;
//	}
//
//	public String getStatementMessage2() {
//		return statementMessage2;
//	}
//
//	public void setStatementMessage2(String statementMessage2) {
//		this.statementMessage2 = statementMessage2;
//	}
//
//	public String getStatementMessage3() {
//		return statementMessage3;
//	}
//
//	public void setStatementMessage3(String statementMessage3) {
//		this.statementMessage3 = statementMessage3;
//	}
//
//	public String getStatementMessage4() {
//		return statementMessage4;
//	}
//
//	public void setStatementMessage4(String statementMessage4) {
//		this.statementMessage4 = statementMessage4;
//	}
//
//	public String getStatementMessage5() {
//		return statementMessage5;
//	}
//
//	public void setStatementMessage5(String statementMessage5) {
//		this.statementMessage5 = statementMessage5;
//	}
//
//	public String getStatementMessage6() {
//		return statementMessage6;
//	}
//
//	public void setStatementMessage6(String statementMessage6) {
//		this.statementMessage6 = statementMessage6;
//	}
//
//	public String getStatementMessage7() {
//		return statementMessage7;
//	}
//
//	public void setStatementMessage7(String statementMessage7) {
//		this.statementMessage7 = statementMessage7;
//	}
//
//	public String getStatementMessage8() {
//		return statementMessage8;
//	}
//
//	public void setStatementMessage8(String statementMessage8) {
//		this.statementMessage8 = statementMessage8;
//	}
//
//	public String getMataUangRingkasan() {
//		return mataUangRingkasan;
//	}
//
//	public void setMataUangRingkasan(String mataUangRingkasan) {
//		this.mataUangRingkasan = mataUangRingkasan;
//	}
//
//	public Double getKursValasIDR() {
//		return kursValasIDR;
//	}
//
//	public void setKursValasIDR(Double kursValasIDR) {
//		this.kursValasIDR = kursValasIDR;
//	}
//
//	public Double getSaldoRingkasan() {
//		return saldoRingkasan;
//	}
//
//	public void setSaldoRingkasan(Double saldoRingkasan) {
//		this.saldoRingkasan = saldoRingkasan;
//	}
//
//	public Double getSaldoIDR() {
//		return saldoIDR;
//	}
//
//	public void setSaldoIDR(Double saldoIDR) {
//		this.saldoIDR = saldoIDR;
//	}
//
//	public Double getTotalSaldoIDR() {
//		return totalSaldoIDR;
//	}
//
//	public void setTotalSaldoIDR(Double totalSaldoIDR) {
//		this.totalSaldoIDR = totalSaldoIDR;
//	}
//
//	public String getCatatan() {
//		return catatan;
//	}
//
//	public void setCatatan(String catatan) {
//		this.catatan = catatan;
//	}
//
//	public Double getTunggakanBunga() {
//		return tunggakanBunga;
//	}
//
//	public void setTunggakanBunga(Double tunggakanBunga) {
//		this.tunggakanBunga = tunggakanBunga;
//	}
//
//	public Double getTunggakanDenda() {
//		return tunggakanDenda;
//	}
//
//	public void setTunggakanDenda(Double tunggakanDenda) {
//		this.tunggakanDenda = tunggakanDenda;
//	}
//
//	public Double getTunggakanBiayaLain() {
//		return tunggakanBiayaLain;
//	}
//
//	public void setTunggakanBiayaLain(Double tunggakanBiayaLain) {
//		this.tunggakanBiayaLain = tunggakanBiayaLain;
//	}
//
//	public Double getTotalTunggakan() {
//		return totalTunggakan;
//	}
//
//	public void setTotalTunggakan(Double totalTunggakan) {
//		this.totalTunggakan = totalTunggakan;
//	}
//
//	public Double getPointBalance() {
//		return pointBalance;
//	}
//
//	public void setPointBalance(Double pointBalance) {
//		this.pointBalance = pointBalance;
//	}
//
//	public String getElecAddrTypeDesc() {
//		return elecAddrTypeDesc;
//	}
//
//	public void setElecAddrTypeDesc(String elecAddrTypeDesc) {
//		this.elecAddrTypeDesc = elecAddrTypeDesc;
//	}
//
//	public String getElecAddrCode() {
//		return elecAddrCode;
//	}
//
//	public void setElecAddrCode(String elecAddrCode) {
//		this.elecAddrCode = elecAddrCode;
//	}
	
	public ESMultiCurrencyHistory() {
		super();
	}
	
	public ESMultiCurrencyHistory(ESMultiCurrency mcy) {
		super();
		this.setAccountNumber(mcy.getAccountNumber());
		this.setKodeCabangAmbil(mcy.getKodeCabangAmbil());
		this.setNamaRekening(mcy.getNamaRekening());
		this.setAlamat1(mcy.getAlamat1());
		this.setAlamat2(mcy.getAlamat2());
		this.setAlamat3(mcy.getAlamat3());
		this.setAlamat4(mcy.getAlamat4());
		this.setTanggalCetak(mcy.getTanggalCetak());
		this.setKodeCabangRek(mcy.getKodeCabangRek());
		this.setNamaCabangRek(mcy.getNamaCabangRek());
		this.setProductTypeDesc(mcy.getProductTypeDesc());
		this.setCurrencyType(mcy.getCurrencyType());
		this.setHalaman(mcy.getHalaman());
		this.setTanggal(mcy.getTanggal());
		this.setTanggalValuta(mcy.getTanggalValuta());
		this.setUraian(mcy.getUraian());
		this.setAmountDebit(mcy.getAmountDebit());
		this.setAmountCredit(mcy.getAmountCredit());
		this.setSaldo(mcy.getSaldo());
		this.setSaldoAwal(mcy.getSaldoAwal());
		this.setTotalTransDrBulan(mcy.getTotalTransDrBulan());
		this.setTotalTransCrBulan(mcy.getTotalTransCrBulan());
		this.setStatementMessage1(mcy.getStatementMessage1());
		this.setStatementMessage2(mcy.getStatementMessage2());
		this.setStatementMessage3(mcy.getStatementMessage3());
		this.setStatementMessage4(mcy.getStatementMessage4());
		this.setStatementMessage5(mcy.getStatementMessage5());
		this.setStatementMessage6(mcy.getStatementMessage6());
		this.setStatementMessage7(mcy.getStatementMessage7());
		this.setStatementMessage8(mcy.getStatementMessage8());
		this.setMataUangRingkasan(mcy.getMataUangRingkasan());
		this.setKursValasIDR(mcy.getKursValasIDR());
		this.setSaldoRingkasan(mcy.getSaldoRingkasan());
		this.setSaldoIDR(mcy.getSaldoIDR());
		this.setTotalSaldoIDR(mcy.getTotalSaldoIDR());
		this.setCatatan(mcy.getCatatan());
		this.setTunggakanBunga(mcy.getTunggakanBunga());
		this.setTunggakanDenda(mcy.getTunggakanDenda());
		this.setTunggakanBiayaLain(mcy.getTunggakanBiayaLain());
		this.setTotalTunggakan(mcy.getTotalTunggakan());
		this.setPointBalance(mcy.getPointBalance());
		this.setElecAddrTypeDesc(mcy.getElecAddrTypeDesc());
		this.setElecAddrCode(mcy.getElecAddrCode());
	}
	
	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTglCetakStr() {
		return tglCetakStr;
	}

	public void setTglCetakStr(String tglCetakStr) {
		this.tglCetakStr = tglCetakStr;
	}
}
