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

import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_accountbalance.RESConsumerSequence_type0;
import com.ocbc.schemas.soa.emf.account.aggregates.TransactionStatementDetails_type0;
import com.optima.oraclejdbc.daoimpl.AccountListDao;
import com.optima.oraclejdbc.daoimpl.DailyStatementDao;
import com.optima.oraclejdbc.daoimpl.MappingProductDao;
import com.optima.oraclejdbc.model.AccountList;
import com.optima.oraclejdbc.model.DailyStatementTemp;
import com.optima.oraclejdbc.model.MappingProduct;
import com.optima.oraclejdbc.response.dailystatement.DailyStatementResponse;
import com.optima.oraclejdbc.response.dailystatement.DailyStatementSummaryResponse;
import com.optima.oraclejdbc.response.dailystatement.PointResponse;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.DateUtility;

@Service
public class DailyStatementService {
	@Autowired
	DailyStatementDao dao;
	
	@Autowired
	AccountListDao accountListDao;
	
	@Autowired
	MappingProductDao mappingProductDao;
	
	public List<DailyStatementResponse> getMiniStatement(String accountNumber, String currency, Date startDate, Date endDate, 
			Integer start, Integer end) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		return dao.getMiniStatement(accountNumber, currency, startDate, endDate, start, end);
	}
	
	public List<DailyStatementResponse> getMiniStatement(String accountNumber, String currency) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		Calendar now = Calendar.getInstance();
		Calendar threeMonthAgo = Calendar.getInstance();
		threeMonthAgo.add(Calendar.MONTH, -3);
		return dao.getMiniStatement(accountNumber, currency, threeMonthAgo.getTime(), now.getTime(), 1, 10);
	}

	public List<DailyStatementResponse> getByDateStatement(String accountNumber, String currency, Date startDate, Date endDate, Integer start,
			Integer end, String sortBy, String sortType, String searchKeyWord) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		
		String realSortBy = getRealSortBy(sortBy, sortType);
		return dao.getByDateStatement(accountNumber, currency, startDate, endDate, start, end, realSortBy, sortType, searchKeyWord);
	}

	public List<DailyStatementResponse> getByRecentTransactionsStatement(String accountNumber, String currency, int count, Integer start,
			Integer end, String sortBy, String sortType, String searchKeyWord) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		
		String realSortBy = getRealSortBy(sortBy, sortType);
		return dao.getByRecentTransactionsStatement(accountNumber, currency, count, start, end, realSortBy, sortType, searchKeyWord);
	}
	
	private String getRealSortBy(String sortBy, String sortType){
		String realSortBy;
		if( sortBy.equals("transaction date") ){
			realSortBy = "TRANSACTION_DATE";
		}else if (sortBy.equals("value date")){
			realSortBy = "VALUE_DATE";
		}else if (sortBy.equals("description")){
			realSortBy = "TRX_DESC";
		}else if (sortBy.equals("debet")){
			if( sortType.equals("ASC") ){
				realSortBy = "DEBIT_OR_CREDIT ASC, AMOUNT";
			}else{
				realSortBy = "DEBIT_OR_CREDIT DESC, AMOUNT";
			}			
		}else if (sortBy.equals("credit")){
			if( sortType.equals("ASC") ){
				realSortBy = "DEBIT_OR_CREDIT DESC, AMOUNT";
			}else{
				realSortBy = "DEBIT_OR_CREDIT ASC, AMOUNT";
			}	
		}else if (sortBy.equals("balance")){
			realSortBy = "BALANCE";
		}else{
			realSortBy = "RECORD_ID";
		}
		return realSortBy;
	}
	
	public int getTotalCountByDate(String accountNumber, String currency, Date start, Date end, String searchKeyWord){
		return dao.getTotalCountByDate(accountNumber, currency, start, end, searchKeyWord);
	}
	
	public int getTotalCountByRecent(String accountNumber, String currency, int count, String searchKeyWord){
		return dao.getTotalCountByRecent(accountNumber, currency, count, searchKeyWord);
	}

	public List<DailyStatementSummaryResponse> getDailyStatementSummary(
			RESConsumerSequence_type0[] resConsumerSequence_type0) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		List<DailyStatementSummaryResponse> res = new ArrayList<DailyStatementSummaryResponse>();
		for (RESConsumerSequence_type0 sequence : resConsumerSequence_type0) {
			String accountNumber = sequence.getConsDepositBalance().getAccountBalancelnquiryKey()
					.getAlternateAccountKeyDetail().getAccountNo1();
			String currency = sequence.getConsDepositBalance().getAccountBalancelnquiryKey()
					.getAlternateAccountKeyDetail().getCurrencyType();
			AccountList accountList = accountListDao.getAccountListByAccountNumber(accountNumber, currency);
			if( accountList == null )
				continue;
			MappingProduct mappingProduct = mappingProductDao.getProductById(accountList.getProductId());
			DailyStatementSummaryResponse sum = new DailyStatementSummaryResponse();
			sum.setAccountNumber(accountNumber);
			sum.setAvailable(sequence.getConsDepositBalance().getDepositAccountBalanceDetails().getAvailableBalance().doubleValue());
			sum.setBalance(sequence.getConsDepositBalance().getDepositAccountBalanceDetails().getLedgerBalance().doubleValue());
			sum.setCurrency(accountList.getCurrency());
			if( sequence.getConsDepositBalance().getDepositAccountBalanceDetails().getHoldBalance() != null )
				sum.setHold(sequence.getConsDepositBalance().getDepositAccountBalanceDetails().getHoldBalance().doubleValue());
			if( sequence.getConsDepositBalance().getDepositAccountBalanceDetails().getOverDraftAmount() != null )
				sum.setOverdraft(sequence.getConsDepositBalance().getDepositAccountBalanceDetails().getOverDraftAmount().doubleValue());
			sum.setProduct(mappingProduct);
			res.add(sum);
		}
		return res;
	}

	public List<DailyStatementResponse> getRecentAccountActivity(
			TransactionStatementDetails_type0[] transactionStatementDetails, Double currentBalance,
			String sortType, String searchKeyWord, boolean fromSummary, String accountNumber, String currency) {
		List<DailyStatementResponse> res = new ArrayList<DailyStatementResponse>();
		Double balance = currentBalance;
		boolean isYesterdaySubmitted = dao.isYesterdaySubmitted(accountNumber, currency);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date yesterday = cal.getTime();
		if( transactionStatementDetails != null ){
			boolean isAsc = sortType.equalsIgnoreCase("ASC");
			for( int i=transactionStatementDetails.length-1;i>=0;i--){		
				DailyStatementResponse response = new DailyStatementResponse(transactionStatementDetails[i], balance, fromSummary);
				if( isYesterdaySubmitted && yesterday.equals(response.getTransactionDate()) )
					continue;
				if( fromSummary ){
					double temp = response.getBalance();
					response.setBalance(balance);
					balance = temp;
				}else
					balance = response.getBalance();
				boolean isInsert = true;
				if( searchKeyWord != null )
					isInsert = contains(response, searchKeyWord);
				if( isInsert ){
					if( isAsc )
						res.add(response);
					else
						res.add(0, response);
				}									
			}
			
		}
		return res;
	}

	/*
	 * Check whether searchKeyWord is contained in daily statement or not
	 * Check to trans description, trx description, transaction date, value date,
	 * amount, and balance
	 * 
	 * Note:
	 * Case insensitive search in trans description and trx description
	 * BigDecimal value search in amount and balance, meaning thousand separator in searchKeyWord will be omitted
	 * Date value search in transaction date and value date
	 * Date format: dd/MM/yyyy
	 * 
	 * @param response object to check to
	 * @param searchKeyWord key word to search in object
	 *  
	 */
	private boolean contains(DailyStatementResponse response, String searchKeyWord) {
		searchKeyWord = searchKeyWord.toLowerCase();
		if( response.getTransDescription() != null && response.getTransDescription().toLowerCase().contains(searchKeyWord) 
				|| response.getTrxDescription() != null && response.getTrxDescription().toLowerCase().contains(searchKeyWord) )
			return true;
		
		// check whether searchKeyWord is a big decimal or not
		// we use scanner to check it
		Scanner scanner = new Scanner(searchKeyWord);
		boolean isNumber = scanner.hasNextBigDecimal();
		scanner.close();
		if( isNumber ){			
			// get thousand separator, we always use US Locale
			char thousandSeparator = CommonUtils.getThousandSeparator(Locale.US);
			
			// remove all thousand separator
			String strAmount = searchKeyWord.replaceAll(""+thousandSeparator, "");
			BigDecimal amount = new BigDecimal(strAmount);
			if( response.getAmount() == amount.doubleValue() 
					|| response.getBalance() == amount.doubleValue() ) {
				
				return true;
			}
		}
		
		// check if searchKeyWord is a date
		Date searchDate = DateUtility.parse(searchKeyWord, "dd/MM/yyyy");
		if( searchDate != null ){
			if (response.getTransactionDate().compareTo(searchDate) == 0
					|| response.getValueDate().compareTo(searchDate) == 0 ){
				return true;
			}
		}
		
		return false;
	}

	public Double getLastBalance(String accountNumber, String currency) {
		return dao.getLastBalance(accountNumber, currency);
	}

	public List<PointResponse> getPointsSummary(String cifKey, RESConsumerSequence_type0[] resConsumerSequence_type0) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		ArrayList<PointResponse> res = new ArrayList<PointResponse>();
		for (RESConsumerSequence_type0 seq : resConsumerSequence_type0) {
			PointResponse response = new PointResponse(seq.getConsDepositBalance());
			AccountList account = accountListDao.getAccountListByProducCode(cifKey, 
					seq.getConsDepositBalance().getAccountBalancelnquiryKey().getProductType());
			if( account != null ){
				MappingProduct product = mappingProductDao.getProductById(account.getProductId());
				response.setProduct(product);
				response.setAccountNo(account.getAccountNumber());
			}
			res.add(response);
		}
		return res;
	}

	public void insertTemporary(List<DailyStatementTemp> listTemp) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		dao.insertOrUpdateBatch(listTemp.toArray());
	}
	
	public void deleteTemp(String accountNumber) {
		dao.deleteTemp(accountNumber);
	}
}
