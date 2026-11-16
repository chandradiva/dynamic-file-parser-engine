package com.optima.oraclejdbc.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ConfigValues implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1280855503945505436L;
	/*
	 * All field name using BO_TBL_APP_PARAM key value
	 * Look up to BO_TBL_APP_PARAM table if you need field description
	 */
	
	private String msdr;
	private String mitp;
	private String mdrdp;
	private String mdrtp;
	private String mdjrdppp;
	private String msc;
	private String errt;
	private String arto;
	
	public ConfigValues(List<Map<String, Object>> values){
		for (Map<String, Object> map : values) {
			if( ((String)map.get("KEY")).equalsIgnoreCase("msdr") )
				msdr = (String)map.get("VALUE");
			else if( ((String)map.get("KEY")).equalsIgnoreCase("mitp") )
				mitp = (String)map.get("VALUE");
			else if( ((String)map.get("KEY")).equalsIgnoreCase("mdrdp") )
				mdrdp = (String)map.get("VALUE");
			else if( ((String)map.get("KEY")).equalsIgnoreCase("mdrtp") )
				mdrtp = (String)map.get("VALUE");
			else if( ((String)map.get("KEY")).equalsIgnoreCase("mdjrdppp") )
				mdjrdppp = (String)map.get("VALUE");
			else if( ((String)map.get("KEY")).equalsIgnoreCase("msc") )
				msc = (String)map.get("VALUE");
			else if( ((String)map.get("KEY")).equalsIgnoreCase("errt") )
				errt = (String)map.get("VALUE");
			else if( ((String)map.get("KEY")).equalsIgnoreCase("arto") )
				arto = (String)map.get("VALUE");
		}
	}
	
	public String getMsdr() {
		return msdr;
	}
	public void setMsdr(String msdr) {
		this.msdr = msdr;
	}
	public String getMitp() {
		return mitp;
	}
	public void setMitp(String mitp) {
		this.mitp = mitp;
	}
	public String getMdrdp() {
		return mdrdp;
	}
	public void setMdrdp(String mdrdp) {
		this.mdrdp = mdrdp;
	}
	public String getMdrtp() {
		return mdrtp;
	}
	public void setMdrtp(String mdrtp) {
		this.mdrtp = mdrtp;
	}
	public String getMdjrdppp() {
		return mdjrdppp;
	}
	public void setMdjrdppp(String mdjrdppp) {
		this.mdjrdppp = mdjrdppp;
	}
	public String getMsc() {
		return msc;
	}
	public void setMsc(String msc) {
		this.msc = msc;
	}

	public String getErrt() {
		return errt;
	}

	public void setErrt(String errt) {
		this.errt = errt;
	}

	public String getArto() {
		return arto;
	}

	public void setArto(String arto) {
		this.arto = arto;
	}
	
}
