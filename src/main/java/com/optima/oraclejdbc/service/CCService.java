package com.optima.oraclejdbc.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocbc.schemas.soa.creditcard.service.wbst_inquiry_ccbalance.RESConsumerSequence_type1;
import com.optima.oraclejdbc.daoimpl.CCDao;
import com.optima.oraclejdbc.daoimpl.CreditCardInfoDao;
import com.optima.oraclejdbc.daoimpl.MappingProductDao;
import com.optima.oraclejdbc.model.CCUnbilledTrans;
import com.optima.oraclejdbc.model.CreditCardInfo;
import com.optima.oraclejdbc.model.MappingProduct;
import com.optima.oraclejdbc.response.creditcard.AccountBalanceResponse;
import com.optima.oraclejdbc.response.creditcard.AccountHeaderResponse;
import com.optima.oraclejdbc.response.creditcard.CreditCardBalanceResponse;
import com.optima.oraclejdbc.response.creditcard.CustomerHeaderResponse;
import com.optima.oraclejdbc.response.creditcard.RateInfoResponse;
import com.optima.oraclejdbc.response.creditcard.RawRateInfoResponse;
import com.optima.oraclejdbc.response.creditcard.RawTransactionDetailResponse;
import com.optima.oraclejdbc.response.creditcard.RewardSummaryResponse;
import com.optima.oraclejdbc.response.creditcard.RowTransactionDetailResponse;
import com.optima.oraclejdbc.response.creditcard.TransactionDetailResponse;
import com.optima.oraclejdbc.response.creditcard.UnbilledTransactionDetailResponse;
import com.optima.oraclejdbc.response.creditcard.UnbilledTransactionResponse;
import com.optima.oraclejdbc.response.creditcard.UserLimitResponse;

@Service
public class CCService {

	@Autowired
	CCDao dao;
	
	@Autowired
	MappingProductDao mappingProductDao;
	
	@Autowired
	CreditCardInfoDao creditCardInfoDao;
	
	public CustomerHeaderResponse getCustomerHeaderByCustomerNumber(String customerNumber, int year, int month) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		Calendar start = Calendar.getInstance();
		start.set(year, month-1, 1);
		Calendar end = Calendar.getInstance();
		end.set(Calendar.DATE, start.getActualMaximum(Calendar.DATE));
		return dao.getCustomerHeaderByCustomerNumber(customerNumber, start.getTime(), end.getTime());
	}

	public AccountHeaderResponse getAccountHeader(Long id) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {		
		return dao.getAccountHeader(id);
	}

	public TransactionDetailResponse getTransactionDetail(List<RawTransactionDetailResponse> rawTransactionDetails){
		TransactionDetailResponse response = new TransactionDetailResponse();
		response.setTransactionDetails(new ArrayList<RowTransactionDetailResponse>());
		for (RawTransactionDetailResponse rawTransaction : rawTransactionDetails) {
			Character tag = rawTransaction.getTag();
			if( tag.equals('C') ){
				response.setFullname(rawTransaction.getCardholderName());
				response.setCustomerNumber(rawTransaction.getCardholderNumber());
			
			}else if( tag.equals('P') ){
				response.setLastMonthLabel(rawTransaction.getDescription());
				response.setLastMonthSign(rawTransaction.getAmountSign());
				response.setLastMonthBalance(rawTransaction.getAmount());
			}else if( tag.equals('T') ){
				RowTransactionDetailResponse rowTransaction = new RowTransactionDetailResponse();
				rowTransaction.setTransactionDate(rawTransaction.getTransactionDate());
				rowTransaction.setPostingDate(rawTransaction.getPostingDate());
				rowTransaction.setDescription(rawTransaction.getDescription());
				rowTransaction.setSign(rawTransaction.getAmountSign());
				rowTransaction.setAmount(rawTransaction.getAmount());
				response.getTransactionDetails().add(rowTransaction);
			}else if( tag.equals('S') ){
				response.setSubTotalLabel(rawTransaction.getDescription());
				response.setSubTotalSign(rawTransaction.getAmountSign());
				response.setSubTotal(rawTransaction.getAmount());
			}else if( tag.equals('G') ){
				response.setTotalLabel(rawTransaction.getDescription());
				response.setTotalSign(rawTransaction.getAmountSign());
				response.setTotal(rawTransaction.getAmount());
			}
		}
		return response;
	}
	
	public List<TransactionDetailResponse> getTransactionDetails(Long accountHeaderId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		List<RawTransactionDetailResponse> records = dao.getRawTransactionDetail(accountHeaderId);
		List<TransactionDetailResponse> res = new ArrayList<TransactionDetailResponse>();
		TransactionDetailResponse response = getTransactionDetail(records);
		res.add(response);
		return res;
	}
	
	public List<TransactionDetailResponse> getTransactionDetails(String customerNumber, int year, int month) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		Calendar start = Calendar.getInstance();
		start.set(year, month-1, 1);
		Calendar end = Calendar.getInstance();
		end.set(Calendar.DATE, start.getActualMaximum(Calendar.DATE));
		Hashtable<Long, List<RawTransactionDetailResponse>> raws = dao.getTransactionDetails(customerNumber, start.getTime(), end.getTime());
		List<TransactionDetailResponse> res = new ArrayList<TransactionDetailResponse>();
		Enumeration<Long> keys = raws.keys();
		while( keys.hasMoreElements() ){
			TransactionDetailResponse response = getTransactionDetail(raws.get(keys.nextElement()));
			res.add(response);
		}
		return res;
	}

	public AccountBalanceResponse getAccountBalance(Long id) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		return dao.getAccountBalance(id);
	}

	public RewardSummaryResponse getRewardSummary(Long id) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		return dao.getRewardSummary(id);
	}

	public RateInfoResponse getRateInfo(Long id) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		UserLimitResponse userLimit = dao.getUserLimit(id);
		if( userLimit == null )
			return null;
		RateInfoResponse rateInfo = new RateInfoResponse();
		rateInfo.setCreditLimit(userLimit.getCreditLimit());
		rateInfo.setAvailableLimit(userLimit.getAvailableLimit());
		RawRateInfoResponse rawRateInfo = dao.getRawRateInfo(id);
		if( rawRateInfo == null )
			return null;
		rateInfo.setYearlyRetail(rawRateInfo.getYearlyRetail());
		rateInfo.setYearlyAdvanced(rawRateInfo.getYearlyAdvanced());
		rateInfo.setMonthlyRetail(rawRateInfo.getMonthlyRetail());
		rateInfo.setMonthlyAdvanced(rawRateInfo.getMonthlyAdvanced());
		return rateInfo;
	}

	public CustomerHeaderResponse getCustomerHeaderByAccountNumber(String strAccountNumber, int year, int month) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		Calendar start = Calendar.getInstance();
		start.set(year, month-1, 1);
		Calendar end = Calendar.getInstance();
		end.set(Calendar.DATE, start.getActualMaximum(Calendar.DATE));
		return dao.getCustomerHeaderByAccountNumber(strAccountNumber, start.getTime(), end.getTime());
	}

	public List<CreditCardBalanceResponse> getSummary(RESConsumerSequence_type1[] resConsumerSequence_type1s) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		ArrayList<CreditCardBalanceResponse> res = new ArrayList<CreditCardBalanceResponse>();
		if( resConsumerSequence_type1s != null )
			for (RESConsumerSequence_type1 seq : resConsumerSequence_type1s) {
				CreditCardBalanceResponse resp = new CreditCardBalanceResponse(seq);
				CreditCardInfo ccInfo = creditCardInfoDao.getInfo(resp.getCreditCardNumber());
				MappingProduct product = null;
				if( ccInfo != null ){
					product = mappingProductDao.getProductById(ccInfo.getProductId());									
				}
				if( product == null ){
					product = new MappingProduct();
					product.setProductName("TBD");
				}
				resp.setProduct(product);
				res.add(resp);
			}
		return res;
	}

	public UnbilledTransactionResponse getUnbilled(
			com.ocbc.schemas.soa.creditcard.service.wbst_inquiry_ccunbilledactivity.RESConsumerSequence_type1[] resConsumerSequence_type1
			, int start, int end) {
		
		UnbilledTransactionResponse res = new UnbilledTransactionResponse();
		if( resConsumerSequence_type1 != null && resConsumerSequence_type1.length > 0 ){
			Arrays.sort(resConsumerSequence_type1, new Comparator<com.ocbc.schemas.soa.creditcard.service.wbst_inquiry_ccunbilledactivity.RESConsumerSequence_type1>() {

				@Override
				public int compare(com.ocbc.schemas.soa.creditcard.service.wbst_inquiry_ccunbilledactivity.RESConsumerSequence_type1 o1,com.ocbc.schemas.soa.creditcard.service.wbst_inquiry_ccunbilledactivity.RESConsumerSequence_type1 o2) {
					if( o1.getCreditCardTransactionDetail().getTransactionDate().before(o2.getCreditCardTransactionDetail().getTransactionDate()) )
						return -1;
					if( o1.getCreditCardTransactionDetail().getTransactionDate().after(o2.getCreditCardTransactionDetail().getTransactionDate()) )
						return 1;
					return 0;
				}
			});
			res.setFromDate(resConsumerSequence_type1[0].getCreditCardTransactionDetail().getTransactionDate());
			res.setToDate(resConsumerSequence_type1[resConsumerSequence_type1.length-1].getCreditCardTransactionDetail().getTransactionDate());
			res.setTransactionDetails(new ArrayList<UnbilledTransactionDetailResponse>());
			int i = 0;
			for (com.ocbc.schemas.soa.creditcard.service.wbst_inquiry_ccunbilledactivity.RESConsumerSequence_type1 seq : resConsumerSequence_type1) {
				UnbilledTransactionDetailResponse response = new UnbilledTransactionDetailResponse(seq);
				if( i>=start && i<=end)
					res.getTransactionDetails().add(response);
				i++;
			}
			res.setCountTransaction(resConsumerSequence_type1.length);
		}else
			res.setCountTransaction(0);
		
		return res;
	}
	
	public void insertTemporary(List<CCUnbilledTrans> listTemp) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		dao.insertOrUpdateBatch(listTemp.toArray());
	}
	
	public void deleteTemporary(String ccNumber) {
		dao.deleteTemp(ccNumber);
	}
	
	public boolean exists(String ccNo, int year, int month){
		Calendar thisMonth = Calendar.getInstance();
		thisMonth.set(Calendar.DATE, 1);
		thisMonth.set(Calendar.MONTH, month-1);
		thisMonth.set(Calendar.YEAR, year);
		
		Calendar nextMonth = Calendar.getInstance();
		nextMonth.set(Calendar.MONTH, month-1);
		nextMonth.set(Calendar.YEAR, year);
		nextMonth.set(Calendar.DATE, nextMonth.getActualMaximum(Calendar.DATE));
		
		return dao.exists(ccNo, thisMonth.getTime(), nextMonth.getTime());
	}
}
