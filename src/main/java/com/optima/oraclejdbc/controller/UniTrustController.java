package com.optima.oraclejdbc.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisp.schemas.soa.wsdl.service.wbst_inquiry_recentutaccountactivity.operationimpl.services.wbst__inquiry__recentutaccountactivity.processes.WBST_Inquiry_RecentUTAccountActivityStub;
import com.ocbc.schemas.soa.emf.common.body.ErrorDetail_type0;
import com.ocbc.schemas.soa.emf.investment.aggregates.UTTransactionDetails_type0;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailinvestment.ServiceEnvelope;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailinvestment.ServiceEnvelopeE;
import com.ocbc.schemas.soa.wsdl.service.wbst_inquiry_detailinvestment.operationimpl.services.wbst__inquiry__detailinvestment.processes.WBST_Inquiry_DetailInvestmentStub;
import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.daoimpl.AccountListDao;
import com.optima.oraclejdbc.daoimpl.SystemParameterDao;
import com.optima.oraclejdbc.elixirprop.UnitTrustProp;
import com.optima.oraclejdbc.model.AccountList;
import com.optima.oraclejdbc.model.ElixirModel;
import com.optima.oraclejdbc.model.UnitTrust;
import com.optima.oraclejdbc.model.UnitTrustTemp;
import com.optima.oraclejdbc.model.UserInfo;
import com.optima.oraclejdbc.parser.NISPReverse;
import com.optima.oraclejdbc.requestinput.ByDateHistoryInput;
import com.optima.oraclejdbc.requestinput.ByRecentTransactionsInput;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.response.unitrust.UniTrustTrxHistoryResponse;
import com.optima.oraclejdbc.response.unittrust.UnitTrustResponse;
import com.optima.oraclejdbc.service.AccountListRegisterService;
import com.optima.oraclejdbc.service.UniTrustService;
import com.optima.oraclejdbc.tibco.customerinfo.CustomerInfoRequest;
import com.optima.oraclejdbc.tibco.unittrust.RecentUTActivityServiceEnvelopeE;
import com.optima.oraclejdbc.tibco.unittrust.UnitTrustServiceEnvelopeE;
import com.optima.oraclejdbc.utils.CSVUtils;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.DateUtility;
import com.optima.oraclejdbc.utils.RsaUtil;
import com.optima.oraclejdbc.utils.StringUtility;
import com.optima.oraclejdbc.utils.StubUtil;

@RestController
@RequestMapping(value="/unittrust")
public class UniTrustController {

	private static final Logger logger = Logger.getLogger(UniTrustController.class);
	
	@Autowired
	UniTrustService service;
	
	@Resource(name="confProp")
	private java.util.Properties confProperties;
	
	@Autowired
	StubUtil stubUtil;
	
	@Autowired
	CustomerInfoRequest customerInfo;
	
	@Autowired
	AccountListDao accountListDao;
	
	@Autowired
	NISPReverse<UnitTrust> reverse;
	
	@Autowired
	SystemParameterDao sysParamDao;
	
	@Autowired
	AccountListRegisterService accountListService;
	
	@Autowired
	RsaUtil rsaUtil;
	
	@RequestMapping(value="/ministatement", method=RequestMethod.POST)
	public Serializable getMiniStatement(@RequestParam String userId, @RequestParam String cifKey, 
			@RequestParam String accountNumber, @RequestParam String count, @RequestParam String monthsBefore){
		try{
			int iCount = Integer.parseInt(count);
			int iMonthsBefore = Integer.parseInt(monthsBefore);
			Response res = getRecentActivity(userId, cifKey, accountNumber, 1, iCount, "DESC", null);
			if( res.getResultCode() == ResponseErrorCode.SUCCESS ){			
				@SuppressWarnings("unchecked")
				List<UniTrustTrxHistoryResponse> recentData = (List<UniTrustTrxHistoryResponse>)res.getData();
				if( recentData.size() < iCount ){
					Calendar now = Calendar.getInstance();
					Calendar monthsAgo = Calendar.getInstance();
					monthsAgo.add(Calendar.MONTH, -iMonthsBefore);
					List<UniTrustTrxHistoryResponse> historyData = service.getMiniStatement(accountNumber, monthsAgo.getTime(), now.getTime(), null, iCount-recentData.size());
					recentData.addAll(historyData);
				}
				res.setData(recentData);
			}
			return res;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}
	
	@RequestMapping(value = "getsummary", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getSummary(@RequestParam String userId, @RequestParam String cifKey) {
		try {
			Response res = new Response();
			//stub
			String strContextPath = CommonUtils.getContextPath(confProperties);
			
			WBST_Inquiry_DetailInvestmentStub stub = new WBST_Inquiry_DetailInvestmentStub(strContextPath+"Services/WBST_Inquiry_DetailInvestment/Processes/MainRequestResponse_HTTP");
//			WBST_Inquiry_DetailInvestmentStub stub = new WBST_Inquiry_DetailInvestmentStub(strContextPath+"services/DetailInvestment_MainRequestResponse__HTTP");
			ServiceEnvelopeE serviceEnvelopeE = UnitTrustServiceEnvelopeE.instance();
			UnitTrustServiceEnvelopeE.setUserId(serviceEnvelopeE, userId);
			UnitTrustServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
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
						.getRESConsumer().isInvestmentAccountDetailSpecified()){
					List<UnitTrustResponse> data = service.getSummary(serviceEnvelope.getServiceBody().getRsDetail()
							.getRESConsumer().getInvestmentAccountDetail());
				
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
	
	@RequestMapping(value="/history", method=RequestMethod.POST)
	public Serializable getTransactionHistoryStatement(@Valid @ModelAttribute ByDateHistoryInput input, BindingResult bindingResult){
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
					if( !sortBy.equals("reference number") && !sortBy.equals("transaction date") && !sortBy.equals("effective date") 
							&& !sortBy.equals("transaction amount") && !sortBy.equals("transaction type")
							&& !sortBy.equals("nav") && !sortBy.equals("unit")
							&& !sortBy.equals("status") && !sortBy.equals("transaction source") ){
						errMsg.add("sortBy: invalid value, valid value are \"reference number\", \"transaction date\", \"effective date\", "
								+ "\"transaction amount\", \"transaction type\", \"nav\", \"unit\", \"status\" or \"transaction source\"");
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
				Calendar now = Calendar.getInstance();
				String searchKeyWord = input.getSearchKeyWord();
				int totalHistory = service.getTotalCountByDate(accountNumber, startDate, endDate, searchKeyWord);
				Calendar calEnd = Calendar.getInstance();
				calEnd.setTime(endDate);
				if( now.get(Calendar.YEAR) == calEnd.get(Calendar.YEAR)
						&& now.get(Calendar.MONTH) == calEnd.get(Calendar.MONTH)
						&& now.get(Calendar.DATE) == calEnd.get(Calendar.DATE)){
					
					if( sortType.equalsIgnoreCase("ASC") ){
						int start = (page-1)*countPerPage+1;
						int end = page*countPerPage;
						
						List<UniTrustTrxHistoryResponse> historyData = service.getByDateStatement(accountNumber, startDate, endDate, start, end, sortBy, sortType, searchKeyWord);
						int historySize = historyData.size();
						response = getRecentActivity(input.getUserId(), input.getCifKey(), accountNumber,
								(page-1)*countPerPage+1, countPerPage-historySize, sortType, searchKeyWord);
						if( response.getResultCode() == ResponseErrorCode.SUCCESS ){
							@SuppressWarnings("unchecked")
							List<UniTrustTrxHistoryResponse> recentData = (List<UniTrustTrxHistoryResponse>)response.getData();
							historyData.addAll(recentData);
							response.setData(historyData);
							response.setTotalAllData(totalHistory+response.getTotalAllData());
							response.setResultCode(ResponseErrorCode.SUCCESS);
							response.setMessage(ResponseErrorCode.MSG_SUCCESS);
						}
					}else{
						response = getRecentActivity(input.getUserId(), input.getCifKey(), accountNumber,
								(page-1)*countPerPage+1, countPerPage, sortType, searchKeyWord);
						if( response.getResultCode() == ResponseErrorCode.SUCCESS ){
							@SuppressWarnings("unchecked")
							List<UniTrustTrxHistoryResponse> recentData = (List<UniTrustTrxHistoryResponse>)response.getData();
							if( recentData.size() < countPerPage ){
								int start = 1;
								int end = 1;
								if( recentData.size() == 0 ){
									start = (page-1)*countPerPage-response.getTotalAllData()+1;
									end = page*countPerPage - response.getTotalAllData();
								}else{
									end = countPerPage - recentData.size();
								}
								List<UniTrustTrxHistoryResponse> historyData = service.getByDateStatement(accountNumber, startDate, endDate, start, end, sortBy, sortType, searchKeyWord);
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
					response.setData(service.getByDateStatement(accountNumber, startDate, endDate, start, end, sortBy, sortType, searchKeyWord ));
					response.setTotalAllData(totalHistory);
					response.setResultCode(ResponseErrorCode.SUCCESS);
					response.setMessage(ResponseErrorCode.MSG_SUCCESS);
				}
				return response;
			}
		}catch( Exception e){
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
					if( !sortBy.equals("reference number") && !sortBy.equals("transaction date") && !sortBy.equals("effective date") 
							&& !sortBy.equals("transaction amount") && !sortBy.equals("transaction type")
							&& !sortBy.equals("nav") && !sortBy.equals("unit")
							&& !sortBy.equals("status") && !sortBy.equals("transaction source") ){
						errMsg.add("sortBy: invalid value, valid value are \"reference number\", \"transaction date\", \"effective date\", "
								+ "\"transaction amount\", \"transaction type\", \"nav\", \"unit\", \"status\" or \"transaction source\"");
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
				int count = Integer.parseInt(strCount);
				String searchKeyWord = input.getSearchKeyWord();
				response = getRecentActivity(input.getUserId(), input.getCifKey(), accountNumber,
						1, count, sortType, searchKeyWord);
				if( response.getResultCode() == ResponseErrorCode.SUCCESS ){
					int totalData = 0;
					int recentTotalData = response.getTotalAllData();
					List<UniTrustTrxHistoryResponse> result = new ArrayList<UniTrustTrxHistoryResponse>();
					@SuppressWarnings("unchecked")
					List<UniTrustTrxHistoryResponse> recentData = (List<UniTrustTrxHistoryResponse>)response.getData();
					
					if( recentTotalData < count ){
						int start = 1;
						int end = count - recentTotalData;
						totalData = recentTotalData + service.getTotalCountByRecent(accountNumber, end, searchKeyWord);
						List<UniTrustTrxHistoryResponse> historyData = service.getByRecentTransactionsStatement(accountNumber, end, start, end, sortBy, sortType, searchKeyWord );
						if( sortType.equalsIgnoreCase("DESC") ){
							result.addAll(recentData);
							result.addAll(historyData);
						}else{
							result.addAll(historyData);
							result.addAll(recentData);
						}
					}else{
						result.addAll(recentData);
						totalData = recentData.size();
					}
					
					int start = (page-1)*countPerPage;
					int end = page*countPerPage-1;
					
					List<UniTrustTrxHistoryResponse> resData = new ArrayList<UniTrustTrxHistoryResponse>();
					for(int i=0;i<result.size();i++){
						if( i>=start && i<=end )
							resData.add(result.get(i));
					}
					
					response.setData(resData);
					response.setTotalAllData(totalData);				
				}
				
				return response;
			}
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}
	
	public Response getRecentActivity(String userId, String cifKey, 
			String accountNumber, int start, int count, String sortType, String searchKeyWord) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, AxisFault{
		Response res = new Response();
		String strContextPath = CommonUtils.getContextPath(confProperties);
		
		WBST_Inquiry_RecentUTAccountActivityStub stub = new WBST_Inquiry_RecentUTAccountActivityStub(strContextPath+"Services/WBST_Inquiry_RecentUTAccountActivity/Processes/MainRequestResponse_HTTP");
//		WBST_Inquiry_RecentUTAccountActivityStub stub = new WBST_Inquiry_RecentUTAccountActivityStub(strContextPath+"services/RecentUT_MainRequestResponse__HTTP");
		com.ocbc.schemas.soa.emf.service.wbst_inquiry_recentutaccountactivity.ServiceEnvelopeE serviceEnvelopeE = RecentUTActivityServiceEnvelopeE.instance();
		RecentUTActivityServiceEnvelopeE.setUserId(serviceEnvelopeE, userId);
		RecentUTActivityServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
		RecentUTActivityServiceEnvelopeE.setAccountNo1(serviceEnvelopeE, accountNumber);
		RecentUTActivityServiceEnvelopeE.setAccountType(serviceEnvelopeE, "");
		RecentUTActivityServiceEnvelopeE.setCurrencyType(serviceEnvelopeE, "");
		RecentUTActivityServiceEnvelopeE.setBranchCode(serviceEnvelopeE, "");
		RecentUTActivityServiceEnvelopeE.setLanguageCode(serviceEnvelopeE, "ID");
		
		com.ocbc.schemas.soa.emf.service.wbst_inquiry_recentutaccountactivity.ServiceEnvelopeE response = (com.ocbc.schemas.soa.emf.service.wbst_inquiry_recentutaccountactivity.ServiceEnvelopeE)stubUtil.request(stub, serviceEnvelopeE);
		if( response == null ){
			res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
			res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
			List<String> errMsg = new ArrayList<String>();
			errMsg.add(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
			res.setErrMsg(errMsg);
			return res;
		}
		com.ocbc.schemas.soa.emf.service.wbst_inquiry_recentutaccountactivity.ServiceEnvelope serviceEnvelope = response.getServiceEnvelope();
		
		
		
		if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			
			if( serviceEnvelope.getServiceBody().getRsDetail().isRESConsumerSpecified() ){
				UTTransactionDetails_type0[] utTransactionDetails_type0s = serviceEnvelope.getServiceBody().getRsDetail()
						.getRESConsumer().getUTTransactionDetails();
				List<UniTrustTrxHistoryResponse> recentData = service.getRecentUTAccountActivity(utTransactionDetails_type0s, start, count, sortType, searchKeyWord);
				res.setData(recentData);
				if( utTransactionDetails_type0s == null ){
					res.setTotalAllData(0);
				}else{
					res.setTotalAllData(recentData.size());
				}
			}else{
				res.setData(new ArrayList<UniTrustTrxHistoryResponse>());
				res.setTotalAllData(0);
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
	}
	
	@RequestMapping(value="/print-history", method=RequestMethod.POST)
	public void printTransactionHistoryStatement(@Valid @ModelAttribute ByDateHistoryInput input, 
			@RequestParam String filename, @RequestParam int isPrint, @RequestParam String productName, 
			@RequestParam(defaultValue="0") int oneStep,
			HttpServletResponse servletResponse, 
			BindingResult bindingResult){
		
		try {
			Response response = new Response();
			List<String> errMsg = new ArrayList<String>();
			
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
				if( !sortBy.equals("reference number") && !sortBy.equals("transaction date") && !sortBy.equals("effective date") 
						&& !sortBy.equals("transaction amount") && !sortBy.equals("transaction type")
						&& !sortBy.equals("nav") && !sortBy.equals("unit")
						&& !sortBy.equals("status") && !sortBy.equals("transaction source") ){
					errMsg.add("sortBy: invalid value, valid value are \"reference number\", \"transaction date\", \"effective date\", "
							+ "\"transaction amount\", \"transaction type\", \"nav\", \"unit\", \"status\" or \"transaction source\"");
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
			if( now.get(Calendar.YEAR) == calEnd.get(Calendar.YEAR)
					&& now.get(Calendar.MONTH) == calEnd.get(Calendar.MONTH)
					&& now.get(Calendar.DATE) == calEnd.get(Calendar.DATE)){
				
				if( sortType.equalsIgnoreCase("ASC") ){
//					int start = (page-1)*countPerPage+1;
//					int end = page*countPerPage;
					
					List<UnitTrustTemp> listTemp = new ArrayList<UnitTrustTemp>();
					List<UniTrustTrxHistoryResponse> historyData = service.getByDateStatement(accountNumber, startDate, endDate, null, null, sortBy, sortType, searchKeyWord);
					response = getRecentActivity(input.getUserId(), input.getCifKey(), accountNumber,
							1, Integer.MAX_VALUE, sortType, searchKeyWord);
					if( response.getResultCode() == ResponseErrorCode.SUCCESS ){
						@SuppressWarnings("unchecked")
						List<UniTrustTrxHistoryResponse> recentData = (List<UniTrustTrxHistoryResponse>)response.getData();
						historyData.addAll(recentData);
						UnitTrustTemp temp = null;
						for (UniTrustTrxHistoryResponse item : historyData) {
							temp = new UnitTrustTemp();
							
							temp.setAccountNo(accountNumber);
							temp.setCurrencyCode(item.getCurrencyCode());
							temp.setTransactionDate(item.getTransactionDate());
							temp.setEffectiveDate(item.getEffectiveDate());
							temp.setReferenceNo(item.getReferenceNo());
							temp.setTransactionType(item.getTransactionType().toString());
							temp.setAmount(item.getAmount());
							temp.setNav(item.getNav());
							temp.setFeeAmount(item.getFeeAmount());
							temp.setUnit(item.getUnit());
							temp.setStatus(item.getStatus().toString());
							temp.setTransactionSource(item.getTransactionSource());
							temp.setStatusStr("TBD");
							temp.setPeriode("TBD");
							temp.setTransactionTypeStr("TBD");
							
							listTemp.add(temp);
						}
					}
					
					
					if( oneStep == 1 && listTemp.size() == 0 ){
						servletResponse.getOutputStream().write(new byte[0]);
						return;
					}
					service.insertTemporary(listTemp);
				}else{
					List<UnitTrustTemp> listTemp = new ArrayList<UnitTrustTemp>();
					response = getRecentActivity(input.getUserId(), input.getCifKey(), accountNumber,
							1, Integer.MAX_VALUE, sortType, searchKeyWord);
					
					if( response.getResultCode() == ResponseErrorCode.SUCCESS ){
						@SuppressWarnings("unchecked")
						List<UniTrustTrxHistoryResponse> recentData = (List<UniTrustTrxHistoryResponse>)response.getData();
						List<UniTrustTrxHistoryResponse> historyData = service.getByDateStatement(accountNumber, startDate, endDate, null, null, sortBy, sortType, searchKeyWord);
						recentData.addAll(historyData);
						UnitTrustTemp temp = null;
						for (UniTrustTrxHistoryResponse item : recentData) {
							temp = new UnitTrustTemp();
							
							temp.setAccountNo(accountNumber);
							temp.setCurrencyCode(item.getCurrencyCode());
							temp.setTransactionDate(item.getTransactionDate());
							temp.setEffectiveDate(item.getEffectiveDate());
							temp.setReferenceNo(item.getReferenceNo());
							temp.setTransactionType(item.getTransactionType().toString());
							temp.setAmount(item.getAmount());
							temp.setNav(item.getNav());
							temp.setFeeAmount(item.getFeeAmount());
							temp.setUnit(item.getUnit());
							temp.setStatus(item.getStatus().toString());
							temp.setTransactionSource(item.getTransactionSource());
							temp.setStatusStr("TBD");
							temp.setPeriode("TBD");
							temp.setTransactionTypeStr("TBD");
							
							listTemp.add(temp);
						}
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
				List<UniTrustTrxHistoryResponse> historyData = service.getByDateStatement(accountNumber, startDate, endDate, null, null, sortBy, sortType, searchKeyWord);
				List<UnitTrustTemp> listTemp = new ArrayList<UnitTrustTemp>();
				UnitTrustTemp temp = null;
				for (UniTrustTrxHistoryResponse item : historyData) {
					temp = new UnitTrustTemp();
					
					temp.setAccountNo(accountNumber);
					temp.setCurrencyCode(item.getCurrencyCode());
					temp.setTransactionDate(item.getTransactionDate());
					temp.setEffectiveDate(item.getEffectiveDate());
					temp.setReferenceNo(item.getReferenceNo());
					temp.setTransactionType(item.getTransactionType().toString());
					temp.setAmount(item.getAmount());
					temp.setNav(item.getNav());
					temp.setFeeAmount(item.getFeeAmount());
					temp.setUnit(item.getUnit());
					temp.setStatus(item.getStatus().toString());
					temp.setTransactionSource(item.getTransactionSource());
					temp.setStatusStr("TBD");
					temp.setPeriode("TBD");
					temp.setTransactionTypeStr("TBD");
					
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
			
			AccountList acc = accountListDao.getAccountListByAccountNumber(accountNumber);
			java.util.Properties prop = UnitTrustProp.getUTStatementHistoryProp(input, userInfo, acc, productName, model.getProviderUrl());
			
			String elWorkspace = model.getWorkspace();
			String reportName = elWorkspace + "/UTStatement.rml";
			String contentType = "application/pdf";
			
			OutputStream fos = null;
			String uuid = UUID.randomUUID().toString();
			String tempDir = CommonUtils.getTempDir(confProperties);
			try {
				if( oneStep == 0 )
					fos = new FileOutputStream(tempDir+uuid);
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
				response.setData(uuid);
				service.deleteTemp(accountNumber);
			} finally{
				if( fos != null )
					fos.close();
			}
			
			if( oneStep == 0 ){
				ObjectMapper mapper = new ObjectMapper();
				PrintWriter out = servletResponse.getWriter();
				out.print(mapper.writeValueAsString(response));
			}			
		} catch (Exception e) {
			logger.error("Error", e);
			try{
				ObjectMapper mapper = new ObjectMapper();
				PrintWriter out = servletResponse.getWriter();				
				out.print(mapper.writeValueAsString(CommonUtils.responseException(e)));
			}catch( Exception e1){
				logger.error("Error", e1);
			}
		}
		
	}
	
	@RequestMapping(value="/print-recent", method=RequestMethod.POST)
	public void printTransactionHistoryStatement(@Valid @ModelAttribute ByRecentTransactionsInput input, 
			@RequestParam String filename, @RequestParam int isPrint, @RequestParam String productName,
			@RequestParam(defaultValue="0") int oneStep,
			HttpServletResponse servletResponse, 
			BindingResult bindingResult){
		
		try {
			Response response = new Response();
			
			List<String> errMsg = new ArrayList<String>();
			
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
				if( !sortBy.equals("reference number") && !sortBy.equals("transaction date") && !sortBy.equals("effective date") 
						&& !sortBy.equals("transaction amount") && !sortBy.equals("transaction type")
						&& !sortBy.equals("nav") && !sortBy.equals("unit")
						&& !sortBy.equals("status") && !sortBy.equals("transaction source") ){
					errMsg.add("sortBy: invalid value, valid value are \"reference number\", \"transaction date\", \"effective date\", "
							+ "\"transaction amount\", \"transaction type\", \"nav\", \"unit\", \"status\" or \"transaction source\"");
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
			int count = Integer.parseInt(strCount);
			String searchKeyWord = input.getSearchKeyWord();
			response = getRecentActivity(input.getUserId(), input.getCifKey(), accountNumber,
					1, count, sortType, searchKeyWord);
			if( response.getResultCode() == ResponseErrorCode.SUCCESS ){
				List<UniTrustTrxHistoryResponse> result = new ArrayList<UniTrustTrxHistoryResponse>();
				@SuppressWarnings("unchecked")
				List<UniTrustTrxHistoryResponse> recentData = (List<UniTrustTrxHistoryResponse>)response.getData();
				List<UniTrustTrxHistoryResponse> historyData = service.getByRecentTransactionsStatement(accountNumber, count, null, null, sortBy, sortType, searchKeyWord );
				if( sortType.equalsIgnoreCase("DESC") ){
					result.addAll(recentData);
					result.addAll(historyData);
				}else{
					result.addAll(historyData);
					result.addAll(recentData);
				}
				
				List<UnitTrustTemp> listTemp = new ArrayList<UnitTrustTemp>();
				UnitTrustTemp temp = null;
				for (UniTrustTrxHistoryResponse item : result) {
					temp = new UnitTrustTemp();
					
					temp.setAccountNo(accountNumber);
					temp.setCurrencyCode(item.getCurrencyCode());
					temp.setTransactionDate(item.getTransactionDate());
					temp.setEffectiveDate(item.getEffectiveDate());
					temp.setReferenceNo(item.getReferenceNo());
					temp.setTransactionType(item.getTransactionType().toString());
					temp.setAmount(item.getAmount());
					temp.setNav(item.getNav());
					temp.setFeeAmount(item.getFeeAmount());
					temp.setUnit(item.getUnit());
					temp.setStatus(item.getStatus().toString());
					temp.setTransactionSource(item.getTransactionSource());
					temp.setStatusStr("TBD");
					temp.setPeriode("TBD");
					temp.setTransactionTypeStr("TBD");
					
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
				
				AccountList acc = accountListDao.getAccountListByAccountNumber(accountNumber);
				java.util.Properties prop = UnitTrustProp.getUTStatementRecentProp(input, userInfo, acc, productName, model.getProviderUrl());
				
				String elWorkspace = model.getWorkspace();
				String reportName = elWorkspace + "/UTStatement.rml";
				String contentType = "application/pdf";
				String tempDir = CommonUtils.getTempDir(confProperties);
				String uuid = UUID.randomUUID().toString();
				OutputStream fos = null;
				try {
					if( oneStep == 0 )
						fos = new FileOutputStream(tempDir+uuid);
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
					response.setData(uuid);
					
					//delete
					service.deleteTemp(accountNumber);
				} finally{
					if( fos != null )
						fos.close();
				}
			}
			if( oneStep == 0 ){
				PrintWriter out = servletResponse.getWriter();
				ObjectMapper mapper = new ObjectMapper();
				out.print(mapper.writeValueAsString(response));
			}			
		} catch (Exception e) {
			logger.error("Error", e);
			try{
				ObjectMapper mapper = new ObjectMapper();
				PrintWriter out = servletResponse.getWriter();				
				out.print(mapper.writeValueAsString(CommonUtils.responseException(e)));
			}catch( Exception e1){
				logger.error("Error", e1);
			}
		}
		
	}
	
	@RequestMapping(value = "reverse/{accNo}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response testingReverse(@PathVariable String accNo) {
		
		try {
			Response res = new Response();
			List<UnitTrust> listData = service.getListDataByAccNo(accNo);
			List<String> result = reverse.reverse(listData, "");
			for (String item : result) {
				System.out.println(item);
			}
			
			res.setData("Sukses");
			res.setResultCode(1000);
			return res;
		} catch (Exception e) {
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
				if( !sortBy.equals("reference number") && !sortBy.equals("transaction date") && !sortBy.equals("effective date") 
						&& !sortBy.equals("transaction amount") && !sortBy.equals("transaction type")
						&& !sortBy.equals("nav") && !sortBy.equals("unit")
						&& !sortBy.equals("status") && !sortBy.equals("transaction source") ){
					errMsg.add("sortBy: invalid value, valid value are \"reference number\", \"transaction date\", \"effective date\", "
							+ "\"transaction amount\", \"transaction type\", \"nav\", \"unit\", \"status\" or \"transaction source\"");
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
			
			List<UnitTrustTemp> listTemp = new ArrayList<UnitTrustTemp>();
			
			if( now.get(Calendar.YEAR) == calEnd.get(Calendar.YEAR)
					&& now.get(Calendar.MONTH) == calEnd.get(Calendar.MONTH)
					&& now.get(Calendar.DATE) == calEnd.get(Calendar.DATE)){
				
				if( sortType.equalsIgnoreCase("ASC") ){
//					int start = (page-1)*countPerPage+1;
//					int end = page*countPerPage;
					
					List<UniTrustTrxHistoryResponse> historyData = service.getByDateStatement(accountNumber, startDate, endDate, null, null, sortBy, sortType, searchKeyWord);
					UnitTrustTemp temp = null;
					for (UniTrustTrxHistoryResponse item : historyData) {
						temp = new UnitTrustTemp();
						
						temp.setAccountNo(accountNumber);
						temp.setCurrencyCode(item.getCurrencyCode());
						temp.setTransactionDate(item.getTransactionDate());
						temp.setEffectiveDate(item.getEffectiveDate());
						temp.setReferenceNo(item.getReferenceNo());
						temp.setTransactionType(item.getTransactionType().toString());
						temp.setAmount(item.getAmount());
						temp.setNav(item.getNav());
						temp.setFeeAmount(item.getFeeAmount());
						temp.setUnit(item.getUnit());
						temp.setStatus(item.getStatus().toString());
						temp.setTransactionSource(item.getTransactionSource());
						temp.setStatusStr("TBD");
						temp.setPeriode("TBD");
						temp.setTransactionTypeStr("TBD");
						
						listTemp.add(temp);
					}
					
//					service.insertTemporary(listTemp);
				}else{
					List<UniTrustTrxHistoryResponse> historyData = service.getByDateStatement(accountNumber, startDate, endDate, null, null, sortBy, sortType, searchKeyWord);
					UnitTrustTemp temp = null;
					for (UniTrustTrxHistoryResponse item : historyData) {
						temp = new UnitTrustTemp();
						
						temp.setAccountNo(accountNumber);
						temp.setCurrencyCode(item.getCurrencyCode());
						temp.setTransactionDate(item.getTransactionDate());
						temp.setEffectiveDate(item.getEffectiveDate());
						temp.setReferenceNo(item.getReferenceNo());
						temp.setTransactionType(item.getTransactionType().toString());
						temp.setAmount(item.getAmount());
						temp.setNav(item.getNav());
						temp.setFeeAmount(item.getFeeAmount());
						temp.setUnit(item.getUnit());
						temp.setStatus(item.getStatus().toString());
						temp.setTransactionSource(item.getTransactionSource());
						temp.setStatusStr("TBD");
						temp.setPeriode("TBD");
						temp.setTransactionTypeStr("TBD");
						
						listTemp.add(temp);
					}
					
//					service.insertTemporary(listTemp);
				}
								
			}else{
				//int start = (page-1)*countPerPage+1;
				//int end = page*countPerPage;
				List<UniTrustTrxHistoryResponse> historyData = service.getByDateStatement(accountNumber, startDate, endDate, null, null, sortBy, sortType, searchKeyWord);
				UnitTrustTemp temp = null;
				for (UniTrustTrxHistoryResponse item : historyData) {
					temp = new UnitTrustTemp();
					
					temp.setAccountNo(accountNumber);
					temp.setCurrencyCode(item.getCurrencyCode());
					temp.setTransactionDate(item.getTransactionDate());
					temp.setEffectiveDate(item.getEffectiveDate());
					temp.setReferenceNo(item.getReferenceNo());
					temp.setTransactionType(item.getTransactionType().toString());
					temp.setAmount(item.getAmount());
					temp.setNav(item.getNav());
					temp.setFeeAmount(item.getFeeAmount());
					temp.setUnit(item.getUnit());
					temp.setStatus(item.getStatus().toString());
					temp.setTransactionSource(item.getTransactionSource());
					temp.setStatusStr("TBD");
					temp.setPeriode("TBD");
					temp.setTransactionTypeStr("TBD");
					
					listTemp.add(temp);
				}
				
//				service.insertTemporary(listTemp);
			}
			
			//print-csv
			String tempDir = CommonUtils.getTempDir(confProperties);
			String uuid = UUID.randomUUID().toString();
			FileOutputStream fos = new FileOutputStream(tempDir+uuid);
			
			String separator = CommonUtils.getCsvDelimiter(sysParamDao.getParameterValues());
			char customQuote = '"';
			
			StringBuilder sb = new StringBuilder();
			
			for (UnitTrustTemp item : listTemp) {
				List<String> datas = new ArrayList<String>();
				
				datas.add(item.getReferenceNo());
				datas.add(DateUtility.formatDate(item.getTransactionDate()));
				datas.add(DateUtility.formatDate(item.getEffectiveDate()));
				datas.add(this.getTransTypeUT(item.getTransactionType()));
				datas.add(StringUtility.formatNumber(item.getAmount()));
				datas.add(StringUtility.formatNumber(item.getNav()));
				datas.add(StringUtility.formatNumber(item.getFeeAmount()));
				datas.add(StringUtility.formatNumber(item.getUnit()));
				datas.add(this.getStatusUT(item.getStatus()));
				datas.add(this.getTransSourceUT(item.getTransactionSource()));
				
				sb.append(CSVUtils.writeLineOut( datas, separator, customQuote));
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
			response.setData(uuid);
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
				if( !sortBy.equals("reference number") && !sortBy.equals("transaction date") && !sortBy.equals("effective date") 
						&& !sortBy.equals("transaction amount") && !sortBy.equals("transaction type")
						&& !sortBy.equals("nav") && !sortBy.equals("unit")
						&& !sortBy.equals("status") && !sortBy.equals("transaction source") ){
					errMsg.add("sortBy: invalid value, valid value are \"reference number\", \"transaction date\", \"effective date\", "
							+ "\"transaction amount\", \"transaction type\", \"nav\", \"unit\", \"status\" or \"transaction source\"");
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
			
			Integer page = null;
			if( strPage != null )
				page = Integer.parseInt(input.getPage());
			
			Integer countPerPage = null;
			if( strCountPerPage != null )
				countPerPage = Integer.parseInt(strCountPerPage);
			
			String accountNumber = input.getAccountNumber();
			int count = Integer.parseInt(strCount);
			String searchKeyWord = input.getSearchKeyWord();
			
			List<UnitTrustTemp> listTemp = new ArrayList<UnitTrustTemp>();
			
			response = getRecentActivity(input.getUserId(), input.getCifKey(), accountNumber,
					1, count, sortType, searchKeyWord);
			if( response.getResultCode() == ResponseErrorCode.SUCCESS ){
				List<UniTrustTrxHistoryResponse> result = new ArrayList<UniTrustTrxHistoryResponse>();
				@SuppressWarnings("unchecked")
				List<UniTrustTrxHistoryResponse> recentData = (List<UniTrustTrxHistoryResponse>)response.getData();
				List<UniTrustTrxHistoryResponse> historyData = service.getByRecentTransactionsStatement(accountNumber, count, null, null, sortBy, sortType, searchKeyWord );
				if( sortType.equalsIgnoreCase("DESC") ){
					result.addAll(recentData);
					result.addAll(historyData);
				}else{
					result.addAll(historyData);
					result.addAll(recentData);
				}
				
				UnitTrustTemp temp = null;
				for (UniTrustTrxHistoryResponse item : result) {
					temp = new UnitTrustTemp();
					
					temp.setAccountNo(accountNumber);
					temp.setCurrencyCode(item.getCurrencyCode());
					temp.setTransactionDate(item.getTransactionDate());
					temp.setEffectiveDate(item.getEffectiveDate());
					temp.setReferenceNo(item.getReferenceNo());
					temp.setTransactionType(item.getTransactionType().toString());
					temp.setAmount(item.getAmount());
					temp.setNav(item.getNav());
					temp.setFeeAmount(item.getFeeAmount());
					temp.setUnit(item.getUnit());
					temp.setStatus(item.getStatus().toString());
					temp.setTransactionSource(item.getTransactionSource());
					temp.setStatusStr("TBD");
					temp.setPeriode("TBD");
					temp.setTransactionTypeStr("TBD");
					
					listTemp.add(temp);
				}
				
//				service.insertTemporary(listTemp);
				
				//print-csv
				String tempDir = CommonUtils.getTempDir(confProperties);
				String uuid = UUID.randomUUID().toString();
				String contentType = "text/csv";
				FileOutputStream fos = new FileOutputStream(tempDir+uuid);
				
				String separator = CommonUtils.getCsvDelimiter(sysParamDao.getParameterValues());
				char customQuote = '"';
				
				StringBuilder sb = new StringBuilder();
				
				for (UnitTrustTemp item : listTemp) {
					List<String> datas = new ArrayList<String>();
					
					datas.add(item.getReferenceNo());
					datas.add(DateUtility.formatDate(item.getTransactionDate()));
					datas.add(DateUtility.formatDate(item.getEffectiveDate()));
					datas.add(this.getTransTypeUT(item.getTransactionType()));
					datas.add(StringUtility.formatNumber(item.getAmount()));
					datas.add(StringUtility.formatNumber(item.getNav()));
					datas.add(StringUtility.formatNumber(item.getFeeAmount()));
					datas.add(StringUtility.formatNumber(item.getUnit()));
					datas.add(this.getStatusUT(item.getStatus()));
					datas.add(this.getTransSourceUT(item.getTransactionSource()));
					
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
			}
			return response;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}
	
	private String getTransTypeUT(String transType) {
		String result = "";
		
		if (transType.equals("1")) {
			result = "Subscription";
		} else if (transType.equals("2")) {
			result = "Redemption";
		} else if (transType.equals("3")) {
			result = "Switching In";
		} else if (transType.equals("4")) {
			result = "Switching Out";
		}
		
		return result;
	}
	
	private String getStatusUT(String status) {
		String result = "";
		
		if (status.equals("0")) {
			result = "In Progress";
		} else if (status.equals("1")) {
			result = "Succcessful";
		} else if (status.equals("2")) {
			result = "Rejected";
		} else if (status.equals("3")) {
			result = "Cancelled (by Branch)";
		} else if (status.equals("4")) {
			result = "Cancelled (by PO)";
		}
		
		return result;
	}
	
	private String getTransSourceUT(String transSource) {
		String result = "";
		
		if (transSource.equalsIgnoreCase("m")) {
			result = "Mobile Banking";
		} else if (transSource.equalsIgnoreCase("i")) {
			result = "Internet Banking";
		} else if (transSource.equalsIgnoreCase("b")) {
			result = "Branch";
		} else if (transSource.equalsIgnoreCase("p")) {
			result = "Phone";
		}
		
		return result;
	}
}
