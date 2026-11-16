package com.optima.oraclejdbc.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.daoimpl.BancassDao;
import com.optima.oraclejdbc.daoimpl.MappingProductDao;
import com.optima.oraclejdbc.model.MappingProduct;
import com.optima.oraclejdbc.response.bancass.BancassMiniResponse;
import com.optima.oraclejdbc.response.bancass.DetailBiayaResponse;
import com.optima.oraclejdbc.response.bancass.DetailPremiResponse;
import com.optima.oraclejdbc.response.bancass.DisclaimerResponse;
import com.optima.oraclejdbc.response.bancass.TransactionSummaryResponse;
import com.optima.oraclejdbc.response.bancass.TransaksiLainResponse;


@Service
public class BancassService {
	
	@Autowired
	BancassDao dao;
	
	@Autowired
	MappingProductDao mappingProductDao;
	
	public List<BancassMiniResponse> getMiniStatement(String batchId, Date startDate, Date endDate, 
			Integer startRow, Integer rowCount) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		return dao.getMiniStatement(batchId, startDate, endDate, startRow, rowCount);
	}
	
	public List<BancassMiniResponse> getMiniStatement(String spajNo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		Calendar now = Calendar.getInstance();
		Calendar threeMonthAgo = Calendar.getInstance();
		threeMonthAgo.add(Calendar.MONTH, -3);
		return dao.getMiniStatement(spajNo, threeMonthAgo.getTime(), now.getTime(), null, 10);
	}

	public List<BancassMiniResponse> getByDateStatement(String accountNumber, Date startDate, Date endDate, Integer page,
			Integer countPerPage, String sortBy, String sortType) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String realSortBy = getRealSortBy(sortBy);
		return dao.getByDateStatement(accountNumber, startDate, endDate, page, countPerPage, realSortBy, sortType);
	}
	
	private String getRealSortBy(String sortBy){
		String realSortBy;
		if (sortBy.equals("transaction date")){
			realSortBy = "TRANSACTION_DATE";
		}else if (sortBy.equals("description")){
			realSortBy = "BILLING_DESCRIPTION";
		}else if (sortBy.equals("amount")){
			realSortBy = "BILLING_AMOUNT";					
		}else if (sortBy.equals("status")){
			realSortBy = "TRANSACTION_STATUS";	
		}else{
			realSortBy = "TO_NUMBER(NO)";
		}
		return realSortBy;
	}

	public List<BancassMiniResponse> getByRecentTransactionsStatement(String batchId, int count, Integer page,
			Integer countPerPage, String sortBy, String sortType) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String realSortBy = getRealSortBy(sortBy);
		return dao.getByRecentTransactionsStatement(batchId, count, page, countPerPage, realSortBy, sortType);
	}
	
	public int getTotalCountByDate(String batchId, Date start, Date end){
		return dao.getTotalCountByDate(batchId, start, end);
	}
	
	public int getTotalCountByRecent(String batchId, int count){
		return dao.getTotalCountByRecent(batchId, count);
	}
	
	public MappingProduct getProductName(String productCode) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		return mappingProductDao.getProductByCode(productCode);
	}
	
	public TransactionSummaryResponse getTransactionSummaryByPolicyNo(String policyNo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		return dao.getTransactionSummaryByPolicyNo(policyNo);
	}
	
	public List<DetailPremiResponse> getListDetailPremiByPolicyNo(Long shId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		return dao.getListDetailPremiByPolicyNo(shId);
	}
	
	public List<DetailBiayaResponse> getListDetailBiayaByPolicyNo(Long shId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		return dao.getListDetailBiayaByPolicyNo(shId);
	}
	
	public List<TransaksiLainResponse> getListTransaksiLainByPolicyNo(Long shId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		return dao.getListTransaksiLainByPolicyNo(shId);
	}
	
	public List<DisclaimerResponse> getListDisclaimerByPolicyNo(Long shId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		return dao.getListDisclaimerByPolicyNo(shId);
	}
	
	public boolean isExists(String policyNo){
		return dao.isExists(policyNo);
	}
}
