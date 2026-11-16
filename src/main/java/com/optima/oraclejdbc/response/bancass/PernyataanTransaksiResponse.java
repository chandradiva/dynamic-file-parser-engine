package com.optima.oraclejdbc.response.bancass;

import java.io.Serializable;
import java.util.List;

public class PernyataanTransaksiResponse implements Serializable {

	private static final long serialVersionUID = 5431609925987213079L;
	private TransactionSummaryResponse sh;
	private List<DetailPremiResponse> listDp;
	private List<DetailBiayaResponse> listDb;
	private List<TransaksiLainResponse> listTl;
	private List<DisclaimerResponse> listDm;
	
	public TransactionSummaryResponse getSh() {
		return sh;
	}
	public void setSh(TransactionSummaryResponse sh) {
		this.sh = sh;
	}
	public List<DetailPremiResponse> getListDp() {
		return listDp;
	}
	public void setListDp(List<DetailPremiResponse> listDp) {
		this.listDp = listDp;
	}
	public List<DetailBiayaResponse> getListDb() {
		return listDb;
	}
	public void setListDb(List<DetailBiayaResponse> listDb) {
		this.listDb = listDb;
	}
	public List<TransaksiLainResponse> getListTl() {
		return listTl;
	}
	public void setListTl(List<TransaksiLainResponse> listTl) {
		this.listTl = listTl;
	}
	public List<DisclaimerResponse> getListDm() {
		return listDm;
	}
	public void setListDm(List<DisclaimerResponse> listDm) {
		this.listDm = listDm;
	}
}
