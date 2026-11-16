package com.optima.oraclejdbc.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisp.schemas.soa.wsdl.service.wbst_inquiry_accountbalance.operationimpl.services.wbst__inquiry__accountbalance.processes.WBST_Inquiry_AccountBalanceStub;
import com.nisp.schemas.soa.wsdl.service.wbst_inquiry_recentaccountactivity.operationimpl.services.wbst__inquiry__recentaccountactivity.processes.WBST_Inquiry_RecentAccountActivityStub;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_accountbalance.ConsDepositBalance_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_accountbalance.REQConsumerSequence_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_accountbalance.REQConsumer_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_accountbalance.RESConsumerSequence_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_accountbalance.RESConsumer_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_accountbalance.RqDetail_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_accountbalance.RsDetail_type0;
import com.ocbc.schemas.soa.emf.account.aggregates.AccountBalancelnquiryKey_type0;
import com.ocbc.schemas.soa.emf.account.aggregates.AlternateAccountKeyDetail_type0;
import com.ocbc.schemas.soa.emf.account.aggregates.DepositAccountBalanceDetails_type0;
import com.ocbc.schemas.soa.emf.account.aggregates.TransactionStatementDetails_type0;
import com.ocbc.schemas.soa.emf.common.body.ErrorDetail_type0;
import com.ocbc.schemas.soa.emf.common.body.ProviderError_type0;
import com.ocbc.schemas.soa.emf.common.header.ClientControl_type0;
import com.ocbc.schemas.soa.emf.common.header.ClientDetail_type0;
import com.ocbc.schemas.soa.emf.common.header.CommonDetailChoice_type2;
import com.ocbc.schemas.soa.emf.common.header.CommonDetailChoice_type3;
import com.ocbc.schemas.soa.emf.common.header.CommonDetail_type0;
import com.ocbc.schemas.soa.emf.common.header.RecordControlChoice_type0;
import com.ocbc.schemas.soa.emf.common.header.RecordControl_type0;
import com.ocbc.schemas.soa.emf.common.header.RegionDetail_type0;
import com.ocbc.schemas.soa.emf.common.header.RemarkDetail_type0;
import com.ocbc.schemas.soa.emf.common.header.RqNature_type0;
import com.ocbc.schemas.soa.emf.common.header.UserDetail_type0;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_accountbalance.ServiceBody;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_accountbalance.ServiceEnvelope;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_accountbalance.ServiceEnvelopeE;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_accountbalance.ServiceHeader;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_accountbalance.ServiceHeaderChoice_type1;
import com.optima.oraclejdbc.api.ReturnData;
import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.daoimpl.SystemParameterDao;
import com.optima.oraclejdbc.elixirprop.DailyStatementProp;
import com.optima.oraclejdbc.model.AccountList;
import com.optima.oraclejdbc.model.DailyStatementTemp;
import com.optima.oraclejdbc.model.ElixirModel;
import com.optima.oraclejdbc.model.UserInfo;
import com.optima.oraclejdbc.requestinput.ByDateHistoryInput;
import com.optima.oraclejdbc.requestinput.ByRecentTransactionsInput;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.response.dailystatement.DailyStatementDetailResponse;
import com.optima.oraclejdbc.response.dailystatement.DailyStatementResponse;
import com.optima.oraclejdbc.response.dailystatement.DailyStatementSummaryResponse;
import com.optima.oraclejdbc.response.dailystatement.DepositAccountResponse;
import com.optima.oraclejdbc.response.dailystatement.PointResponse;
import com.optima.oraclejdbc.service.AccountListRegisterService;
import com.optima.oraclejdbc.service.DailyStatementService;
import com.optima.oraclejdbc.tibco.accountbalance.AccountBalanceServiceEnvelopeE;
import com.optima.oraclejdbc.tibco.accountbalance.RecentAccountActivityServiceEnvelopeE;
import com.optima.oraclejdbc.tibco.customerinfo.CustomerInfoRequest;
import com.optima.oraclejdbc.utils.CSVUtils;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.DateUtility;
import com.optima.oraclejdbc.utils.RsaUtil;
import com.optima.oraclejdbc.utils.StringUtility;
import com.optima.oraclejdbc.utils.StubUtil;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@RestController
@RequestMapping(value="/dailystatement")
public class DailyStatementController {
	
	private static final Logger logger = Logger.getLogger(DailyStatementController.class);
	
	@Autowired
	DailyStatementService service;
	
	@Autowired
	AccountListRegisterService accountListService;
	
	@Autowired
	StubUtil stubUtil;
	
	@Autowired
	CustomerInfoRequest customerInfo;
	
	@Autowired
	RsaUtil rsaUtil;
	
	@Resource(name="confProp")
	private java.util.Properties confProperties;
	
	@Autowired
	SystemParameterDao sysParamDao;
		
	@RequestMapping(value="/ministatement", method=RequestMethod.POST)
	public Response getMiniStatement(@RequestParam String userId, @RequestParam String cifKey, @RequestParam String accountNumber,
			@RequestParam String currency, @RequestParam(defaultValue="DESC") String sortType,
			@RequestParam String count, @RequestParam String monthsBefore
			, @RequestParam(defaultValue="") String accountType, @RequestParam(defaultValue="") String branchCode){
		
		try{
			int iCount = Integer.parseInt(count);
			int iMonthsBefore = Integer.parseInt(monthsBefore);
			AccountList account = accountListService.getAccountListByAccountNumber(accountNumber, currency);		
			if( account != null ){
				if( accountType.length() == 0 )
					if( account.getAccountType() != null )
						accountType = account.getAccountType().toString();
				
				if( branchCode.length() == 0 )
					branchCode = account.getBranchCode();
			}	
			
			Response res = getRecentAccountActivity("000", userId, cifKey, accountType,
					branchCode, accountNumber, currency, 1, iCount, sortType, null);
			if( res.getResultCode() == ResponseErrorCode.SUCCESS ){
				@SuppressWarnings("unchecked")
				List<DailyStatementResponse> recentData = (List<DailyStatementResponse>)res.getData();
				if( recentData.size() < iCount ){
					Calendar now = Calendar.getInstance();
					Calendar monthsAgo = Calendar.getInstance();
					monthsAgo.add(Calendar.MONTH, -iMonthsBefore);
					List<DailyStatementResponse> historyData = service.getMiniStatement(accountNumber, currency, monthsAgo.getTime(), now.getTime(), 1, iCount-recentData.size());
					recentData.addAll(historyData);
				}
				res.setData(recentData);
			}
			return res;
		}catch(Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
			
	}
	
	@RequestMapping(value = "getpointsummary", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getPointSummary(@RequestParam String userId, @RequestParam String cifKey) {
		try {
			Response res = new Response();
			//stub
			String strContextPath = CommonUtils.getContextPath(confProperties);
			WBST_Inquiry_AccountBalanceStub stub = new WBST_Inquiry_AccountBalanceStub(strContextPath+"Services/WBST_Inquiry_AccountBalance/Processes/MainRequestResponse_HTTP");
//			WBST_Inquiry_AccountBalanceStub stub = new WBST_Inquiry_AccountBalanceStub(strContextPath+"services/AccountBalance_MainRequestResponse__HTTP");
			ServiceEnvelopeE serviceEnvelopeE = AccountBalanceServiceEnvelopeE.instance();
			AccountBalanceServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
			AccountBalanceServiceEnvelopeE.setUserId(serviceEnvelopeE, userId);
			AccountBalanceServiceEnvelopeE.setSetOperationCode(serviceEnvelopeE, "200");
			AccountBalanceServiceEnvelopeE.setSetBonusPGM(serviceEnvelopeE, "POINT");
			
			ServiceEnvelopeE response = (ServiceEnvelopeE)stubUtil.request(stub, serviceEnvelopeE);
			if( response == null ){
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				List<String> errMsg = new ArrayList<String>();
				errMsg.add(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				res.setErrMsg(errMsg);
				return res;
			}
			ServiceEnvelope serviceEnvelope = response.getServiceEnvelope();
			
			if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
				res.setResultCode(ResponseErrorCode.SUCCESS);
				res.setMessage(ResponseErrorCode.MSG_SUCCESS);
				if( serviceEnvelope.getServiceBody().getRsDetail()
						.getRESConsumer().isRESConsumerSequence_type0Specified() ){
					List<PointResponse> data = service.getPointsSummary(cifKey,serviceEnvelope.getServiceBody().getRsDetail()
							.getRESConsumer().getRESConsumerSequence_type0());
					
					res.setData(data);
				}else{
					res.setData(new Object[0]);
				}
				
				
			}else{
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				ErrorDetail_type0 errorDetail_type0[] = serviceEnvelope.getServiceBody().getError().getErrorDetail();
				ArrayList<String> errorCodes = new ArrayList<String>();
				for (ErrorDetail_type0 err : errorDetail_type0) {
					errorCodes.add(err.getErrorCode()+" : "+err.getErrorDescription());
				}
				res.setErrMsg(errorCodes);
			}
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}

		
	}
	
	@RequestMapping(value = "getaccountdetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getAccountDetail(@RequestParam String userId, @RequestParam String cifKey, 
			@RequestParam String accountNumber, @RequestParam String accountType, @RequestParam String currency
			, @RequestParam int mcBit){		
		try {
			Response res = new Response();
			//stub
			String strContextPath = CommonUtils.getContextPath(confProperties);
			WBST_Inquiry_AccountBalanceStub stub = new WBST_Inquiry_AccountBalanceStub(strContextPath+"Services/WBST_Inquiry_AccountBalance/Processes/MainRequestResponse_HTTP");
			
			ServiceEnvelopeE serviceEnvelopeE = AccountBalanceServiceEnvelopeE.instance();
			AccountBalanceServiceEnvelopeE.setUserId(serviceEnvelopeE, userId);
			AccountBalanceServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
			AccountBalanceServiceEnvelopeE.setAccountNo1(serviceEnvelopeE, accountNumber);
			AccountBalanceServiceEnvelopeE.setAccountType(serviceEnvelopeE, accountType);
			AccountBalanceServiceEnvelopeE.setBranchCode(serviceEnvelopeE, "01010");
			AccountBalanceServiceEnvelopeE.setCurrencyType(serviceEnvelopeE, currency);
			AccountBalanceServiceEnvelopeE.setSetOperationCode(serviceEnvelopeE, "100");
			AccountBalanceServiceEnvelopeE.setMcBit(serviceEnvelopeE, mcBit==1);
			AccountBalanceServiceEnvelopeE.enableAccountDetail(serviceEnvelopeE);
			
			ServiceEnvelopeE response = (ServiceEnvelopeE)stubUtil.request(stub, serviceEnvelopeE);
			if( response == null ){
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				res.setData(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				return res;
			}
			
			//response
			ServiceEnvelope serviceEnvelope = response.getServiceEnvelope();
			
//			ServiceEnvelope serviceEnvelope = new ServiceEnvelope();
//			ServiceBody serviceBody = new ServiceBody();
//			
//			//response
//			
//			RsDetail_type0 rsDetail_type0 = new RsDetail_type0();
//			RESConsumer_type0 resConsumer_type0 = new RESConsumer_type0();
//			
//			RESConsumerSequence_type0 resConsumerSequence_type0[] = new RESConsumerSequence_type0[1];
//			RESConsumerSequence_type0 resConsumerSequence = new RESConsumerSequence_type0();
//			
//			ConsDepositBalance_type0 consDepositBalance_type0[] = new ConsDepositBalance_type0[1];
//			ConsDepositBalance_type0 consDepositBalance = new ConsDepositBalance_type0();
//			
//			AccountBalancelnquiryKey_type0 accountBalancelnquiryKey = new AccountBalancelnquiryKey_type0();
//			
//			AlternateAccountKeyDetail_type0 alternateAccountKeyDetail_type0[] = new AlternateAccountKeyDetail_type0[1];
//			AlternateAccountKeyDetail_type0 alternateAccountKeyDetail = new AlternateAccountKeyDetail_type0();
//			
//			consDepositBalance.setResponseCode("00");
//			consDepositBalance.setStatus("00");
//			
//			alternateAccountKeyDetail.setAccountNo1("10800010778");
//			alternateAccountKeyDetail.setAccountType("D");
//			alternateAccountKeyDetail.setCurrencyType("IDR");
//			alternateAccountKeyDetail.setMCBit(false);
//			alternateAccountKeyDetail.setBranchCode("01010");
//			alternateAccountKeyDetail_type0[0] = alternateAccountKeyDetail;
//			accountBalancelnquiryKey.setAlternateAccountKeyDetail(alternateAccountKeyDetail);
//			accountBalancelnquiryKey.setProductType("GRPRG01002");
//			consDepositBalance.setAccountBalancelnquiryKey(accountBalancelnquiryKey);
//			
//			DepositAccountBalanceDetails_type0 depositAccountBalanceDetails_type0 = new DepositAccountBalanceDetails_type0();
//			depositAccountBalanceDetails_type0.setAvailableBalance(new BigDecimal(250000));
//			depositAccountBalanceDetails_type0.setHoldBalance(new BigDecimal(5000));
//			depositAccountBalanceDetails_type0.setOverDraftAmount(new BigDecimal(10000));
//			depositAccountBalanceDetails_type0.setLedgerBalance(new BigDecimal(200000));
//			consDepositBalance.setDepositAccountBalanceDetails(depositAccountBalanceDetails_type0);
//			
//			consDepositBalance_type0[0] = consDepositBalance;
//			
//			resConsumerSequence.setConsDepositBalance(consDepositBalance);
//			resConsumerSequence_type0[0] = resConsumerSequence;
//			
//			resConsumer_type0.setRESConsumerSequence_type0(resConsumerSequence_type0);
//			rsDetail_type0.setRESConsumer(resConsumer_type0);
//			serviceBody.setRsDetail(rsDetail_type0);
//			serviceEnvelope.setServiceBody(serviceBody);
			
			if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
				res.setResultCode(ResponseErrorCode.SUCCESS);
				res.setMessage(ResponseErrorCode.MSG_SUCCESS);
//				res.setData(new DailyStatementDetailResponse(serviceEnvelope.getServiceBody().getRsDetail().getRESConsumer().getRESConsumerSequence_type0()[0]
//						.getConsDepositBalance().getDepositAccountBalanceDetails()));
				List<DailyStatementSummaryResponse> data = service.getDailyStatementSummary(serviceEnvelope.getServiceBody().getRsDetail()
						.getRESConsumer().getRESConsumerSequence_type0());
				
				res.setData(data);
				
			}else{
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				ErrorDetail_type0 errorDetail_type0[] = serviceEnvelope.getServiceBody().getError().getErrorDetail();
				ArrayList<String> errorCodes = new ArrayList<String>();
				for (ErrorDetail_type0 err : errorDetail_type0) {
					errorCodes.add(err.getErrorCode()+" : "+err.getErrorDescription());
				}
				res.setErrMsg(errorCodes);
			}
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);			
		}
		
	}
	
	@RequestMapping(value="/history", method=RequestMethod.POST)
	public Response getTransactionHistoryStatement(@Valid @ModelAttribute ByDateHistoryInput input, BindingResult bindingResult){
		try{
			Response response = new Response();
			
			List<String> errMsg = new ArrayList<String>();
			if( !CommonUtils.validateInput(bindingResult, errMsg) ){
				response.setResultCode(ResponseErrorCode.INVALID_REQUEST_INPUT);
				response.setMessage(ResponseErrorCode.MSG_INVALID_REQUEST_INPUT);
				response.setErrMsg(errMsg);
				return response;
			}else{			
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date startDate = null;
				Date endDate = null;
				try{
					startDate = sdf.parse(input.getStart());
				}catch (ParseException e) {
					errMsg.add("start: invalid date format, should be in dd/MM/yyyy format");
				}
				
				try{
					endDate = sdf.parse(input.getEnd());
				}catch (ParseException e) {
					errMsg.add("end: invalid date format, should be in dd/MM/yyyy format");
				}
				
				String strPage = input.getPage();
				if( strPage != null )
					CommonUtils.checkPositiveInteger("page", strPage, errMsg);
				
				String strCountPerPage = input.getCountPerPage();
				if( strCountPerPage != null )
					CommonUtils.checkPositiveInteger("countPerPage", strCountPerPage, errMsg);
				
				String sortBy = input.getSortBy();
				if( sortBy != null ){
					sortBy = sortBy.toLowerCase();
					if( !sortBy.equals("transaction date") && !sortBy.equals("value date") && !sortBy.equals("description") 
							&& !sortBy.equals("debet") && !sortBy.equals("kredit") && !sortBy.equals("balance") ){
						errMsg.add("sortBy: invalid value, valid value are \"transaction date\", \"value date\", \"description\", "
								+ "\"debet\", \"kredit\", or \"balance\"");
					}
				}else
					sortBy = "default";
				
				String sortType = input.getSortType();
				if( sortType != null ){
					sortType = sortType.toUpperCase();
					if( !sortType.equals("ASC") && !sortType.equals("DESC") ){
						errMsg.add("sortType: invalid value, valid value are \"ASC\" or \"DESC\"");
					}
				}else
					sortType = "DESC";
				
				if( errMsg.size() > 0 ){
					response.setResultCode(ResponseErrorCode.INVALID_REQUEST_INPUT);
					response.setMessage(ResponseErrorCode.MSG_INVALID_REQUEST_INPUT);
					response.setErrMsg(errMsg);
					return response;
				}
				
				Integer page = null;
				if( strPage != null )
					page = Integer.parseInt(input.getPage());
				else
					page = 1;
				Integer countPerPage = null;
				if( strCountPerPage != null )
					countPerPage = Integer.parseInt(strCountPerPage);
				else
					countPerPage = Integer.MAX_VALUE;
				String accountNumber = input.getAccountNumber();
				String currency = input.getCurrency();
				Calendar now = Calendar.getInstance();
				String searchKeyWord = input.getSearchKeyWord();
				int totalHistory = service.getTotalCountByDate(accountNumber, input.getCurrency(), startDate, endDate, searchKeyWord);
				Calendar calEnd = Calendar.getInstance();
				calEnd.setTime(endDate);
				if( now.get(Calendar.YEAR) == calEnd.get(Calendar.YEAR)
						&& now.get(Calendar.MONTH) == calEnd.get(Calendar.MONTH)
						&& now.get(Calendar.DATE) == calEnd.get(Calendar.DATE)){
					AccountList account = accountListService.getAccountListByAccountNumber(accountNumber, currency);
					String accountType = input.getAccountType();
					accountType = accountType==null?"":accountType;
					String branchCode = input.getBranchCode();
					branchCode = branchCode==null?"":branchCode;
					if( account != null ){
						if( accountType.length() == 0 )
							if( account.getAccountType() != null )
								accountType = account.getAccountType().toString();					
						if( branchCode.length() == 0 )
							branchCode = account.getBranchCode();
						else
							branchCode = "";
					}
					
					String operationCode;
					if( currency.equalsIgnoreCase("POINT") ){
						operationCode="100";
					}else{
						operationCode="000";
					}
					
					if( sortType.equalsIgnoreCase("ASC") ){
						int start = (page-1)*countPerPage+1;
						int end = page*countPerPage;
						List<DailyStatementResponse> historyData = service.getByDateStatement(accountNumber, input.getCurrency(), startDate, endDate, start, end, sortBy, "ASC", searchKeyWord );
						int historySize = historyData.size();
						response = getRecentAccountActivity(operationCode, input.getUserId(), input.getCifKey(), accountType,
								branchCode, accountNumber, currency, 1, countPerPage-historySize, "ASC", searchKeyWord);
						if( response.getResultCode() == ResponseErrorCode.SUCCESS ){
							@SuppressWarnings("unchecked")
							List<DailyStatementResponse> recentData = (List<DailyStatementResponse>)response.getData();
							historyData.addAll(recentData);
							response.setData(historyData);
							response.setTotalAllData(totalHistory+response.getTotalAllData());
							response.setResultCode(ResponseErrorCode.SUCCESS);
							response.setMessage(ResponseErrorCode.MSG_SUCCESS);
						}					
					}else{					
						response = getRecentAccountActivity(operationCode, input.getUserId(), input.getCifKey(), accountType,
								branchCode, accountNumber, currency, (page-1)*countPerPage+1, countPerPage, "DESC", searchKeyWord);
						if( response.getResultCode() == ResponseErrorCode.SUCCESS ){
							@SuppressWarnings("unchecked")
							List<DailyStatementResponse> recentData = (List<DailyStatementResponse>)response.getData();
							if( recentData.size() < countPerPage ){
								int start = 1;
								int end = 1;
								if( recentData.size() == 0 ){
									start = (page-1)*countPerPage-response.getTotalAllData()+1;
									end = page*countPerPage - response.getTotalAllData();
								}else{
									end = countPerPage - recentData.size();
								}
								List<DailyStatementResponse> historyData = service.getByDateStatement(accountNumber, input.getCurrency(), startDate, endDate, start, end, sortBy, sortType, searchKeyWord );
								recentData.addAll(historyData);
							}
							response.setData(recentData);
							response.setTotalAllData(response.getTotalAllData()+totalHistory);
							response.setResultCode(ResponseErrorCode.SUCCESS);
							response.setMessage(ResponseErrorCode.MSG_SUCCESS);
						}
					}
					
				}else{
					int start = (page-1)*countPerPage+1;
					int end = page*countPerPage;
					response.setData(service.getByDateStatement(accountNumber, input.getCurrency(), startDate, endDate, start, end, sortBy, sortType, searchKeyWord ));
					response.setTotalAllData(totalHistory);
					response.setResultCode(ResponseErrorCode.SUCCESS);
					response.setMessage(ResponseErrorCode.MSG_SUCCESS);
				}
				
				return response;
			}
		}catch(Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
	}
	
	@RequestMapping(value="/recent", method=RequestMethod.POST)
	public Serializable getTransactionHistoryStatement(@Valid @ModelAttribute ByRecentTransactionsInput input, BindingResult bindingResult){
		try{
			Response response = new Response();
			
			List<String> errMsg = new ArrayList<String>();
			if( !CommonUtils.validateInput(bindingResult, errMsg) ){
				response.setResultCode(ResponseErrorCode.INVALID_REQUEST_INPUT);
				response.setMessage(ResponseErrorCode.MSG_INVALID_REQUEST_INPUT);
				response.setErrMsg(errMsg);
				return response;
			}else{			
				String strCount = input.getCountLastTransactions();
				CommonUtils.checkPositiveInteger("countLastTransactions", strCount, errMsg);
				
				String strPage = input.getPage();
				if( strPage != null )
					CommonUtils.checkPositiveInteger("page", strPage, errMsg);
				
				String strCountPerPage = input.getCountPerPage();
				if( strCountPerPage != null )
					CommonUtils.checkPositiveInteger("countPerPage", strCountPerPage, errMsg);
				
				String sortBy = input.getSortBy();
				if( sortBy != null ){
					sortBy = sortBy.toLowerCase();
					if( !sortBy.equals("transaction date") && !sortBy.equals("value date") && !sortBy.equals("description") 
							&& !sortBy.equals("debet") && !sortBy.equals("credit") && !sortBy.equals("balance") ){
						errMsg.add("sortBy: invalid value, valid value are \"transaction date\", \"value date\", \"description\", "
								+ "\"debet\", \"credit\", or \"balance\"");
					}
				}else
					sortBy = "default";
				
				String sortType = input.getSortType();
				if( sortType != null ){
					sortType = sortType.toUpperCase();
					if( !sortType.equals("ASC") && !sortType.equals("DESC") ){
						errMsg.add("sortType: invalid value, valid value are \"ASC\" or \"DESC\"");
					}
				}else
					sortType = "DESC";
				
				if( errMsg.size() > 0 ){
					response.setResultCode(ResponseErrorCode.INVALID_REQUEST_INPUT);
					response.setMessage(ResponseErrorCode.MSG_INVALID_REQUEST_INPUT);
					response.setErrMsg(errMsg);
					return response;
				}
				
				Integer page = null;
				if( strPage != null )
					page = Integer.parseInt(input.getPage());
				
				Integer countPerPage = null;
				if( strCountPerPage != null )
					countPerPage = Integer.parseInt(strCountPerPage);
				String accountNumber = input.getAccountNumber();
				String currency = input.getCurrency();
				int count = Integer.parseInt(strCount);
				AccountList account = accountListService.getAccountListByAccountNumber(accountNumber, currency);
				String accountType = input.getAccountType();
				accountType = accountType==null?"":accountType;
				String branchCode = input.getBranchCode();
				branchCode = branchCode==null?"":branchCode;
				if( account != null ){
					if( accountType.length() == 0 )
						if( account.getAccountType() != null )
							accountType = account.getAccountType().toString();					
					if( branchCode.length() == 0 )
						branchCode = account.getBranchCode();
					else
						branchCode = "";
				}
				String searchKeyWord = input.getSearchKeyWord();
				String operationCode;
				if( currency.equalsIgnoreCase("POINT") ){
					operationCode="100";
				}else{
					operationCode="000";
				}
				response = getRecentAccountActivity(operationCode, input.getUserId(), input.getCifKey(), accountType,
						branchCode, accountNumber, currency, 1, count, sortType, searchKeyWord);
				if( response.getResultCode() == ResponseErrorCode.SUCCESS ){
					int recentTotalData = response.getTotalAllData();
					List<DailyStatementResponse> result = new ArrayList<DailyStatementResponse>();
					@SuppressWarnings("unchecked")
					List<DailyStatementResponse> recentData = (List<DailyStatementResponse>)response.getData();
					if( recentTotalData < count ){
						int start = 1;
						int end = count - recentTotalData;
						
						List<DailyStatementResponse> historyData = service.getByRecentTransactionsStatement(accountNumber, input.getCurrency(), end, start, end, sortBy, sortType, searchKeyWord );
						if( sortType.equalsIgnoreCase("DESC") ){
							result.addAll(recentData);
							result.addAll(historyData);
						}else{
							result.addAll(historyData);
							result.addAll(recentData);
						}
					}else
						result.addAll(recentData);
					int start = 0;
					if( page == null )
						page = 1;
					
					int end = Integer.MAX_VALUE;
					if( countPerPage != null ){
						end = page*countPerPage-1;
						start = (page-1)*countPerPage;
					}
					
					List<DailyStatementResponse> resData = new ArrayList<DailyStatementResponse>();
					for(int i=0;i<result.size();i++){
						if( i>=start && i<=end )
							resData.add(result.get(i));
					}
					
					response.setData(resData);
					response.setTotalAllData(result.size());
				}
				return response;
			}
		}catch (Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
	}
	
	@RequestMapping(value="/print-history", method=RequestMethod.POST)
	public void printTransactionHistoryStatement(@Valid @ModelAttribute ByDateHistoryInput input, 
			@RequestParam String filename, @RequestParam int isPrint, @RequestParam(defaultValue="0") int oneStep,
			@RequestParam(defaultValue="") String productName,
			HttpServletResponse servletResponse, BindingResult bindingResult
			) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			Response response = new Response();		
			List<String> errMsg = new ArrayList<String>();
			if( !CommonUtils.validateInput(bindingResult, errMsg) ){
				response.setResultCode(ResponseErrorCode.INVALID_REQUEST_INPUT);
				response.setMessage(ResponseErrorCode.MSG_INVALID_REQUEST_INPUT);
				response.setErrMsg(errMsg);
				PrintWriter out = servletResponse.getWriter();				
				out.print(mapper.writeValueAsString(response));
				return;
			}
			
			ElixirModel model = CommonUtils.getElixirModel(confProperties);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date startDate = null;
			Date endDate = null;
			try{
				startDate = sdf.parse(input.getStart());
			}catch (ParseException e) {
				errMsg.add("start: invalid date format, should be in dd/MM/yyyy format");
			}
			
			try{
				endDate = sdf.parse(input.getEnd());
			}catch (ParseException e) {
				errMsg.add("end: invalid date format, should be in dd/MM/yyyy format");
			}
			
			String strPage = input.getPage();
			if( strPage != null )
				CommonUtils.checkPositiveInteger("page", strPage, errMsg);
			
			String strCountPerPage = input.getCountPerPage();
			if( strCountPerPage != null )
				CommonUtils.checkPositiveInteger("countPerPage", strCountPerPage, errMsg);
			
			String sortBy = input.getSortBy();
			if( sortBy != null ){
				sortBy = sortBy.toLowerCase();
				if( !sortBy.equals("transaction date") && !sortBy.equals("value date") && !sortBy.equals("description") 
						&& !sortBy.equals("debet") && !sortBy.equals("kredit") && !sortBy.equals("balance") ){
					errMsg.add("sortBy: invalid value, valid value are \"transaction date\", \"value date\", \"description\", "
							+ "\"debet\", \"kredit\", or \"balance\"");
				}
			}else
				sortBy = "default";
			
			String sortType = input.getSortType();
			if( sortType != null ){
				sortType = sortType.toUpperCase();
				if( !sortType.equals("ASC") && !sortType.equals("DESC") ){
					errMsg.add("sortType: invalid value, valid value are \"ASC\" or \"DESC\"");
				}
			}else
				sortType = "DESC";
			
			if( errMsg.size() > 0 ){
				response.setResultCode(ResponseErrorCode.INVALID_REQUEST_INPUT);
				response.setMessage(ResponseErrorCode.MSG_INVALID_REQUEST_INPUT);
				response.setErrMsg(errMsg);
				PrintWriter out = servletResponse.getWriter();				
				out.print(mapper.writeValueAsString(response));
				return;
			}
			
			String accountNumber = input.getAccountNumber();
			Calendar now = Calendar.getInstance();
			String searchKeyWord = input.getSearchKeyWord();
			Calendar calEnd = Calendar.getInstance();
			calEnd.setTime(endDate);
			String currency = input.getCurrency();
			if( now.get(Calendar.YEAR) == calEnd.get(Calendar.YEAR)
					&& now.get(Calendar.MONTH) == calEnd.get(Calendar.MONTH)
					&& now.get(Calendar.DATE) == calEnd.get(Calendar.DATE)){
				AccountList account = accountListService.getAccountListByAccountNumber(accountNumber, input.getCurrency());
				String accountType = input.getAccountType();
				accountType = accountType==null?"":accountType;
				String branchCode = input.getBranchCode();
				branchCode = branchCode==null?"":branchCode;
				if( account != null ){
					if( accountType.length() == 0 )
						if( account.getAccountType() != null )
							accountType = account.getAccountType().toString();					
					if( branchCode.length() == 0 )
						branchCode = account.getBranchCode();
					else
						branchCode = "";
				}
				String operationCode;
				if( input.getCurrency().equalsIgnoreCase("POINT") ){
					operationCode="100";
				}else{
					operationCode="000";
				}
				
				if( sortType.equalsIgnoreCase("ASC") ){
					//int start = (page-1)*countPerPage+1;
					//int end = page*countPerPage;
					List<DailyStatementResponse> historyData = service.getByDateStatement(accountNumber, input.getCurrency(), startDate, endDate, null, null, sortBy, "ASC", searchKeyWord );
					Response recentResponse = getRecentAccountActivity(operationCode, input.getUserId(), input.getCifKey(), accountType,
							branchCode, accountNumber, currency, 1, Integer.MAX_VALUE, "ASC", searchKeyWord);
					if( recentResponse.getResultCode() == ResponseErrorCode.SUCCESS ){
						@SuppressWarnings("unchecked")
						List<DailyStatementResponse> recentData = (List<DailyStatementResponse>)recentResponse.getData();
						historyData.addAll(recentData);					
					}
					List<DailyStatementTemp> listTemp = new ArrayList<DailyStatementTemp>();
					DailyStatementTemp temp = null;
					for (DailyStatementResponse item : historyData) {
						temp = new DailyStatementTemp();
						
						temp.setAccountNumber(accountNumber);
						temp.setTransactionDate(item.getTransactionDate());
						temp.setValueDate(item.getValueDate());
						temp.setTransCode(item.getTranCode());
						String desc = item.getTransDescription();
						desc = desc.replaceAll("<br />", "\n").replaceAll("<b>", "").replaceAll("</b>", "");						
						temp.setTransDescription(desc);
						temp.setTrxDescription(item.getTrxDescription());
						temp.setSign(item.getSign().toString());
						temp.setAmount(item.getAmount());
						temp.setBalance(item.getBalance());
						temp.setRemark1(item.getRemark1());
						temp.setRemark2(item.getRemark2());
						temp.setRemark3(item.getRemark3());
						temp.setRemark4(item.getRemark4());
						
						listTemp.add(temp);
					}
					if( oneStep == 1 && listTemp.size() == 0 ){
						servletResponse.getOutputStream().write(new byte[0]);
						return;
					}
					service.insertTemporary(listTemp);
				} else {
					List<DailyStatementResponse> recentData;
					Response recentResponse = getRecentAccountActivity(operationCode, input.getUserId(), input.getCifKey(), accountType,
							branchCode, accountNumber, currency, 1, Integer.MAX_VALUE, "ASC", searchKeyWord);
					if( recentResponse.getResultCode() == ResponseErrorCode.SUCCESS ){
						recentData = (List<DailyStatementResponse>)recentResponse.getData();
						List<DailyStatementResponse> historyData = service.getByDateStatement(accountNumber, input.getCurrency(), startDate, endDate, null, null, sortBy, "DESC", searchKeyWord );
						recentData.addAll(historyData);					
					}else{
						recentData = new ArrayList<DailyStatementResponse>();
					}
					
					List<DailyStatementTemp> listTemp = new ArrayList<DailyStatementTemp>();
					DailyStatementTemp temp = null;
					for (DailyStatementResponse item : recentData) {
						temp = new DailyStatementTemp();
						
						temp.setAccountNumber(accountNumber);
						temp.setTransactionDate(item.getTransactionDate());
						temp.setValueDate(item.getValueDate());
						temp.setTransCode(item.getTranCode());
						temp.setTransDescription(item.getTransDescription());
						temp.setTrxDescription(item.getTrxDescription());
						temp.setSign(item.getSign().toString());
						temp.setAmount(item.getAmount());
						temp.setBalance(item.getBalance());
						temp.setRemark1(item.getRemark1());
						temp.setRemark2(item.getRemark2());
						temp.setRemark3(item.getRemark3());
						temp.setRemark4(item.getRemark4());
						
						listTemp.add(temp);
					}
					if( oneStep == 1 && listTemp.size() == 0 ){
						servletResponse.getOutputStream().write(new byte[0]);
						return;
					}
					service.insertTemporary(listTemp);
				}
			}else{
				//int start = (page-1)*countPerPage+1;
				//int end = page*countPerPage;
				List<DailyStatementResponse> historyData = service.getByDateStatement(accountNumber, input.getCurrency(), startDate, endDate, null, null, sortBy, sortType, searchKeyWord);
				List<DailyStatementTemp> listTemp = new ArrayList<DailyStatementTemp>();
				DailyStatementTemp temp = null;
				for (DailyStatementResponse item : historyData) {
					temp = new DailyStatementTemp();
					
					temp.setAccountNumber(accountNumber);
					temp.setTransactionDate(item.getTransactionDate());
					temp.setValueDate(item.getValueDate());
					temp.setTransCode(item.getTranCode());
					temp.setTransDescription(item.getTransDescription());
					temp.setTrxDescription(item.getTrxDescription());
					temp.setSign(item.getSign().toString());
					temp.setAmount(item.getAmount());
					temp.setBalance(item.getBalance());
					temp.setRemark1(item.getRemark1());
					temp.setRemark2(item.getRemark2());
					temp.setRemark3(item.getRemark3());
					temp.setRemark4(item.getRemark4());
					
					listTemp.add(temp);
				}
				if( oneStep == 1 && listTemp.size() == 0 ){
					servletResponse.getOutputStream().write(new byte[0]);
					return;
				}
				service.insertTemporary(listTemp);
			}
			
			//print
			String elHostname = model.getHostname();
			int elPort = model.getPort();
			String elUsername = model.getUsername();
			String elPassword = model.getPassword();
			ERSClient ersClient = new ERSClient(elHostname, elPort, elUsername, elPassword);
			
			Response custRes = customerInfo.getUserInfo(input.getCifKey());
			UserInfo userInfo = null;
			if (custRes.getResultCode() == ResponseErrorCode.SUCCESS) {
				userInfo = (UserInfo) custRes.getData();
			}
			
					
//			java.util.Properties prop = DailyStatementProp.byDateRange(input, userInfo, tglCetak, startDate, endDate, acc);
			String strProductName = productName;
			if( strProductName.length() == 0 ){
				AccountList acc = accountListService.getAccountListByAccountNumber(accountNumber);
				if( acc!=null && acc.getProduct() != null )
					strProductName = acc.getProduct().getProductName();
				else
					strProductName = "N/A";
			}
				
			java.util.Properties prop = DailyStatementProp.getMutasiHistoryProperties(accountNumber, input.getCurrency(), userInfo, startDate, endDate, strProductName, input.getSearchKeyWord(), model.getProviderUrl());

			String elWorkspace = model.getWorkspace();
			String reportName = elWorkspace + "/DailyStatementMutasi.rml";
			String contentType = "application/pdf";
			
			OutputStream fos;
			String tempFilename = "";
			if( oneStep == 0 ){
				String tempDir = CommonUtils.getTempDir(confProperties);
				tempFilename = UUID.randomUUID().toString();
				fos = new FileOutputStream(tempDir+tempFilename);
			}else{
				fos = servletResponse.getOutputStream();
			}
			
			if (isPrint == 1) {
				//print				
				ersClient.renderReport(reportName, contentType, fos, prop);
			} else {
				//export
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ersClient.renderReport(reportName, contentType, baos, prop);
				
				ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
				PDDocument doc = PDDocument.load(bais);
				int keyLength = 128;
				
				AccessPermission permission = new AccessPermission();
				permission.setCanPrint(true);
				
				String encryptedPass = accountListService.getStatementPasswordByCifKey(input.getCifKey());
				if( encryptedPass != null ){
					String password = rsaUtil.rsaDecrypt(encryptedPass);
					
					StandardProtectionPolicy policy = new StandardProtectionPolicy(password, password, permission);
					policy.setEncryptionKeyLength(keyLength);
					policy.setPermissions(permission);
					doc.protect(policy);
				}
				doc.save(fos);
				doc.close();				
			}
			fos.close();
			response.setResultCode(ResponseErrorCode.SUCCESS);
			response.setMessage(ResponseErrorCode.MSG_SUCCESS);
			response.setData(tempFilename);
			//delete
			service.deleteTemp(accountNumber);
			if( oneStep == 0){
				PrintWriter out = servletResponse.getWriter();				
				out.print(mapper.writeValueAsString(response));
			}
		} catch (Exception e) {
			logger.error("Error", e);
			try{
				PrintWriter out = servletResponse.getWriter();				
				out.print(mapper.writeValueAsString(CommonUtils.responseException(e)));
			}catch( Exception e1){
				logger.error("Error", e1);
			}
			
		}
		
	}
	
	@RequestMapping(value="/print-recent", method=RequestMethod.POST)
	public void printTransactionHistoryStatement(@Valid @ModelAttribute ByRecentTransactionsInput input, 
			@RequestParam String filename, @RequestParam int isPrint, @RequestParam(defaultValue="0") int oneStep,
			@RequestParam(defaultValue="") String productName,
			HttpServletResponse servletResponse, BindingResult bindingResult){
		ObjectMapper mapper = new ObjectMapper();
		try {			
			Response response = new Response();
			
			List<String> errMsg = new ArrayList<String>();
			if( !CommonUtils.validateInput(bindingResult, errMsg) ){
				response.setResultCode(ResponseErrorCode.INVALID_REQUEST_INPUT);
				response.setMessage(ResponseErrorCode.MSG_INVALID_REQUEST_INPUT);
				response.setErrMsg(errMsg);
				PrintWriter out = servletResponse.getWriter();				
				out.print(mapper.writeValueAsString(response));
				return ;
			}
			
			ElixirModel model = CommonUtils.getElixirModel(confProperties);
			
			String strCount = input.getCountLastTransactions();
			CommonUtils.checkPositiveInteger("countLastTransactions", strCount, errMsg);
			
			String strPage = input.getPage();
			if( strPage != null )
				CommonUtils.checkPositiveInteger("page", strPage, errMsg);
			
			String strCountPerPage = input.getCountPerPage();
			if( strCountPerPage != null )
				CommonUtils.checkPositiveInteger("countPerPage", strCountPerPage, errMsg);
			
			String sortBy = input.getSortBy();
			if( sortBy != null ){
				sortBy = sortBy.toLowerCase();
				if( !sortBy.equals("transaction date") && !sortBy.equals("value date") && !sortBy.equals("description") 
						&& !sortBy.equals("debet") && !sortBy.equals("credit") && !sortBy.equals("balance") ){
					errMsg.add("sortBy: invalid value, valid value are \"transaction date\", \"value date\", \"description\", "
							+ "\"debet\", \"credit\", or \"balance\"");
				}
			}else
				sortBy = "default";
			
			String sortType = input.getSortType();
			if( sortType != null ){
				sortType = sortType.toUpperCase();
				if( !sortType.equals("ASC") && !sortType.equals("DESC") ){
					errMsg.add("sortType: invalid value, valid value are \"ASC\" or \"DESC\"");
				}
			}else
				sortType = "DESC";
			
			if( errMsg.size() > 0 ){
				response.setResultCode(ResponseErrorCode.INVALID_REQUEST_INPUT);
				response.setMessage(ResponseErrorCode.MSG_INVALID_REQUEST_INPUT);
				response.setErrMsg(errMsg);
				PrintWriter out = servletResponse.getWriter();				
				out.print(mapper.writeValueAsString(response));
				return ;
			}
			
			String accountNumber = input.getAccountNumber();
			String currency = input.getCurrency();
			int count = Integer.parseInt(strCount);
			AccountList account = accountListService.getAccountListByAccountNumber(accountNumber, currency);
			String accountType = input.getAccountType();
			accountType = accountType==null?"":accountType;
			String branchCode = input.getBranchCode();
			branchCode = branchCode==null?"":branchCode;
			if( account != null ){
				if( accountType.length() == 0 )
					if( account.getAccountType() != null )
						accountType = account.getAccountType().toString();					
				if( branchCode.length() == 0 )
					branchCode = account.getBranchCode();
				else
					branchCode = "";
			}
			
			String searchKeyWord = input.getSearchKeyWord();
			String operationCode;
			if( currency.equalsIgnoreCase("POINT") ){
				operationCode="100";
			}else{
				operationCode="000";
			}
			response = getRecentAccountActivity(operationCode, input.getUserId(), input.getCifKey(), accountType,
					branchCode, accountNumber, currency, 1, count, sortType, searchKeyWord);
			if( response.getResultCode() == ResponseErrorCode.SUCCESS ){
				List<DailyStatementResponse> result = new ArrayList<DailyStatementResponse>();
				
				@SuppressWarnings("unchecked")
				List<DailyStatementResponse> recentData = (List<DailyStatementResponse>)response.getData();
				for (DailyStatementResponse dst : recentData) {
					String[] desc = dst.getTransDescription().split("(?i)\\<br\\>");
					dst.setTransDescription(desc[0]);
					
					for (int i = 0; i < desc.length; i++) {
						if (i == 1) {
							dst.setRemark1(desc[i].trim());
						} else if (i == 2) {
							dst.setRemark2(desc[i].trim());
						} else if (i == 3) {
							dst.setRemark3(desc[i].trim());
						} else if (i == 4) {
							dst.setRemark4(desc[i].trim());
						} 
					}
				}
				
				List<DailyStatementResponse> historyData = service.getByRecentTransactionsStatement(accountNumber, input.getCurrency(), count, null, null, sortBy, sortType, searchKeyWord );
				if( sortType.equalsIgnoreCase("DESC") ){
					result.addAll(recentData);
					result.addAll(historyData);
				}else{
					result.addAll(historyData);
					result.addAll(recentData);
				}
				
				List<DailyStatementTemp> listTemp = new ArrayList<DailyStatementTemp>();
				DailyStatementTemp temp = null;
				for (DailyStatementResponse item : result) {
					temp = new DailyStatementTemp();
					
					temp.setAccountNumber(accountNumber);
					temp.setTransactionDate(item.getTransactionDate());
					temp.setValueDate(item.getValueDate());
					temp.setTransCode(item.getTranCode());
					String desc = item.getTransDescription();
					desc = desc.replaceAll("<br />", "\n").replaceAll("<b>", "").replaceAll("</b>", "");						
					temp.setTransDescription(desc);
					temp.setTrxDescription(item.getTrxDescription());
					temp.setSign(item.getSign().toString());
					temp.setAmount(item.getAmount());
					temp.setBalance(item.getBalance());
					temp.setRemark1(item.getRemark1());
					temp.setRemark2(item.getRemark2());
					temp.setRemark3(item.getRemark3());
					temp.setRemark4(item.getRemark4());
					
					listTemp.add(temp);
				}
				if( oneStep == 1 && listTemp.size() == 0 ){
					servletResponse.getOutputStream().write(new byte[0]);
					return;
				}
				service.insertTemporary(listTemp);
				
				//print
				String elHostname = model.getHostname();
				int elPort = model.getPort();
				String elUsername = model.getUsername();
				String elPassword = model.getPassword();
				ERSClient ersClient = new ERSClient(elHostname, elPort, elUsername, elPassword);
				
				Response custRes = customerInfo.getUserInfo(input.getCifKey());
				UserInfo userInfo = null;
				if (custRes.getResultCode() == ResponseErrorCode.SUCCESS) {
					userInfo = (UserInfo) custRes.getData();
				}
				
				String strProductName = productName;
				if( strProductName.length() == 0 ){
					AccountList acc = accountListService.getAccountListByAccountNumber(accountNumber);
					if( acc != null && acc.getProduct() != null )
						strProductName = acc.getProduct().getProductName();
					else
						strProductName = "N/A";
				}
				
//				java.util.Properties prop = DailyStatementProp.byLastTrans(input, userInfo, tglCetak, acc);
				java.util.Properties prop = DailyStatementProp.getMutasiRecentProperties(accountNumber, currency, count, userInfo, strProductName, input.getSearchKeyWord(), model.getProviderUrl());
				
				String elWorkspace = model.getWorkspace();
				String reportName = elWorkspace + "/DailyStatementMutasi.rml";
				String contentType = "application/pdf";
				
				String tempDir = CommonUtils.getTempDir(confProperties);
				String tempFilename = UUID.randomUUID().toString();
				OutputStream fos;
				if( oneStep == 0 )
					fos = new FileOutputStream(tempDir+tempFilename);
				else
					fos = servletResponse.getOutputStream();
				if (isPrint == 1) {
					//print
					ersClient.renderReport(reportName, contentType, fos, prop);
				} else {
					//export
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ersClient.renderReport(reportName, contentType, baos, prop);
					
					ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
					PDDocument doc = PDDocument.load(bais);
					int keyLength = 128;
					
					AccessPermission permission = new AccessPermission();
					permission.setCanPrint(true);
					
					String encryptedPass = accountListService.getStatementPasswordByCifKey(input.getCifKey());
					if( encryptedPass != null ){
						String password = rsaUtil.rsaDecrypt(encryptedPass);
						
						StandardProtectionPolicy policy = new StandardProtectionPolicy(password, password, permission);
						policy.setEncryptionKeyLength(keyLength);
						policy.setPermissions(permission);
						
						doc.protect(policy);
					}
					doc.save(fos);
					doc.close();					
				}
				response.setResultCode(ResponseErrorCode.SUCCESS);
				response.setMessage(ResponseErrorCode.MSG_SUCCESS);
				response.setData(tempFilename);
				fos.close();
				//delete
				service.deleteTemp(accountNumber);
			}
			if( oneStep == 0 ){
				PrintWriter out = servletResponse.getWriter();				
				out.print(mapper.writeValueAsString(response));
				return ;
			}
		} catch (Exception e) {
			logger.error("Error", e);
			try{
				PrintWriter out = servletResponse.getWriter();				
				out.print(mapper.writeValueAsString(CommonUtils.responseException(e)));
			}catch( Exception e1){
				logger.error("Error", e1);
			}
		}			
	}
	
	@RequestMapping(value = "getaccsummary", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getAccSummary(@RequestParam String cifKey, @RequestParam String userId
			, @RequestParam String accountNumber, @RequestParam String accountType
			, @RequestParam(defaultValue="") String currency){
		Response res = new Response();
		
		try {
			//stub
			String strContextPath = CommonUtils.getContextPath(confProperties);			
			WBST_Inquiry_AccountBalanceStub stub = new WBST_Inquiry_AccountBalanceStub(strContextPath+"Services/WBST_Inquiry_AccountBalance/Processes/MainRequestResponse_HTTP");
//			WBST_Inquiry_AccountBalanceStub stub = new WBST_Inquiry_AccountBalanceStub(strContextPath+"services/AccountBalance_MainRequestResponse__HTTP");
			ServiceEnvelopeE serviceEnvelopeE = AccountBalanceServiceEnvelopeE.instance();
			AccountBalanceServiceEnvelopeE.setUserId(serviceEnvelopeE, userId);
			AccountBalanceServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
			AccountBalanceServiceEnvelopeE.setSetOperationCode(serviceEnvelopeE, "000");
//			AccountBalanceServiceEnvelopeE.setAccountNo1(serviceEnvelopeE, accountNumber);
//			AccountBalanceServiceEnvelopeE.setAccountType(serviceEnvelopeE, accountType);
			
			List<AccountList> accList;
			if( currency.length() == 0 )
				accList = accountListService.getAllAccountListByAccountNumber(accountNumber);
			else{
				accList = new ArrayList<AccountList>();
				AccountList acc = accountListService.getAccountListByAccountNumber(accountNumber, currency);
				accList.add(acc);
			}
			
//			AccountBalanceServiceEnvelopeE.setMcBit(serviceEnvelopeE, accList.get(0).getMcBit()==1);
			ArrayList<DailyStatementSummaryResponse> allAcc = new ArrayList<DailyStatementSummaryResponse>();
			for (AccountList acc : accList) {
//				AccountBalanceServiceEnvelopeE.setCurrencyType(serviceEnvelopeE, acc.getCurrency());
				AccountBalanceServiceEnvelopeE.addInquiryAccount(serviceEnvelopeE, accountNumber, accountType, acc.getCurrency(), "01010", acc.getMcBit()==1);
				
			}
			ServiceEnvelopeE response = (ServiceEnvelopeE)stubUtil.request(stub, serviceEnvelopeE);
			if( response == null ){
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				List<String> errMsg = new ArrayList<String>();
				errMsg.add(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				res.setErrMsg(errMsg);
				return res;
			}
			
			//response
			ServiceEnvelope serviceEnvelope = response.getServiceEnvelope();
			if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
				res.setResultCode(ResponseErrorCode.SUCCESS);
				res.setMessage(ResponseErrorCode.MSG_SUCCESS);
				List<DailyStatementSummaryResponse> data = service.getDailyStatementSummary(serviceEnvelope.getServiceBody().getRsDetail()
						.getRESConsumer().getRESConsumerSequence_type0());
				
				allAcc.add(data.get(0));
				
			}else{
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				ErrorDetail_type0 errorDetail_type0[] = serviceEnvelope.getServiceBody().getError().getErrorDetail();
				ArrayList<String> errorCodes = new ArrayList<String>();
				for (ErrorDetail_type0 err : errorDetail_type0) {
					errorCodes.add(err.getErrorCode()+" : "+err.getErrorDescription());
				}
				res.setErrMsg(errorCodes);
			}
			res.setData(allAcc);
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);			
		}
		
		return res;
	}
	
	@RequestMapping(value = "getsummary", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getSummary(@RequestParam String cifKey, @RequestParam String userId){
		try {
			Response res = new Response();
			//stub
			String strContextPath = CommonUtils.getContextPath(confProperties);			
			WBST_Inquiry_AccountBalanceStub stub = new WBST_Inquiry_AccountBalanceStub(strContextPath+"Services/WBST_Inquiry_AccountBalance/Processes/MainRequestResponse_HTTP");
//			WBST_Inquiry_AccountBalanceStub stub = new WBST_Inquiry_AccountBalanceStub(strContextPath+"services/AccountBalance_MainRequestResponse__HTTP");
			ServiceEnvelopeE serviceEnvelopeE = AccountBalanceServiceEnvelopeE.instance();
			AccountBalanceServiceEnvelopeE.setUserId(serviceEnvelopeE, userId);
			AccountBalanceServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
			AccountBalanceServiceEnvelopeE.setSetOperationCode(serviceEnvelopeE, "100");
			
			ServiceEnvelopeE response = (ServiceEnvelopeE)stubUtil.request(stub, serviceEnvelopeE);
			if( response == null ){
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				List<String> errMsg = new ArrayList<String>();
				errMsg.add(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				res.setErrMsg(errMsg);
				return res;
			}
			
			//response
			ServiceEnvelope serviceEnvelope = response.getServiceEnvelope();
			if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
				res.setResultCode(ResponseErrorCode.SUCCESS);
				res.setMessage(ResponseErrorCode.MSG_SUCCESS);
				List<DailyStatementSummaryResponse> data = service.getDailyStatementSummary(serviceEnvelope.getServiceBody().getRsDetail()
						.getRESConsumer().getRESConsumerSequence_type0());
				
				res.setData(data);
				
			}else{
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				ErrorDetail_type0 errorDetail_type0[] = serviceEnvelope.getServiceBody().getError().getErrorDetail();
				ArrayList<String> errorCodes = new ArrayList<String>();
				for (ErrorDetail_type0 err : errorDetail_type0) {
					if( err.getProviderError() != null )
						for (ProviderError_type0 pErr : err.getProviderError()) {
							errorCodes.add(pErr.getProviderErrorCode()+" : "+pErr.getProviderErrorDetail());
						}
					errorCodes.add(err.getErrorCode()+" : "+err.getErrorDescription());
				}
				res.setErrMsg(errorCodes);
			}
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);			
		}		
	}
	
	public Response getRecentAccountActivity(String operationCode, String userId, String cifKey, String accountType, String branchCode, 
			String accountNumber, String currency, int start, int count, String sortType, String searchKeyWord) throws AxisFault, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		Response res = new Response();
		String strContextPath = CommonUtils.getContextPath(confProperties);
		
		WBST_Inquiry_RecentAccountActivityStub stub = new WBST_Inquiry_RecentAccountActivityStub(strContextPath+"Services/WBST_Inquiry_RecentAccountActivity/Processes/MainRequestResponse_HTTP");
//		WBST_Inquiry_RecentAccountActivityStub stub = new WBST_Inquiry_RecentAccountActivityStub(strContextPath+"services/Recent_MainRequestResponse__HTTP");
		com.ocbc.schemas.soa.emf.service.wbst_inquiry_recentaccountactivity.ServiceEnvelopeE serviceEnvelopeE = RecentAccountActivityServiceEnvelopeE.instance();
		RecentAccountActivityServiceEnvelopeE.setUserId(serviceEnvelopeE, userId);
		RecentAccountActivityServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
		RecentAccountActivityServiceEnvelopeE.setAccountNo1(serviceEnvelopeE, accountNumber);
		RecentAccountActivityServiceEnvelopeE.setCurrencyType(serviceEnvelopeE, currency);
		RecentAccountActivityServiceEnvelopeE.setBranchCode(serviceEnvelopeE, branchCode);
		RecentAccountActivityServiceEnvelopeE.setAccountType(serviceEnvelopeE, accountType);
		RecentAccountActivityServiceEnvelopeE.setSetOperationCode(serviceEnvelopeE, operationCode);
		
		com.ocbc.schemas.soa.emf.service.wbst_inquiry_recentaccountactivity.ServiceEnvelopeE response = (com.ocbc.schemas.soa.emf.service.wbst_inquiry_recentaccountactivity.ServiceEnvelopeE)stubUtil.request(stub, serviceEnvelopeE);
		if( response == null ){
			res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
			res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
			List<String> errMsg = new ArrayList<String>();
			errMsg.add(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
			res.setErrMsg(errMsg);
			return res;
		}
		com.ocbc.schemas.soa.emf.service.wbst_inquiry_recentaccountactivity.ServiceEnvelope serviceEnvelope = response.getServiceEnvelope();

		
		if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			
			Double currentBalance = 0d;
			if( operationCode.equals("000") )
				currentBalance = service.getLastBalance(accountNumber, currency);
			else{
				Response pointResponse = getPointSummary(userId, cifKey);
				if( pointResponse.getResultCode() == ResponseErrorCode.SUCCESS ){
					@SuppressWarnings("unchecked")
					List<PointResponse> listPoint = (List<PointResponse>) pointResponse.getData();
					for (PointResponse point : listPoint) {
						if( point.getAccountNo().trim().contains(accountNumber.trim()) ){
							currentBalance = point.getBalance().doubleValue();
							break;
						}
					}
				}
			}
			TransactionStatementDetails_type0[] transactionStatementDetails = serviceEnvelope.getServiceBody().getRsDetail()
					.getRESConsumer().getTransactionStatementDetails();
			List<DailyStatementResponse> recentData = new ArrayList<DailyStatementResponse>();
			List<DailyStatementResponse> buffer = service.getRecentAccountActivity(transactionStatementDetails, currentBalance, sortType, searchKeyWord, !operationCode.equals("000"), accountNumber, currency);
			for(int i=0;i<buffer.size();i++){
				if( (i+1) >= start && ((i+1) < start+count || count == Integer.MAX_VALUE) ){
					recentData.add(buffer.get(i));
				}
			}
			 
			res.setData(recentData);
			if( transactionStatementDetails == null )
				res.setTotalAllData(0);
			else
				res.setTotalAllData(buffer.size());
		}else{
			res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
			res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
			ErrorDetail_type0 errorDetail_type0[] = serviceEnvelope.getServiceBody().getError().getErrorDetail();
			ArrayList<String> errorCodes = new ArrayList<String>();
			for (ErrorDetail_type0 err : errorDetail_type0) {
				errorCodes.add(err.getErrorCode()+" : "+err.getErrorDescription());
			}
			res.setErrMsg(errorCodes);
		}
		
		return res;	
	}
	
	@RequestMapping(value = "pointministatement", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response getPointMiniStatement(@RequestParam String userId, @RequestParam String cifKey, @RequestParam String accountNumber
			, @RequestParam(defaultValue="") String accountType, @RequestParam(defaultValue="") String branchCode){
		
		try{
			AccountList account = accountListService.getAccountListByAccountNumber(accountNumber);
			if( account != null ){
				if( accountType.length() == 0 )
					if( account.getAccountType() != null )
						accountType = account.getAccountType().toString();					
				if( branchCode.length() == 0 )
					branchCode = account.getBranchCode();
				else
					branchCode = "";
			}
			Response res = getRecentAccountActivity("100", userId, cifKey, accountType,
					branchCode, accountNumber, account.getCurrency(), 1, 10, "DESC", null);
			if( res.getResultCode() == ResponseErrorCode.SUCCESS ){
				@SuppressWarnings("unchecked")
				List<DailyStatementResponse> recentData = (List<DailyStatementResponse>)res.getData();			
				res.setData(recentData);
			}
			return res;
		}catch(Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
	}
	
	@RequestMapping(value="/print-csv-history", method=RequestMethod.POST)
	public @ResponseBody Response printCsvTransactionHistoryStatement(@Valid @ModelAttribute ByDateHistoryInput input, 
			@RequestParam String filename, BindingResult bindingResult){
		
		try{
			Response response = new Response();
			List<String> errMsg = new ArrayList<String>();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date startDate = null;
			Date endDate = null;
			try{
				startDate = sdf.parse(input.getStart());
			}catch (ParseException e) {
				errMsg.add("start: invalid date format, should be in dd/MM/yyyy format");
			}
			
			try{
				endDate = sdf.parse(input.getEnd());
			}catch (ParseException e) {
				errMsg.add("end: invalid date format, should be in dd/MM/yyyy format");
			}
			
			String strPage = input.getPage();
			if( strPage != null )
				CommonUtils.checkPositiveInteger("page", strPage, errMsg);
			
			String strCountPerPage = input.getCountPerPage();
			if( strCountPerPage != null )
				CommonUtils.checkPositiveInteger("countPerPage", strCountPerPage, errMsg);
			
			String sortBy = input.getSortBy();
			if( sortBy != null ){
				sortBy = sortBy.toLowerCase();
				if( !sortBy.equals("transaction date") && !sortBy.equals("value date") && !sortBy.equals("description") 
						&& !sortBy.equals("debet") && !sortBy.equals("kredit") && !sortBy.equals("balance") ){
					errMsg.add("sortBy: invalid value, valid value are \"transaction date\", \"value date\", \"description\", "
							+ "\"debet\", \"kredit\", or \"balance\"");
				}
			}else
				sortBy = "default";
			
			String sortType = input.getSortType();
			if( sortType != null ){
				sortType = sortType.toUpperCase();
				if( !sortType.equals("ASC") && !sortType.equals("DESC") ){
					errMsg.add("sortType: invalid value, valid value are \"ASC\" or \"DESC\"");
				}
			}else
				sortType = "DESC";
			
			String accountNumber = input.getAccountNumber();
			Calendar now = Calendar.getInstance();
			String searchKeyWord = input.getSearchKeyWord();
			Calendar calEnd = Calendar.getInstance();
			calEnd.setTime(endDate);
			
			List<DailyStatementTemp> listTemp = new ArrayList<DailyStatementTemp>();
			
			if( now.get(Calendar.YEAR) == calEnd.get(Calendar.YEAR)
					&& now.get(Calendar.MONTH) == calEnd.get(Calendar.MONTH)
					&& now.get(Calendar.DATE) == calEnd.get(Calendar.DATE)){
				
				if( sortType.equalsIgnoreCase("ASC") ){
					//int start = (page-1)*countPerPage+1;
					//int end = page*countPerPage;
					List<DailyStatementResponse> historyData = service.getByDateStatement(accountNumber, input.getCurrency(), startDate, endDate, null, null, sortBy, "ASC", searchKeyWord );
					DailyStatementTemp temp = null;
					for (DailyStatementResponse item : historyData) {
						temp = new DailyStatementTemp();
						
						temp.setAccountNumber(accountNumber);
						temp.setTransactionDate(item.getTransactionDate());
						temp.setValueDate(item.getValueDate());
						temp.setTransCode(item.getTranCode());
						temp.setTransDescription(item.getTransDescription());
						temp.setTrxDescription(item.getTrxDescription());
						temp.setSign(item.getSign().toString());
						temp.setAmount(item.getAmount());
						temp.setBalance(item.getBalance());
						temp.setRemark1(item.getRemark1());
						temp.setRemark2(item.getRemark2());
						temp.setRemark3(item.getRemark3());
						temp.setRemark4(item.getRemark4());
						
						listTemp.add(temp);
					}
					
//					service.insertTemporary(listTemp);
				} else {
					List<DailyStatementResponse> historyData = service.getByDateStatement(accountNumber, input.getCurrency(), startDate, endDate, null, null, sortBy, "DESC", searchKeyWord );
					DailyStatementTemp temp = null;
					for (DailyStatementResponse item : historyData) {
						temp = new DailyStatementTemp();
						
						temp.setAccountNumber(accountNumber);
						temp.setTransactionDate(item.getTransactionDate());
						temp.setValueDate(item.getValueDate());
						temp.setTransCode(item.getTranCode());
						temp.setTransDescription(item.getTransDescription());
						temp.setTrxDescription(item.getTrxDescription());
						temp.setSign(item.getSign().toString());
						temp.setAmount(item.getAmount());
						temp.setBalance(item.getBalance());
						temp.setRemark1(item.getRemark1());
						temp.setRemark2(item.getRemark2());
						temp.setRemark3(item.getRemark3());
						temp.setRemark4(item.getRemark4());
						
						listTemp.add(temp);
					}
					
//					service.insertTemporary(listTemp);
				}
			}else{
				//int start = (page-1)*countPerPage+1;
				//int end = page*countPerPage;
				List<DailyStatementResponse> historyData = service.getByDateStatement(accountNumber, input.getCurrency(), startDate, endDate, null, null, sortBy, sortType, searchKeyWord);
				DailyStatementTemp temp = null;
				for (DailyStatementResponse item : historyData) {
					temp = new DailyStatementTemp();
					
					temp.setAccountNumber(accountNumber);
					temp.setTransactionDate(item.getTransactionDate());
					temp.setValueDate(item.getValueDate());
					temp.setTransCode(item.getTranCode());
					temp.setTransDescription(item.getTransDescription());
					temp.setTrxDescription(item.getTrxDescription());
					temp.setSign(item.getSign().toString());
					temp.setAmount(item.getAmount());
					temp.setBalance(item.getBalance());
					temp.setRemark1(item.getRemark1());
					temp.setRemark2(item.getRemark2());
					temp.setRemark3(item.getRemark3());
					temp.setRemark4(item.getRemark4());
					
					listTemp.add(temp);
				}
				
//				service.insertTemporary(listTemp);
			}
			
			//print-csv
			String tempDir = CommonUtils.getTempDir(confProperties);
			String tempFilename = UUID.randomUUID().toString();
			FileOutputStream fos = new FileOutputStream(tempDir+tempFilename);
			String separator = CommonUtils.getCsvDelimiter(sysParamDao.getParameterValues());
			char customQuote = '"';
			
			StringBuilder sb = new StringBuilder();
			
			String searchBy = "";
			if (input.getSearchKeyWord() != null || !input.getSearchKeyWord().equals("")) {
				searchBy = "Search By: " + input.getSearchKeyWord();
			}
			
			sb.append(CSVUtils.writeLineOut(Arrays.asList("Mutasi Rekening"), separator, customQuote));
			sb.append(CSVUtils.writeLineOut(Arrays.asList(DateUtility.formatDatePeriode(startDate, endDate)), separator, customQuote));
			sb.append(CSVUtils.writeLineOut(Arrays.asList(searchBy), separator, customQuote));
			sb.append(CSVUtils.writeLineOut(Arrays.asList(""), separator, customQuote));
			sb.append(CSVUtils.writeLineOut(Arrays.asList("ACCOUNT NUMBER", "TGL TRANS", "TGL VALUTA", "URAIAN", "DEBET", "KREDIT", "BALANCE"), separator, customQuote));
			String desc = "";
			for (DailyStatementTemp item : listTemp) {
				desc = item.getTransDescription();
				desc = desc.replaceAll("<br />", " - ").replaceAll("<b>", "").replaceAll("</b>", "");						
//				desc = StringUtility.checkTransCode(item.getTransCode()) + desc 
//						+ StringUtility.checkRemark(item.getRemark1()) + StringUtility.checkRemark(item.getRemark2()) 
//						+ StringUtility.checkRemark(item.getRemark3()) + StringUtility.checkRemark(item.getRemark4());
				
				List<String> datas = new ArrayList<String>();
				
				datas.add(item.getAccountNumber());
				datas.add(DateUtility.formatDate(item.getTransactionDate()));
				datas.add(DateUtility.formatDate(item.getValueDate()));
				datas.add(desc);
				if (item.getSign().equalsIgnoreCase("D")) {
					datas.add(StringUtility.formatNumber(item.getAmount())); //debet
					datas.add("0");
				} else if (item.getSign().equalsIgnoreCase("C")) {
					datas.add("0");
					datas.add(StringUtility.formatNumber(item.getAmount())); //kredit
				}
				datas.add(StringUtility.formatNumber(item.getBalance()));
				
				sb.append(CSVUtils.writeLineOut(datas, separator, customQuote));
			}
			
			//zip
			ZipOutputStream zs = new ZipOutputStream(fos);
			ZipParameters zipParam = new ZipParameters();
			
			zipParam.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			zipParam.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			
			String encryptedPass = accountListService.getStatementPasswordByCifKey(input.getCifKey());
			String password = "";
			if (encryptedPass != null) {
				password = rsaUtil.rsaDecrypt(encryptedPass);
			} else {
				password = CommonCons.DEFAULT_STATEMENT_PASSWORD;
			}
			
			zipParam.setEncryptFiles(true);
			zipParam.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);			
			zipParam.setPassword(password);
			filename = filename.substring(0, filename.lastIndexOf('.'))+".csv";
			zipParam.setFileNameInZip(filename);
			zipParam.setSourceExternalStream(true);
			zs.putNextEntry(null, zipParam);
			zs.write(sb.toString().getBytes());
			zs.closeEntry();
			zs.finish();
			zs.close();
			fos.close();
			
			response.setResultCode(ResponseErrorCode.SUCCESS);
			response.setMessage(ResponseErrorCode.MSG_SUCCESS);
			response.setData(tempFilename);
			
			return response;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
	}
	
	@RequestMapping(value="/print-csv-recent", method=RequestMethod.POST)
	public @ResponseBody Response printCsvTransactionHistoryStatement(@Valid @ModelAttribute ByRecentTransactionsInput input, 
			@RequestParam String filename, BindingResult bindingResult){
		try{
			Response response = new Response();
			
			List<String> errMsg = new ArrayList<String>();
			
			String strCount = input.getCountLastTransactions();
			CommonUtils.checkPositiveInteger("countLastTransactions", strCount, errMsg);
			
			String strPage = input.getPage();
			if( strPage != null )
				CommonUtils.checkPositiveInteger("page", strPage, errMsg);
			
			String strCountPerPage = input.getCountPerPage();
			if( strCountPerPage != null )
				CommonUtils.checkPositiveInteger("countPerPage", strCountPerPage, errMsg);
			
			String sortBy = input.getSortBy();
			if( sortBy != null ){
				sortBy = sortBy.toLowerCase();
				if( !sortBy.equals("transaction date") && !sortBy.equals("value date") && !sortBy.equals("description") 
						&& !sortBy.equals("debet") && !sortBy.equals("credit") && !sortBy.equals("balance") ){
					errMsg.add("sortBy: invalid value, valid value are \"transaction date\", \"value date\", \"description\", "
							+ "\"debet\", \"credit\", or \"balance\"");
				}
			}else
				sortBy = "default";
			
			String sortType = input.getSortType();
			if( sortType != null ){
				sortType = sortType.toUpperCase();
				if( !sortType.equals("ASC") && !sortType.equals("DESC") ){
					errMsg.add("sortType: invalid value, valid value are \"ASC\" or \"DESC\"");
				}
			}else
				sortType = "DESC";
			
			String accountNumber = input.getAccountNumber();
			String currency = input.getCurrency();
			int count = Integer.parseInt(strCount);
			AccountList account = accountListService.getAccountListByAccountNumber(accountNumber, currency);
			String accountType = input.getAccountType();
			accountType = accountType==null?"":accountType;
			String branchCode = input.getBranchCode();
			branchCode = branchCode==null?"":branchCode;
			if( account != null ){
				if( accountType.length() == 0 )
					if( account.getAccountType() != null )
						accountType = account.getAccountType().toString();					
				if( branchCode.length() == 0 )
					branchCode = account.getBranchCode();
				else
					branchCode = "";
			}
			String searchKeyWord = input.getSearchKeyWord();
			response = getRecentAccountActivity("000", input.getUserId(), input.getCifKey(), accountType,
					branchCode, accountNumber, currency, 1, count, sortType, searchKeyWord);
			if( response.getResultCode() == ResponseErrorCode.SUCCESS ){
				List<DailyStatementResponse> result = new ArrayList<DailyStatementResponse>();
				
				@SuppressWarnings("unchecked")
				List<DailyStatementResponse> recentData = (List<DailyStatementResponse>)response.getData();
				for (DailyStatementResponse dst : recentData) {
					String[] desc = dst.getTransDescription().split("(?i)\\<br\\>");
					dst.setTransDescription(desc[0]);
					
					for (int i = 0; i < desc.length; i++) {
						if (i == 1) {
							dst.setRemark1(desc[i].trim());
						} else if (i == 2) {
							dst.setRemark2(desc[i].trim());
						} else if (i == 3) {
							dst.setRemark3(desc[i].trim());
						} else if (i == 4) {
							dst.setRemark4(desc[i].trim());
						} 
					}
				}
				
				List<DailyStatementResponse> historyData = service.getByRecentTransactionsStatement(accountNumber, input.getCurrency(), count, null, null, sortBy, sortType, searchKeyWord );
				if( sortType.equalsIgnoreCase("DESC") ){
					result.addAll(recentData);
					result.addAll(historyData);
				}else{
					result.addAll(historyData);
					result.addAll(recentData);
				}
				
				List<DailyStatementTemp> listTemp = new ArrayList<DailyStatementTemp>();
				DailyStatementTemp temp = null;
				for (DailyStatementResponse item : result) {
					temp = new DailyStatementTemp();
					
					temp.setAccountNumber(accountNumber);
					temp.setTransactionDate(item.getTransactionDate());
					temp.setValueDate(item.getValueDate());
					temp.setTransCode(item.getTranCode());
					temp.setTransDescription(item.getTransDescription());
					temp.setTrxDescription(item.getTrxDescription());
					temp.setSign(item.getSign().toString());
					temp.setAmount(item.getAmount());
					temp.setBalance(item.getBalance());
					temp.setRemark1(item.getRemark1());
					temp.setRemark2(item.getRemark2());
					temp.setRemark3(item.getRemark3());
					temp.setRemark4(item.getRemark4());
					
					listTemp.add(temp);
				}
				
//				service.insertTemporary(listTemp);
				
				try {
					//print-csv
					String tempDir = CommonUtils.getTempDir(confProperties);
					String tempFilename = UUID.randomUUID().toString();
					FileOutputStream fos = new FileOutputStream(tempDir+tempFilename);
					String separator = CommonUtils.getCsvDelimiter(sysParamDao.getParameterValues());
					char customQuote = '"';
					
					StringBuilder sb = new StringBuilder();
					
					String searchBy = "";
					if (input.getSearchKeyWord() != null || !input.getSearchKeyWord().equals("")) {
						searchBy = "Search By: " + input.getSearchKeyWord();
					}
					
					sb.append(CSVUtils.writeLineOut(Arrays.asList("Mutasi Rekening"), separator, customQuote));
					sb.append(CSVUtils.writeLineOut(Arrays.asList("Last " + count + " Transactions"), separator, customQuote));
					sb.append(CSVUtils.writeLineOut(Arrays.asList(searchBy), separator, customQuote));
					sb.append(CSVUtils.writeLineOut(Arrays.asList(""), separator, customQuote));
					sb.append(CSVUtils.writeLineOut(Arrays.asList("ACCOUNT NUMBER", "TGL TRANS", "TGL VALUTA", "URAIAN", "DEBET", "KREDIT", "BALANCE"), separator, customQuote));
					String desc = "";
					for (DailyStatementTemp item : listTemp) {
						desc = item.getTransDescription();
						desc = desc.replaceAll("<br />", " - ").replaceAll("<b>", "").replaceAll("</b>", "");
//						desc = StringUtility.checkTransCode(item.getTransCode()) + item.getTransDescription() 
//								+ StringUtility.checkRemark(item.getRemark1()) + StringUtility.checkRemark(item.getRemark2()) 
//								+ StringUtility.checkRemark(item.getRemark3()) + StringUtility.checkRemark(item.getRemark4());
						
						List<String> datas = new ArrayList<String>();
						
						datas.add(item.getAccountNumber());
						datas.add(DateUtility.formatDate(item.getTransactionDate()));
						datas.add(DateUtility.formatDate(item.getValueDate()));
						datas.add(desc);
						if (item.getSign().equalsIgnoreCase("D")) {
							datas.add(StringUtility.formatNumber(item.getAmount())); //debet
							datas.add("0");
						} else if (item.getSign().equalsIgnoreCase("C")) {
							datas.add("0");
							datas.add(StringUtility.formatNumber(item.getAmount())); //kredit
						}
						datas.add(StringUtility.formatNumber(item.getBalance()));
						
						sb.append(CSVUtils.writeLineOut(datas, separator, customQuote));
					}
					
					//zip
					ZipOutputStream zs = new ZipOutputStream(fos);
					ZipParameters zipParam = new ZipParameters();
					
					zipParam.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
					zipParam.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
					
					String encryptedPass = accountListService.getStatementPasswordByCifKey(input.getCifKey());
					String password = "";
					if (encryptedPass != null) {
						password = rsaUtil.rsaDecrypt(encryptedPass);
					} else {
						password = CommonCons.DEFAULT_STATEMENT_PASSWORD;
					}
					
					zipParam.setEncryptFiles(true);
					zipParam.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);				
					zipParam.setPassword(password);
					filename = filename.substring(0, filename.lastIndexOf('.'))+".csv";
					zipParam.setFileNameInZip(filename);
					zipParam.setSourceExternalStream(true);
					zs.putNextEntry(null, zipParam);
					zs.write(sb.toString().getBytes());
					zs.closeEntry();
					zs.finish();
					zs.close();
					response.setResultCode(ResponseErrorCode.SUCCESS);
					response.setMessage(ResponseErrorCode.MSG_SUCCESS);
					response.setData(tempFilename);
				} catch (IOException e) {
					e.printStackTrace();
					response.setResultCode(ResponseErrorCode.INTERNAL_SERVER_ERROR);
					response.setMessage(ResponseErrorCode.MSG_INTERNAL_SERVER_ERROR);
				} catch (ZipException e) {
					e.printStackTrace();
					response.setResultCode(ResponseErrorCode.INTERNAL_SERVER_ERROR);
					response.setMessage(ResponseErrorCode.MSG_INTERNAL_SERVER_ERROR);
				} 					
			}
			return response;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
	}
}
