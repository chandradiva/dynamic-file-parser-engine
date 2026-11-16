package com.optima.oraclejdbc.model.estatement;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntity;

@NISPEntity(tableName = "ES_MCB_HISTORY")
public class ESMCBHistory extends ESMCBilingual implements Serializable {

	private static final long serialVersionUID = 4839761457234490206L;

	@NISPEntity(columnName = "PERIODE")
	private String periode;
	
	@NISPEntity(columnName = "TYPE")
	private int type;
	
	@NISPEntity(columnName = "TGL_CETAK_STR")
	private String tglCetakStr;
	
	public ESMCBHistory() {
		super();
	}
	
	public ESMCBHistory(ESMCBilingual mcb) {
		super();
		this.setNoRekening(mcb.getNoRekening());
		this.setKodeCabangAmbil(mcb.getKodeCabangAmbil());
		this.setNamaRekening(mcb.getNamaRekening());
		this.setAlamat1(mcb.getAlamat1());
		this.setAlamat2(mcb.getAlamat2());
		this.setAlamat3(mcb.getAlamat3());
		this.setAlamat4(mcb.getAlamat4());
		this.setTanggalCetak(mcb.getTanggalCetak());
		this.setKodeCabangRekening(mcb.getKodeCabangRekening());
		this.setNamaCabangRekening(mcb.getNamaCabangRekening());
		this.setProductTypeDesc(mcb.getProductTypeDesc());
		this.setMataUang(mcb.getMataUang());
		this.setHalaman(mcb.getHalaman());
		this.setTanggal(mcb.getTanggal());
		this.setTanggalValuta(mcb.getTanggalValuta());
		this.setUraian(mcb.getUraian());
		this.setDebet(mcb.getDebet());
		this.setKredit(mcb.getKredit());
		this.setSaldo(mcb.getSaldo());
		this.setSaldoAwal(mcb.getSaldoAwal());
		this.setTotalTransDrBulan(mcb.getTotalTransDrBulan());
		this.setTotalTransCrBulan(mcb.getTotalTransCrBulan());
		this.setStatementMessage1(mcb.getStatementMessage1());
		this.setStatementMessage2(mcb.getStatementMessage2());
		this.setStatementMessage3(mcb.getStatementMessage3());
		this.setStatementMessage4(mcb.getStatementMessage4());
		this.setStatementMessage5(mcb.getStatementMessage5());
		this.setStatementMessage6(mcb.getStatementMessage6());
		this.setStatementMessage7(mcb.getStatementMessage7());
		this.setStatementMessage8(mcb.getStatementMessage8());
		this.setNote(mcb.getNote());
		this.setMataUangRingkasan(mcb.getMataUangRingkasan());
		this.setKursValasIDR(mcb.getKursValasIDR());
		this.setSaldoRingkasan(mcb.getSaldoRingkasan());
		this.setSaldoIDR(mcb.getSaldoIDR());
		this.setTotalSaldoIDR(mcb.getTotalSaldoIDR());
		this.setCatatan(mcb.getCatatan());
		this.setTanggalTransaksiOd(mcb.getTanggalTransaksiOd());
		this.setNoFasilitas(mcb.getNoFasilitas());
		this.setPersenBunga(mcb.getPersenBunga());
		this.setTanggalPerjanjianOd(mcb.getTanggalPerjanjianOd());
		this.setTanggalAwalOd(mcb.getTanggalAwalOd());
		this.setTanggalAkhirOd(mcb.getTanggalAkhirOd());
		this.setPlafond(mcb.getPlafond());
		this.setKelonggaranTarik(mcb.getKelonggaranTarik());
		this.setPointBalance(mcb.getPointBalance());
		this.setTunggakanBunga(mcb.getTunggakanBunga());
		this.setTunggakanDenda(mcb.getTunggakanDenda());
		this.setTunggakanBiayaLain(mcb.getTunggakanBiayaLain());
		this.setTotalTunggakan(mcb.getTotalTunggakan());
		this.setElectronicAddressDesc(mcb.getElectronicAddressDesc());
		this.setElectronicAddressCode(mcb.getElectronicAddressCode());
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
