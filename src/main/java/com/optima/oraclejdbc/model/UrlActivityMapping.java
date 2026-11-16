package com.optima.oraclejdbc.model;

import java.io.Serializable;

import com.optima.oraclejdbc.annotations.NISPEntitySerialize;

public class UrlActivityMapping implements Serializable {

	private static final long serialVersionUID = 5416846525802007047L;

	@NISPEntitySerialize(field="ID")
	private Integer id;
	
	@NISPEntitySerialize(field="URL_PATH")
	private String urlPath;
	
	@NISPEntitySerialize(field="ACTIVITY")
	private String activity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}
}
