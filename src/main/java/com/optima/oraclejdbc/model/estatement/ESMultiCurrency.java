package com.optima.oraclejdbc.model.estatement;

import java.io.Serializable;
import java.util.Date;

import com.optima.oraclejdbc.annotations.NISPEntity;
import com.optima.oraclejdbc.annotations.NISPEntityDateFomat;
import com.optima.oraclejdbc.annotations.NISPEntityId;
import com.optima.oraclejdbc.annotations.NISPEntityNumberFormat;
import com.optima.oraclejdbc.annotations.NISPEntityReverse;
import com.optima.oraclejdbc.annotations.Position;

@NISPEntity(tableName = "ES_MULTI_CURRENCY")
public class ESMultiCurrency extends DDIModel implements Serializable {

	private static final long serialVersionUID = -9113785359599411348L;

	@NISPEntityId
	@NISPEntity(columnName = "ID")
	protected Long id; //DDI236MTXS
	
	@NISPEntity(columnName = "ACCOUNT_NUMBER")
	@Position(start = 0, length = 20)
	protected String accountNumber;
	
	@NISPEntity(columnName = "KODE_CABANG_AMBIL")
	@Position(start = 20, length = 6)
	protected String kodeCabangAmbil;
	
	@NISPEntity(columnName = "NAMA_REKENING")
	@Position(start = 26, length = 40)
	protected String namaRekening;
	
	@NISPEntity(columnName = "ALAMAT_1")
	@Position(start = 66, length = 40)
	protected String alamat1;
	
	@NISPEntity(columnName = "ALAMAT_2")
	@Position(start = 106, length = 40)
	protected String alamat2;
	
	@NISPEntity(columnName = "ALAMAT_3")
	@Position(start = 146, length = 40)
	protected String alamat3;
	
	@NISPEntity(columnName = "ALAMAT_4")
	@Position(start = 186, length = 40)
	protected String alamat4;
	
	@NISPEntity(columnName = "TANGGAL_CETAK")
	@NISPEntityDateFomat(format = "ddMMyy")
	@Position(start = 226, length = 8)
	protected Date tanggalCetak;
	
	@NISPEntity(columnName = "KODE_CABANG_REK")
	@Position(start = 234, length = 6)
	protected String kodeCabangRek;
	
	@NISPEntity(columnName = "NAMA_CABANG_REK")
	@Position(start = 240, length = 30)
	protected String namaCabangRek;
	
	@NISPEntity(columnName = "PRODUCT_TYPE_DESC")
	@Position(start = 270, length = 20)
	protected String productTypeDesc;
	
	@NISPEntity(columnName = "CURRENCY_TYPE")
	@Position(start = 290, length = 4)
	protected String currencyType;
	
	@NISPEntity(columnName = "HALAMAN")
	@Position(start = 294, length = 6)
	protected String halaman;
	
	@NISPEntity(columnName = "TANGGAL")
	@NISPEntityDateFomat(format = "ddMMyy")
	@Position(start = 300, length = 8)
	protected Date tanggal;
	
	@NISPEntity(columnName = "TANGGAL_VALUTA")
	@NISPEntityDateFomat(format = "ddMMyy")
	@Position(start = 308, length = 8)
	protected Date tanggalValuta;
	
	@NISPEntity(columnName = "URAIAN")
	@Position(start = 316, length = 150)
	protected String uraian;
	
	@NISPEntity(columnName = "AMOUNT_DEBIT")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 466, length = 19)
	protected Double amountDebit;
	
	@NISPEntity(columnName = "AMOUNT_CREDIT")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 485, length = 19)
	protected Double amountCredit;
	
	@NISPEntity(columnName = "SALDO")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 504, length = 21)
	protected Double saldo;
	
	@NISPEntity(columnName = "SALDO_AWAL")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 525, length = 21)
	protected Double saldoAwal;
	
	@NISPEntity(columnName = "TOTAL_TRANS_DR_BULAN")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 546, length = 21)
	protected Double totalTransDrBulan;
	
	@NISPEntity(columnName = "TOTAL_TRANS_CR_BULAN")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 567, length = 21)
	protected Double totalTransCrBulan;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_1")
	@Position(start = 588, length = 40)
	protected String statementMessage1;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_2")
	@Position(start = 628, length = 40)
	protected String statementMessage2;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_3")
	@Position(start = 668, length = 40)
	protected String statementMessage3;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_4")
	@Position(start = 708, length = 40)
	protected String statementMessage4;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_5")
	@Position(start = 748, length = 40)
	protected String statementMessage5;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_6")
	@Position(start = 788, length = 40)
	protected String statementMessage6;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_7")
	@Position(start = 828, length = 40)
	protected String statementMessage7;
	
	@NISPEntity(columnName = "STATEMENT_MESSAGE_8")
	@Position(start = 868, length = 40)
	protected String statementMessage8;
	
	@NISPEntity(columnName = "MATA_UANG_RINGKASAN")
	@Position(start = 908, length = 4)
	protected String mataUangRingkasan;
	
	@NISPEntity(columnName = "KURS_VALAS_IDR")
	@NISPEntityNumberFormat(scale = 7)
	@Position(start = 912, length = 15)
	protected Double kursValasIDR;
	
	@NISPEntity(columnName = "SALDO_RINGKASAN")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 927, length = 19)
	protected Double saldoRingkasan;
	
	@NISPEntity(columnName = "SALDO_IDR")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 946, length = 19)
	protected Double saldoIDR;
	
	@NISPEntity(columnName = "TOTAL_SALDO_IDR")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 965, length = 19)
	protected Double totalSaldoIDR;
	
	@NISPEntity(columnName = "CATATAN")
	@Position(start = 984, length = 150)
	protected String catatan;
	
	@NISPEntity(columnName = "TUNGGAKAN_BUNGA")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 1134, length = 19)
	protected Double tunggakanBunga;
	
	@NISPEntity(columnName = "TUNGGAKAN_DENDA")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 1153, length = 19)
	protected Double tunggakanDenda;
	
	@NISPEntity(columnName = "TUNGGAKAN_BIAYA_LAIN")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 1172, length = 19)
	protected Double tunggakanBiayaLain;
	
	@NISPEntity(columnName = "TOTAL_TUNGGAKAN")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 1191, length = 19)
	protected Double totalTunggakan;
	
	@NISPEntity(columnName = "POINT_BALANCE")
	@NISPEntityNumberFormat(scale = 2)
	@Position(start = 1210, length = 13)
	protected Double pointBalance;
	
	@NISPEntity(columnName = "ELEC_ADDR_TYPE_DESC")
	@NISPEntityReverse(include = false)
	@Position(start = 1223, length = 40)
	protected String elecAddrTypeDesc; //email
	
	@NISPEntity(columnName = "ELEC_ADDR_CODE")
	@NISPEntityReverse(include = false)
	@Position(start = 1263, length = 2)
	protected String elecAddrCode;
	
	@NISPEntity(columnName = "BLOCK_HC")
	private Integer blockHc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getKodeCabangAmbil() {
		return kodeCabangAmbil;
	}

	public void setKodeCabangAmbil(String kodeCabangAmbil) {
		this.kodeCabangAmbil = kodeCabangAmbil;
	}

	public String getNamaRekening() {
		return namaRekening;
	}

	public void setNamaRekening(String namaRekening) {
		this.namaRekening = namaRekening;
	}

	public String getAlamat1() {
		return alamat1;
	}

	public void setAlamat1(String alamat1) {
		this.alamat1 = alamat1;
	}

	public String getAlamat2() {
		return alamat2;
	}

	public void setAlamat2(String alamat2) {
		this.alamat2 = alamat2;
	}

	public String getAlamat3() {
		return alamat3;
	}

	public void setAlamat3(String alamat3) {
		this.alamat3 = alamat3;
	}

	public String getAlamat4() {
		return alamat4;
	}

	public void setAlamat4(String alamat4) {
		this.alamat4 = alamat4;
	}

	public Date getTanggalCetak() {
		return tanggalCetak;
	}

	public void setTanggalCetak(Date tanggalCetak) {
		this.tanggalCetak = tanggalCetak;
	}

	public String getKodeCabangRek() {
		return kodeCabangRek;
	}

	public void setKodeCabangRek(String kodeCabangRek) {
		this.kodeCabangRek = kodeCabangRek;
	}

	public String getNamaCabangRek() {
		return namaCabangRek;
	}

	public void setNamaCabangRek(String namaCabangRek) {
		this.namaCabangRek = namaCabangRek;
	}

	public String getProductTypeDesc() {
		return productTypeDesc;
	}

	public void setProductTypeDesc(String productTypeDesc) {
		this.productTypeDesc = productTypeDesc;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getHalaman() {
		return halaman;
	}

	public void setHalaman(String halaman) {
		this.halaman = halaman;
	}

	public Date getTanggal() {
		return tanggal;
	}

	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

	public Date getTanggalValuta() {
		return tanggalValuta;
	}

	public void setTanggalValuta(Date tanggalValuta) {
		this.tanggalValuta = tanggalValuta;
	}

	public String getUraian() {
		return uraian;
	}

	public void setUraian(String uraian) {
		this.uraian = uraian;
	}

	public Double getAmountDebit() {
		return amountDebit;
	}

	public void setAmountDebit(Double amountDebit) {
		this.amountDebit = amountDebit;
	}

	public Double getAmountCredit() {
		return amountCredit;
	}

	public void setAmountCredit(Double amountCredit) {
		this.amountCredit = amountCredit;
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

	public Double getTotalTransDrBulan() {
		return totalTransDrBulan;
	}

	public void setTotalTransDrBulan(Double totalTransDrBulan) {
		this.totalTransDrBulan = totalTransDrBulan;
	}

	public Double getTotalTransCrBulan() {
		return totalTransCrBulan;
	}

	public void setTotalTransCrBulan(Double totalTransCrBulan) {
		this.totalTransCrBulan = totalTransCrBulan;
	}

	public String getStatementMessage1() {
		return statementMessage1;
	}

	public void setStatementMessage1(String statementMessage1) {
		this.statementMessage1 = statementMessage1;
	}

	public String getStatementMessage2() {
		return statementMessage2;
	}

	public void setStatementMessage2(String statementMessage2) {
		this.statementMessage2 = statementMessage2;
	}

	public String getStatementMessage3() {
		return statementMessage3;
	}

	public void setStatementMessage3(String statementMessage3) {
		this.statementMessage3 = statementMessage3;
	}

	public String getStatementMessage4() {
		return statementMessage4;
	}

	public void setStatementMessage4(String statementMessage4) {
		this.statementMessage4 = statementMessage4;
	}

	public String getStatementMessage5() {
		return statementMessage5;
	}

	public void setStatementMessage5(String statementMessage5) {
		this.statementMessage5 = statementMessage5;
	}

	public String getStatementMessage6() {
		return statementMessage6;
	}

	public void setStatementMessage6(String statementMessage6) {
		this.statementMessage6 = statementMessage6;
	}

	public String getStatementMessage7() {
		return statementMessage7;
	}

	public void setStatementMessage7(String statementMessage7) {
		this.statementMessage7 = statementMessage7;
	}

	public String getStatementMessage8() {
		return statementMessage8;
	}

	public void setStatementMessage8(String statementMessage8) {
		this.statementMessage8 = statementMessage8;
	}

	public String getMataUangRingkasan() {
		return mataUangRingkasan;
	}

	public void setMataUangRingkasan(String mataUangRingkasan) {
		this.mataUangRingkasan = mataUangRingkasan;
	}

	public Double getKursValasIDR() {
		return kursValasIDR;
	}

	public void setKursValasIDR(Double kursValasIDR) {
		this.kursValasIDR = kursValasIDR;
	}

	public Double getSaldoRingkasan() {
		return saldoRingkasan;
	}

	public void setSaldoRingkasan(Double saldoRingkasan) {
		this.saldoRingkasan = saldoRingkasan;
	}

	public Double getSaldoIDR() {
		return saldoIDR;
	}

	public void setSaldoIDR(Double saldoIDR) {
		this.saldoIDR = saldoIDR;
	}

	public Double getTotalSaldoIDR() {
		return totalSaldoIDR;
	}

	public void setTotalSaldoIDR(Double totalSaldoIDR) {
		this.totalSaldoIDR = totalSaldoIDR;
	}

	public String getCatatan() {
		return catatan;
	}

	public void setCatatan(String catatan) {
		this.catatan = catatan;
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

	public Double getPointBalance() {
		return pointBalance;
	}

	public void setPointBalance(Double pointBalance) {
		this.pointBalance = pointBalance;
	}

	public String getElecAddrTypeDesc() {
		return elecAddrTypeDesc;
	}

	public void setElecAddrTypeDesc(String elecAddrTypeDesc) {
		this.elecAddrTypeDesc = elecAddrTypeDesc;
	}

	public String getElecAddrCode() {
		return elecAddrCode;
	}

	public void setElecAddrCode(String elecAddrCode) {
		this.elecAddrCode = elecAddrCode;
	}

	public Integer getBlockHc() {
		return blockHc;
	}

	public void setBlockHc(Integer blockHc) {
		this.blockHc = blockHc;
	}
}
