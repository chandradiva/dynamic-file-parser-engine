package com.optima.oraclejdbc.daoimpl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.elixirprop.CreditCardProp;
import com.optima.oraclejdbc.model.CCAccountBalance;
import com.optima.oraclejdbc.model.CCAccountHeader;
import com.optima.oraclejdbc.model.CCAccountLevelMessage;
import com.optima.oraclejdbc.model.CCAccountTrailer;
import com.optima.oraclejdbc.model.CCCustomerHeader;
import com.optima.oraclejdbc.model.CCCustomerHeaderMessage;
import com.optima.oraclejdbc.model.CCCustomerTrailer;
import com.optima.oraclejdbc.model.CCDualCurrency;
import com.optima.oraclejdbc.model.CCFileTrailer;
import com.optima.oraclejdbc.model.CCFooterMessage;
import com.optima.oraclejdbc.model.CCLuckyDraw;
import com.optima.oraclejdbc.model.CCParentModel;
import com.optima.oraclejdbc.model.CCPromotionalMessage;
import com.optima.oraclejdbc.model.CCRateInfo;
import com.optima.oraclejdbc.model.CCRewardSummary;
import com.optima.oraclejdbc.model.CCStatement;
import com.optima.oraclejdbc.model.CCStatementMessage;
import com.optima.oraclejdbc.model.CCTransactionDetails;
import com.optima.oraclejdbc.model.CCUnbilledTrans;
import com.optima.oraclejdbc.model.CustomerInfo;
import com.optima.oraclejdbc.model.ElixirModel;
import com.optima.oraclejdbc.model.backoffice.BOCCRegex;
import com.optima.oraclejdbc.model.backoffice.BOSendEmail;
import com.optima.oraclejdbc.report.ReportCCStatement;
import com.optima.oraclejdbc.response.creditcard.AccountBalanceResponse;
import com.optima.oraclejdbc.response.creditcard.AccountHeaderResponse;
import com.optima.oraclejdbc.response.creditcard.CustomerHeaderResponse;
import com.optima.oraclejdbc.response.creditcard.RawRateInfoResponse;
import com.optima.oraclejdbc.response.creditcard.RawTransactionDetailResponse;
import com.optima.oraclejdbc.response.creditcard.RewardSummaryResponse;
import com.optima.oraclejdbc.response.creditcard.UserLimitResponse;
import com.optima.oraclejdbc.utils.CCRegexUtils;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.DateUtility;
import com.optima.oraclejdbc.utils.RsaUtil;

@Component
public class CCDao extends ParentDao<CCParentModel> {

	@Autowired
	RsaUtil rsaUtil;
	
	@Autowired
	CCRegexDao ccRegexDao;
	
	@Autowired
	SystemParameterDao sysParamDao;
	
	@Autowired
	ContentParameterDao contentParamDao;
	
	@Autowired
	AccountListDao accountListDao;
	
	@Resource(name="confProp")
	private Properties confProperties;
	
	@Autowired
	CustomerInfoDao customerInfoDao;
	
	public CustomerHeaderResponse getCustomerHeaderByCustomerNumber(String customerNumber, Date start, Date end) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT ID, CIF_NAME, MAILING_ADDRESS_LINE_1, MAILING_ADDRESS_LINE_2, MAILING_ADDRESS_LINE_3, "
				+ "MAILING_ADDRESS_LINE_4, MAILING_ADDRESS_POSTAL_CODE "
				+ "FROM CC_CUSTOMER_HEADER "
				+ "WHERE CUSTOMER_NUMBER = ? AND STATEMENT_DATE >= ? AND STATEMENT_DATE <= ?";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(customerNumber);
		args.add(start);
		args.add(end);
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if( rows.size() > 0 ){
			CustomerHeaderResponse response = serialize(CustomerHeaderResponse.class, rows.get(0));
			return response;
		}
		return null;
	}

	public AccountHeaderResponse getAccountHeader(Long id) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT ID, CH_ID, CARDHOLDER_NUMBER, STATEMENT_DATE, PAYMENT_DUE_DATE, "
				+ "TOTAL_OUTSTANDING_BALANCE, MINIMUM_PAYMENT, CREDIT_QUALITY "
				+ "FROM CC_ACCOUNT_HEADER "
				+ "WHERE CH_ID = ?";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(id);
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if( rows.size() > 0 ){
			AccountHeaderResponse response = serialize(AccountHeaderResponse.class, rows.get(0));
			return response;
		}
		return null;
	}

	private List<Long> getAllAccountHeaders(List<Long> customerHeaderIds){
		String sql = "SELECT ID FROM CC_ACCOUNT_HEADER WHERE CH_ID = ?";
		List<Long> res = new ArrayList<Long>();
		for (Long id : customerHeaderIds) {
			ArrayList<Object> args = new ArrayList<Object>();
			args.add(id);
			List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
			for (Map<String, Object> row : rows) {
				res.add(((BigDecimal)row.get("ID")).longValue());
			}
		}
		return res;
	}
	
	public List<RawTransactionDetailResponse> getRawTransactionDetail(Long accountHeaderId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		String sql = "SELECT DETAIL_RECORD_TAG, CARDHOLDER_NAME, CARDHOLDER_NUMBER, TRANSACTION_DATE, POSTING_DATE, DESCRIPTION_1, "
				+ "AMOUNT_SIGN, AMOUNT "
				+ "FROM CC_TRANSACTION_DETAILS "
				+ "WHERE DETAIL_RECORD_TAG IN ('C', 'P', 'T', 'S', 'G') AND AH_ID=? "
				+ "ORDER BY ID ASC";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(accountHeaderId);
		List<Map<String, Object>> transactions = template.queryForList(sql, args.toArray());
		List<RawTransactionDetailResponse> records = new ArrayList<RawTransactionDetailResponse>();
		for (Map<String, Object> transaction : transactions) {
			RawTransactionDetailResponse row = serialize(RawTransactionDetailResponse.class, transaction);
			records.add(row);
		}
		return records;
	}
	
	public Hashtable<Long, List<RawTransactionDetailResponse>> getTransactionDetails(String customerNumber, Date start, Date end) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		List<Long> customerHeaderIds = getCustomerHeadersIds(customerNumber, start, end);
		List<Long> accountHeaderIds = getAllAccountHeaders(customerHeaderIds);
		Hashtable<Long, List<RawTransactionDetailResponse>> res = new Hashtable<Long, List<RawTransactionDetailResponse>>();
		for (Long ahId : accountHeaderIds) {
			List<RawTransactionDetailResponse> records = getRawTransactionDetail(ahId);
			res.put(ahId, records);
		}		
		return res;
	}

	private List<Long> getCustomerHeadersIds(String customerNumber, Date start, Date end) {
		String sql = "SELECT ID FROM CC_CUSTOMER_HEADER WHERE CUSTOMER_NUMBER = ? "
				+ "AND STATEMENT_DATE >= ? AND STATEMENT_DATE <= ? ";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(customerNumber);
		args.add(start);
		args.add(end);
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray() );
		List<Long> res = new ArrayList<Long>();
		for (Map<String, Object> row : rows) {
			res.add(((BigDecimal)row.get("ID")).longValue());
		}
		return res;
	}

	public AccountBalanceResponse getAccountBalance(Long id) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT OPENING_BALANCE_SIGN, OPEN_BALANCE_BEFORE, RETAIL_TRANSACTION_SIGN, RETAIL_TRANSACTION_AMOUNT, "
				+ "INTEREST_CHARGES_SIGN, INTEREST_CHARGES_AMOUNT, PAYMENT_SIGN, PAYMENT_AMOUNT, "
				+ "TOTAL_SIGN, TOTAL "
				+ "FROM CC_ACCOUNT_BALANCE "
				+ "WHERE CH_ID=?";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(id);
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray() );
		if( rows.size() > 0 ){
			AccountBalanceResponse res = serialize(AccountBalanceResponse.class, rows.get(0));
			return res;
		}
		return null;
	}

	public RewardSummaryResponse getRewardSummary(Long id) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT CARD_HOLDER_NO, EARNED_THIS_MONTH_SIGN, EARNED_THIS_MONTH, PREVIOUS_MONTH_SIGN, PREVIOUS_MONTH, "
				+ "USED_SIGN, USED, AVAILABLE_SIGN, AVAILABLE, AVAILABLE_EXPIRE_DATE, "
				+ "EXTENDED, EXTENDED_EXPIRE_DATE "
				+ "FROM CC_REWARD_SUMMARY "
				+ "WHERE CH_ID=?";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(id);
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray() );
		if( rows.size() > 0 ){
			RewardSummaryResponse res = serialize(RewardSummaryResponse.class, rows.get(0));
			return res;
		}
		return null;
	}

	public UserLimitResponse getUserLimit(Long id) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT CREDIT_LIMIT, AVAILABLE_LIMIT "
				+ "FROM CC_ACCOUNT_HEADER "
				+ "WHERE CH_ID=?";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(id);
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray() );
		if( rows.size() > 0 ){
			UserLimitResponse res = serialize(UserLimitResponse.class, rows.get(0));
			return res;
		}
		return null;
	}

	public RawRateInfoResponse getRawRateInfo(Long id) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT YEARLY_RETAIL_PERCENTAGE, MONTHLY_RETAIL_PERCENTAGE, YEARLY_CASH_ADVANCE, MONTHLY_CASH_ADVENCE "
				+ "FROM CC_RATE_INFO "
				+ "WHERE CH_ID=?";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(id);
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray() );
		if( rows.size() > 0 ){
			RawRateInfoResponse res = serialize(RawRateInfoResponse.class, rows.get(0));
			return res;
		}
		return null;
	}

	private Long getCustomerHeaderId(String strAccountNumber, Date start, Date end ){
		String sql = "SELECT CH_ID FROM CC_ACCOUNT_HEADER WHERE CARDHOLDER_NUMBER = ? "
				+ "AND STATEMENT_DATE >= ? AND STATEMENT_DATE <= ? ";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(strAccountNumber);
		args.add(start);
		args.add(end);
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray() );
		if( rows.size() > 0 ){
			return ((BigDecimal)rows.get(0).get("CH_ID")).longValue();
		}
		return null;
	}
	
	public CustomerHeaderResponse getCustomerHeaderByAccountNumber(String strAccountNumber, Date start, Date end) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		Long chId = getCustomerHeaderId(strAccountNumber, start, end);
		if( chId == null )
			return null;
		
		String sql = "SELECT ID, CUSTOMER_FULLNAME, MAILING_ADDRESS_LINE_1, MAILING_ADDRESS_LINE_2, MAILING_ADDRESS_LINE_3, "
				+ "MAILING_ADDRESS_LINE_4, MAILING_ADDRESS_POSTAL_CODE "
				+ "FROM CC_CUSTOMER_HEADER "
				+ "WHERE ID=?";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(chId);
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if( rows.size() > 0 ){
			CustomerHeaderResponse response = serialize(CustomerHeaderResponse.class, rows.get(0));
			return response;
		}
		return null;
	}

	public Long getNextId() {
		String sql = "SELECT CC_STATEMENT_SEQ1.NEXTVAL FROM DUAL";
		return template.queryForObject(sql, Long.class);		
	}
	
	public Long getNextChId(){
		String sql = "SELECT CC_CUSTOMER_HEADER_SEQ2.NEXTVAL FROM DUAL";
		return template.queryForObject(sql, Long.class);
	}
	
	public Long getNextAhId(){
		String sql = "SELECT CC_ACCOUNT_HEADER_SEQ2.NEXTVAL FROM DUAL";
		return template.queryForObject(sql, Long.class);
	}
	
	@Override
	public void createBatch(Object[] dataArray) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {		
		if( dataArray.length > 0 ){
			List<CCStatement> ccStatements = new ArrayList<CCStatement>();
			List<CCCustomerHeader> ccCustomerHeader = new ArrayList<CCCustomerHeader>();
			List<CCAccountLevelMessage> ccAccountLevelMessage = new ArrayList<CCAccountLevelMessage>();
			List<CCAccountHeader> ccAccountHeader = new ArrayList<CCAccountHeader>();
			List<CCTransactionDetails> ccTransactionDetails = new ArrayList<CCTransactionDetails>();
			List<CCLuckyDraw> ccLuckyDraw = new ArrayList<CCLuckyDraw>();
			List<CCAccountBalance> ccAccountBalance = new ArrayList<CCAccountBalance>();
			List<CCRewardSummary> ccRewardSummary = new ArrayList<CCRewardSummary>();
			List<CCRateInfo> ccRateInfo = new ArrayList<CCRateInfo>();
			List<CCStatementMessage> ccStatementMessage = new ArrayList<CCStatementMessage>();
			List<CCPromotionalMessage> ccPromotionalMessage = new ArrayList<CCPromotionalMessage>();
			List<CCAccountTrailer> ccAccountTrailer = new ArrayList<CCAccountTrailer>();
			List<CCCustomerHeaderMessage> ccCustomerHeaderMessage = new ArrayList<CCCustomerHeaderMessage>();
			List<CCCustomerTrailer> ccCustomerTrailer = new ArrayList<CCCustomerTrailer>();
			List<CCDualCurrency> ccDualCurrency = new ArrayList<CCDualCurrency>();
			List<CCFileTrailer> ccFileTrailer = new ArrayList<CCFileTrailer>();
			List<CCFooterMessage> ccFooterMessage = new ArrayList<CCFooterMessage>();
			List<CCUnbilledTrans> ccUnbilled = new ArrayList<CCUnbilledTrans>();
			
			for(int i=0;i<dataArray.length;i++){
				if( dataArray[i] instanceof CCStatement ){
					ccStatements.add((CCStatement)dataArray[i]);
				}else if( dataArray[i] instanceof CCCustomerHeader ){
					ccCustomerHeader.add((CCCustomerHeader)dataArray[i]);
				}else if( dataArray[i] instanceof CCAccountLevelMessage ){
					ccAccountLevelMessage.add((CCAccountLevelMessage)dataArray[i]);
				}else if( dataArray[i] instanceof CCAccountHeader ){
					ccAccountHeader.add((CCAccountHeader)dataArray[i]);
				}else if( dataArray[i] instanceof CCTransactionDetails ){
					ccTransactionDetails.add((CCTransactionDetails)dataArray[i]);
				}else if( dataArray[i] instanceof CCLuckyDraw ){
					ccLuckyDraw.add((CCLuckyDraw)dataArray[i]);
				}else if( dataArray[i] instanceof CCAccountBalance ){
					ccAccountBalance.add((CCAccountBalance)dataArray[i]);
				}else if( dataArray[i] instanceof CCAccountBalance ){
					ccAccountBalance.add((CCAccountBalance)dataArray[i]);
				}else if( dataArray[i] instanceof CCRewardSummary ){
					ccRewardSummary.add((CCRewardSummary)dataArray[i]);
				}else if( dataArray[i] instanceof CCRateInfo ){
					ccRateInfo.add((CCRateInfo)dataArray[i]);
				}else if( dataArray[i] instanceof CCStatementMessage ){
					ccStatementMessage.add((CCStatementMessage)dataArray[i]);
				}else if( dataArray[i] instanceof CCPromotionalMessage ){
					ccPromotionalMessage.add((CCPromotionalMessage)dataArray[i]);
				}else if( dataArray[i] instanceof CCAccountTrailer){
					ccAccountTrailer.add((CCAccountTrailer)dataArray[i]);
				}else if( dataArray[i] instanceof CCCustomerHeaderMessage){
					ccCustomerHeaderMessage.add((CCCustomerHeaderMessage)dataArray[i]);
				}else if( dataArray[i] instanceof CCCustomerTrailer){
					ccCustomerTrailer.add((CCCustomerTrailer)dataArray[i]);
				}else if( dataArray[i] instanceof CCDualCurrency){
					ccDualCurrency.add((CCDualCurrency)dataArray[i]);
				}else if( dataArray[i] instanceof CCFileTrailer){
					ccFileTrailer.add((CCFileTrailer)dataArray[i]);
				}else if( dataArray[i] instanceof CCFooterMessage){
					ccFooterMessage.add((CCFooterMessage)dataArray[i]);
				} else if (dataArray[i] instanceof CCUnbilledTrans) {
					ccUnbilled.add((CCUnbilledTrans) dataArray[i]);
				}
			}
			
			super.createBatch(ccStatements.toArray());
			super.createBatch(ccCustomerHeader.toArray());
			super.createBatch(ccAccountLevelMessage.toArray());
			super.createBatch(ccAccountHeader.toArray());
			super.createBatch(ccTransactionDetails.toArray());
			super.createBatch(ccLuckyDraw.toArray());
			super.createBatch(ccAccountBalance.toArray());
			super.createBatch(ccRewardSummary.toArray());
			super.createBatch(ccRateInfo.toArray());
			super.createBatch(ccStatementMessage.toArray());
			super.createBatch(ccPromotionalMessage.toArray());
			super.createBatch(ccAccountTrailer.toArray());
			super.createBatch(ccCustomerHeaderMessage.toArray());
			super.createBatch(ccCustomerTrailer.toArray());
			super.createBatch(ccDualCurrency.toArray());
			super.createBatch(ccFileTrailer.toArray());
			super.createBatch(ccFooterMessage.toArray());
			super.createBatch(ccUnbilled.toArray());
			
			//print cc statement
			String folder = CommonUtils.getMonthlyStatementDir(confProperties)+ File.separator + "CC";
			File dir = new File(folder);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			
			ElixirModel elModel = CommonUtils.getElixirModel(confProperties);
			
			try {
				List<BOSendEmail> listEmail = new ArrayList<BOSendEmail>();
				BOSendEmail email = null;
				
				for (CCCustomerHeader ch : ccCustomerHeader) {
					Long chId = ch.getId();
					
					if (chId != null) {
						Date tglCetak = new Date();
						
						CCAccountHeader ah = getRevCC_05_AH(chId);
						CCAccountBalance ab = getRevCC_09_AB(chId);
						CCRewardSummary bs = getRevCC_10_BS(chId);
						CCRateInfo ri = getRevCC_11_RI(chId);
						
						//get periode
						Date statementDate = ah.getStatementDate();
						Calendar cal = Calendar.getInstance();
						cal.setTime(statementDate);
						
						int year = cal.get(Calendar.YEAR);
						int month = cal.get(Calendar.MONTH);
						month++;
						
						String strMonth = "";
						if (month < 10) {
							strMonth = "0" + month;
						} else {
							strMonth = "" + month;
						}
						String period = year + strMonth;
						
						//create folder periode
						String periodeFolder = folder + File.separator + period;
						dir = new File(periodeFolder);
						if (!dir.exists()) {
							dir.mkdir();
						}
						
						String filename = periodeFolder + File.separator + "e-Statement_" + ah.getCardholderNumber() + "_" + ah.getProductDescription().replaceAll("\\s", "_") + "_" + period + "_" + tglCetak.getTime() + ".pdf";
						
						String encryptedPass = accountListDao.getStatementPassword(ah.getCardholderNumber(), 1);
						String password = null;
						if (encryptedPass != null) {
							password = rsaUtil.rsaDecrypt(encryptedPass);
						} else {
							password = DateUtility.formatDate4(ah.getDateOfBirth());
						}
						
						//check regex
						String logo = "";
						List<BOCCRegex> listCCRegex = ccRegexDao.getAllCCRegex();
						BOCCRegex ccRegex = CCRegexUtils.validatePrefix(listCCRegex, ah.getCardholderNumber());
						if (ccRegex != null) {
							logo = ccRegex.getImageUrl();
						}
						
						String kolek = "";
						if (ah.getCreditQuality() != null) {
							kolek = ah.getCreditQuality();
						}
						
						String ccPromoMesgTop = contentParamDao.getParameterValue("CCEPMT");
						String ccPromoMesgBottom = contentParamDao.getParameterValue("CCEPMB");
						String ccBankNotes = contentParamDao.getParameterValue("CCEBN");
						Properties prop = CreditCardProp.getCCStatamentProp(ch, ah, ab, bs, ri, logo, ccPromoMesgTop, ccPromoMesgBottom, ccBankNotes, 0, kolek, elModel.getProviderUrl());
						
						String fullpath = ReportCCStatement.generateReportCC(elModel, filename, password, prop, null);
						
						String cifKey = getCifKey(ah.getCardholderNumber());
						
						//add to list email
						email = new BOSendEmail();
						email.setAccountNumber(ah.getCardholderNumber());
						email.setCifKey(cifKey);
						email.setFilename(fullpath);
						email.setPeriode(period);
						email.setStatus(0);
						email.setTglCetak(tglCetak);
						email.setCategory(7);
						email.setNamaRekening(ch.getCustomerFullname());
						email.setProductName(ah.getProductDescription());
						int isPassDef = 1;
						if( cifKey != null ){
							CustomerInfo ci = customerInfoDao.getUserByCIFKey(cifKey);
							if( ci != null )
								isPassDef = ci.getIsPasswordDefault();
						}
							
						email.setIsPasswordDefault(isPassDef);
						listEmail.add(email);
					}
				}
				
				//save to list email
				if (listEmail.size() > 0) {
					super.createBatch(listEmail.toArray());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void deleteTemp(String ccNumber) {
		String sql = "DELETE FROM " + CommonCons.DB_SCHEMA + ".CC_UNBILLED_TRANS_TEMP WHERE CREDIT_CARD_NUMBER = ? ";
		List<Object> args = new ArrayList<Object>();
		args.add(Long.parseLong(ccNumber));
		
		template.update(sql, args.toArray());
	}
	
	public CCStatement getRevCC_01_FH(Long chId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_CC_01_FH");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CH_ID", OracleTypes.NUMBER));
		
		Map<String, Object> mValues = caller.execute(chId);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		if (rows.size() > 0) {
			CCStatement fh = serialize(CCStatement.class, rows.get(0));
			
			return fh;
		}
		
		return null;
	}
	
	public List<CCCustomerHeader> getListRevCC_02_CH(String cifNo, Date start, Date end) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_CC_02_CH");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CIF", OracleTypes.VARCHAR),
				new SqlParameter("P_START", OracleTypes.DATE),
				new SqlParameter("P_END", OracleTypes.DATE));
		
		Map<String, Object> mValues = caller.execute(CommonUtils.normalizeCifKey(cifNo), start, end);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		List<CCCustomerHeader> datas = new ArrayList<CCCustomerHeader>();
		for (Map<String, Object> row : rows) {
			CCCustomerHeader ch = serialize(CCCustomerHeader.class, row);
			
			datas.add(ch);
		}
		
		return datas;
	}
	
	public CCCustomerHeaderMessage getRevCC_03_CM(Long chId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_CC_03_CM");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CH_ID", OracleTypes.NUMBER));
		
		Map<String, Object> mValues = caller.execute(chId);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		if (rows.size() > 0) {
			CCCustomerHeaderMessage cm = serialize(CCCustomerHeaderMessage.class, rows.get(0));
			
			return cm;
		}
		
		return null;
	}
	
	public CCAccountLevelMessage getRevCC_04_AM(Long chId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_CC_04_AM");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CH_ID", OracleTypes.NUMBER));
		
		Map<String, Object> mValues = caller.execute(chId);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		if (rows.size() > 0) {
			CCAccountLevelMessage am = serialize(CCAccountLevelMessage.class, rows.get(0));
			
			return am;
		}
		
		return null;
	}
	
	public CCAccountHeader getRevCC_05_AH(Long chId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_CC_05_AH");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CH_ID", OracleTypes.NUMBER));
		
		Map<String, Object> mValues = caller.execute(chId);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		if (rows.size() > 0) {
			CCAccountHeader ah = serialize(CCAccountHeader.class, rows.get(0));
			
			return ah;
		}
		
		return null;
	}
	
	public List<CCTransactionDetails> getListRevCC_06_TD(Long ahId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_CC_06_TD");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_AH_ID", OracleTypes.NUMBER));
		
		Map<String, Object> mValues = caller.execute(ahId);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		List<CCTransactionDetails> datas = new ArrayList<CCTransactionDetails>();
		for (Map<String, Object> row : rows) {
			CCTransactionDetails td = serialize(CCTransactionDetails.class, row);
			
			datas.add(td);
		}
		
		return datas;
	}
	
	public CCDualCurrency getRevCC_07_DC(Long chId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_CC_07_DC");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CH_ID", OracleTypes.NUMBER));
		
		Map<String, Object> mValues = caller.execute(chId);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		if (rows.size() > 0) {
			CCDualCurrency dc = serialize(CCDualCurrency.class, rows.get(0));
			
			return dc;
		}
		
		return null;
	}
	
	public CCLuckyDraw getRevCC_08_LD(Long chId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_CC_08_LD");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CH_ID", OracleTypes.NUMBER));
		
		Map<String, Object> mValues = caller.execute(chId);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		if (rows.size() > 0) {
			CCLuckyDraw ld = serialize(CCLuckyDraw.class, rows.get(0));
			
			return ld;
		}
		
		return null;
	}
	
	public CCAccountBalance getRevCC_09_AB(Long chId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_CC_09_AB");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CH_ID", OracleTypes.NUMBER));
		
		Map<String, Object> mValues = caller.execute(chId);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		if (rows.size() > 0) {
			CCAccountBalance ab = serialize(CCAccountBalance.class, rows.get(0));
			
			return ab;
		}
		
		return null;
	}
	
	public CCRewardSummary getRevCC_10_BS(Long chId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_CC_10_BS");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CH_ID", OracleTypes.NUMBER));
		
		Map<String, Object> mValues = caller.execute(chId);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		if (rows.size() > 0) {
			CCRewardSummary bs = serialize(CCRewardSummary.class, rows.get(0));
			
			return bs;
		}
		
		return null;
	}
	
	public CCRateInfo getRevCC_11_RI(Long chId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_CC_11_RI");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CH_ID", OracleTypes.NUMBER));
		
		Map<String, Object> mValues = caller.execute(chId);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		if (rows.size() > 0) {
			CCRateInfo ri = serialize(CCRateInfo.class, rows.get(0));
			
			return ri;
		}
		
		return null;
	}
	
	public CCStatementMessage getRevCC_12_MS(Long chId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_CC_12_MS");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CH_ID", OracleTypes.NUMBER));
		
		Map<String, Object> mValues = caller.execute(chId);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		if (rows.size() > 0) {
			CCStatementMessage ms = serialize(CCStatementMessage.class, rows.get(0));
			
			return ms;
		}
		
		return null;
	}
	
	public CCPromotionalMessage getRevCC_13_MT(Long chId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_CC_13_MT");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CH_ID", OracleTypes.NUMBER));
		
		Map<String, Object> mValues = caller.execute(chId);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		if (rows.size() > 0) {
			CCPromotionalMessage mt = serialize(CCPromotionalMessage.class, rows.get(0));
			
			return mt;
		}
		
		return null;
	}
	
	public CCFooterMessage getRevCC_14_MF(Long chId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_CC_14_MF");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CH_ID", OracleTypes.NUMBER));
		
		Map<String, Object> mValues = caller.execute(chId);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		if (rows.size() > 0) {
			CCFooterMessage mf = serialize(CCFooterMessage.class, rows.get(0));
			
			return mf;
		}
		
		return null;
	}
	
	public CCAccountTrailer getRevCC_15_AT(Long chId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_CC_15_AT");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CH_ID", OracleTypes.NUMBER));
		
		Map<String, Object> mValues = caller.execute(chId);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		if (rows.size() > 0) {
			CCAccountTrailer at = serialize(CCAccountTrailer.class, rows.get(0));
			
			return at;
		}
		
		return null;
	}
	
	public CCCustomerTrailer getRevCC_16_CT(Long chId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_CC_16_CT");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CH_ID", OracleTypes.NUMBER));
		
		Map<String, Object> mValues = caller.execute(chId);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		if (rows.size() > 0) {
			CCCustomerTrailer ct = serialize(CCCustomerTrailer.class, rows.get(0));
			
			return ct;
		}
		
		return null;
	}
	
	public CCFileTrailer getRevCC_17_FT(Long chId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_CC_17_FT");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CH_ID", OracleTypes.NUMBER));
		
		Map<String, Object> mValues = caller.execute(chId);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		if (rows.size() > 0) {
			CCFileTrailer ft = serialize(CCFileTrailer.class, rows.get(0));
			
			return ft;
		}
		
		return null;
	}
	
	public boolean exists(String ccNo, Date startDate, Date endDate) {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_CC_STATEMENT_EXISTS");
		caller.declareParameters(
				new SqlOutParameter("PO_EXISTS", OracleTypes.INTEGER),
				new SqlParameter("P_CC_NO", OracleTypes.VARCHAR),
				new SqlParameter("P_START_DATE", OracleTypes.DATE),
				new SqlParameter("P_END_DATE", OracleTypes.DATE)
				);
		
		Map<String, Object> mValues = caller.execute(ccNo, startDate, endDate);
		
		return ((Integer)mValues.get("PO_EXISTS")).intValue() > 0;
	}
	
	public String getCifKey(String ccNo){
		SimpleJdbcCall caller = new SimpleJdbcCall(template)
				.withSchemaName(CommonCons.DB_SCHEMA)
				.withProcedureName("SP_GET_CIF_KEY_CC")
				.declareParameters(
						new SqlParameter("P_CC_NO", OracleTypes.VARCHAR),
						new SqlOutParameter("RES", OracleTypes.NVARCHAR));
		
		Map<String, Object> mValues = caller.execute(Long.parseLong(ccNo));
		
		String res = (String)mValues.get("RES");
		if (res != null && res.length() > 0) {
			return res;
		}
		
		return null;
	}
}
