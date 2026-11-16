package com.optima.oraclejdbc.requestinput;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.optima.oraclejdbc.utils.CommonUtils;

public class ByDateHistoryInput implements Serializable{

	private static final long serialVersionUID = 255085563611116350L;

	@NotNull(message="{general.notnull}")
	@Size.List({
		@Size(min=1, message="{general.notempty}"),
		@Size(max=23, message="{general.size.max}")
	})
	private String userId;
	
	@NotNull(message="{general.notnull}")
	@Size.List({
		@Size(min=1, message="{general.notempty}"),
		@Size(max=23, message="{general.size.max}")
	})
	private String cifKey;
	
	@NotNull(message="{general.notnull}")
	@Size.List({
		@Size(min=1, message="{general.notempty}"),
		@Size(max=23, message="{general.size.max}")
	})
	private String accountNumber;
	
	@NotNull(message="{general.notnull}")
	@Size.List({
		@Size(min=1, message="{general.notempty}"),
		@Size(max=23, message="{general.size.max}")
	})
	private String currency;
	
	@NotNull(message="{general.notnull}")
	@Size.List({
		@Size(min=10, message="{general.length}"),
		@Size(max=10, message="{general.length}")
	})
	private String start;
	
	@NotNull(message="{general.notnull}")
	@Size.List({
		@Size(min=10, message="{general.length}"),
		@Size(max=10, message="{general.length}")
	})
	private String end;
	
	@Size.List({
		@Size(min=1, message="{general.notempty}"),
		@Size(max=3, message="{general.size.max}")
	})
	private String page;
	
	@Size.List({
		@Size(min=1, message="{general.notempty}"),
		@Size(max=3, message="{general.size.max}")
	})
	private String countPerPage;
	
	private String sortBy;
	
	@Size.List({
		@Size(min=1, message="{general.notempty}"),
		@Size(max=4, message="{general.size.max}")
	})
	private String sortType;
	
	private String accountType;
	
	private String branchCode;
	
	private String searchKeyWord;
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getCountPerPage() {
		return countPerPage;
	}
	public void setCountPerPage(String countPerPage) {
		this.countPerPage = countPerPage;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCifKey() {
		return cifKey;
	}
	public void setCifKey(String cifKey) {
		this.cifKey = cifKey;
	}
	public String getSearchKeyWord() {
		return searchKeyWord;
	}
	public void setSearchKeyWord(String searchKeyWord) {
		this.searchKeyWord = searchKeyWord;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
}
