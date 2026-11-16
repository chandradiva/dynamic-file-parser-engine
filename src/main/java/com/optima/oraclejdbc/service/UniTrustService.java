package com.optima.oraclejdbc.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocbc.schemas.soa.emf.investment.aggregates.InvestmentAccountDetail_type0;
import com.ocbc.schemas.soa.emf.investment.aggregates.UTTransactionDetails_type0;
import com.optima.oraclejdbc.daoimpl.UniTrustDao;
import com.optima.oraclejdbc.model.UnitTrust;
import com.optima.oraclejdbc.model.UnitTrustTemp;
import com.optima.oraclejdbc.response.unitrust.UniTrustTrxHistoryResponse;
import com.optima.oraclejdbc.response.unittrust.UnitTrustResponse;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.DateUtility;

@Service
public class UniTrustService {

	@Autowired
	UniTrustDao dao;
	
	public List<UniTrustTrxHistoryResponse> getMiniStatement(String accountNumber, Date startDate, Date endDate, 
			Integer start, Integer end) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		return dao.getMiniStatement(accountNumber, startDate, endDate, start, end);
	}
	
	public List<UniTrustTrxHistoryResponse> getMiniStatement(String accountNumber) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		Calendar now = Calendar.getInstance();
		Calendar threeMonthAgo = Calendar.getInstance();
		threeMonthAgo.add(Calendar.MONTH, -3);
		return dao.getMiniStatement(accountNumber, threeMonthAgo.getTime(), now.getTime(), null, 10);
	}

	public List<UniTrustTrxHistoryResponse> getByDateStatement(String accountNumber, Date startDate, Date endDate, Integer start,
			Integer end, String sortBy, String sortType, String searchKeyWord) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String realSortBy = getRealSortBy(sortBy);
		return dao.getByDateStatement(accountNumber, startDate, endDate, start, end, realSortBy, sortType, searchKeyWord);
	}

	public List<UniTrustTrxHistoryResponse> getByRecentTransactionsStatement(String accountNumber, int count, Integer page,
			Integer countPerPage, String sortBy, String sortType, String searchKeyWord) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		
		String realSortBy = getRealSortBy(sortBy);
		return dao.getByRecentTransactionsStatement(accountNumber, count, page, countPerPage, realSortBy, sortType, searchKeyWord);
	}
	
	private String getRealSortBy(String sortBy){
		String realSortBy;
		if( sortBy.equals("reference number") ){
			realSortBy = "REFERENCE_NO";
		}else if (sortBy.equals("transaction date")){
			realSortBy = "TRANSACTION_DATE";
		}else if (sortBy.equals("effective date")){
			realSortBy = "EFFECTIVE_DATE";
		}else if (sortBy.equals("transaction amount")){
			realSortBy = "AMOUNT";					
		}else if (sortBy.equals("transaction type")){
			realSortBy = "TRANSACTION_TYPE";	
		}else if (sortBy.equals("nav")){
			realSortBy = "NAV";
		}else if (sortBy.equals("status")){
			realSortBy = "STATUS";
		}else if (sortBy.equals("transaction source")){
			realSortBy = "TRANSACTION_SOURCE";
		}else{
			realSortBy = "TRANSACTION_DATE";
		}
		return realSortBy;
	}
	
	public int getTotalCountByDate(String accountNumber, Date start, Date end, String searchKeyWord){
		return dao.getTotalCountByDate(accountNumber, start, end, searchKeyWord);
	}
	
	public int getTotalCountByRecent(String accountNumber, int count, String searchKeyWord){
		return dao.getTotalCountByRecent(accountNumber, count, searchKeyWord);
	}

	public List<UnitTrustResponse> getSummary(InvestmentAccountDetail_type0[] investmentAccountDetail) {
		ArrayList<UnitTrustResponse> res = new ArrayList<UnitTrustResponse>();
		for (InvestmentAccountDetail_type0 accountDetail : investmentAccountDetail) {
			UnitTrustResponse response = new UnitTrustResponse(accountDetail);
			res.add(response);
		}
		return res;
	}

	public List<UniTrustTrxHistoryResponse> getRecentUTAccountActivity(
			UTTransactionDetails_type0[] utTransactionDetails, int start, int count,  String sortType,
				String searchKeyWord) {
		ArrayList<UniTrustTrxHistoryResponse> res = new ArrayList<UniTrustTrxHistoryResponse>();
		for(int i=(start-1);i<utTransactionDetails.length&&i<(start+count-1);i++){			
			UniTrustTrxHistoryResponse response = new UniTrustTrxHistoryResponse(utTransactionDetails[i]);
			if( response.getReferenceNo().length() > 0 ){
				boolean isInsert = true;
				if( searchKeyWord != null )
					isInsert = contains(response, searchKeyWord);
				if( isInsert )
					if( sortType.equalsIgnoreCase("DESC") )
						res.add(response);
					else
						res.add(0, response);
			}
				
		}
		return res;
	}

	private boolean contains(UniTrustTrxHistoryResponse response, String searchKeyWord) {
		searchKeyWord = searchKeyWord.toUpperCase();
		
		if (response.getReferenceNo() != null 
				&& response.getReferenceNo().toUpperCase().contains(searchKeyWord)) {
			return true;
		}
		
		if( searchKeyWord.equals("SUBSCRIPTION") && response.getTransactionType() == '1' 
				|| searchKeyWord.equals("REDEMPTION") && response.getTransactionType() == '2'
				|| searchKeyWord.equals("SWITCHING IN") && response.getTransactionType() == '3'
				|| searchKeyWord.equals("SWITCHING OUT") && response.getTransactionType() == '4'
				){
			return true;
		}
		
		if( searchKeyWord.equals("MOBILE BANKING") && response.getTransactionSource().toUpperCase().equals("M") 
				|| searchKeyWord.equals("INTERNET BANKING") && response.getTransactionSource().toUpperCase().equals("I")
				|| searchKeyWord.equals("BRANCH") && response.getTransactionSource().toUpperCase().equals("B")
				|| searchKeyWord.equals("PHONE") && response.getTransactionSource().toUpperCase().equals("P")
				){
			return true;
		}
		
		if( searchKeyWord.equals("IN PROGRESS") && response.getStatus() == '0' 
				|| searchKeyWord.equals("SUCCESSFUL") && response.getStatus() == '1'
				|| searchKeyWord.equals("REJECTED") && response.getStatus() == '2'
				|| searchKeyWord.equals("CANCELLED") && response.getStatus() == '3'){
			return true;
		}
		
		Scanner scanner = new Scanner(searchKeyWord);
		boolean isNumber = scanner.hasNextBigDecimal();
		if( isNumber ){
			scanner.next();
			isNumber = !scanner.hasNext();
		}
		
		scanner.close();
		if( isNumber ){
			char thousandSep = CommonUtils.getThousandSeparator(Locale.US);
			String strAmount = searchKeyWord.replaceAll(""+thousandSep, "");
			BigDecimal amount = new BigDecimal(strAmount);
			if( response.getAmount() == amount.doubleValue() 
					|| response.getNav() == amount.doubleValue() 
					|| response.getFeeAmount() == amount.doubleValue()
					|| response.getUnit() == amount.doubleValue() )
				return true;
		}
		
		Date searchDate = DateUtility.parse(searchKeyWord, "dd/MM/yyyy");
		if( searchDate != null ){
			if (response.getTransactionDate().compareTo(searchDate) == 0
					|| response.getEffectiveDate().compareTo(searchDate) == 0 ){
				return true;
			}
		}
		return false;
	}
	
	public void insertTemporary(List<UnitTrustTemp> listTemp) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		dao.insertOrUpdateBatch(listTemp.toArray());
	}
	
	public void deleteTemp(String accountNumber) {
		dao.deleteUTTemp(accountNumber);
	}
	
	public List<UnitTrust> getListDataByAccNo(String accountNo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		return dao.getListDataByAccNo(accountNo);
	}
}
