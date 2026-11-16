package com.optima.oraclejdbc.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisp.schemas.soa.wsdl.service.wbst_inquiry_ccbalance.operationimpl.services.wbst__inquiry__ccbalance.processes.WBST_Inquiry_CCBalanceStub;
import com.nisp.schemas.soa.wsdl.service.wbst_inquiry_ccpoints.operationimpl.services.wbst__inquiry__ccpoints.processes.WBST_Inquiry_CCPointsStub;
import com.nisp.schemas.soa.wsdl.service.wbst_inquiry_ccunbilledactivity.operationimpl.services.wbst__inquiry__ccunbilledactivity.processes.WBST_Inquiry_CCUnbilledActivityStub;
import com.ocbc.schemas.soa.emf.common.body.ErrorDetail_type0;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_ccbalance.ServiceEnvelope;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_ccbalance.ServiceEnvelopeE;
import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.daoimpl.CCRegexDao;
import com.optima.oraclejdbc.daoimpl.ContentParameterDao;
import com.optima.oraclejdbc.daoimpl.CreditCardInfoDao;
import com.optima.oraclejdbc.daoimpl.MappingProductDao;
import com.optima.oraclejdbc.daoimpl.SystemParameterDao;
import com.optima.oraclejdbc.elixirprop.CreditCardProp;
import com.optima.oraclejdbc.model.CCUnbilledTrans;
import com.optima.oraclejdbc.model.CreditCardInfo;
import com.optima.oraclejdbc.model.ElixirModel;
import com.optima.oraclejdbc.model.MappingProduct;
import com.optima.oraclejdbc.model.backoffice.BOCCRegex;
import com.optima.oraclejdbc.requestinput.creditcard.BillingStatementInput;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.response.creditcard.AccountBalanceResponse;
import com.optima.oraclejdbc.response.creditcard.AccountHeaderResponse;
import com.optima.oraclejdbc.response.creditcard.BillingStatementResponse;
import com.optima.oraclejdbc.response.creditcard.CreditCardBalanceResponse;
import com.optima.oraclejdbc.response.creditcard.CustomerHeaderResponse;
import com.optima.oraclejdbc.response.creditcard.RateInfoResponse;
import com.optima.oraclejdbc.response.creditcard.RewardSummaryResponse;
import com.optima.oraclejdbc.response.creditcard.TransactionDetailResponse;
import com.optima.oraclejdbc.response.creditcard.UnbilledTransactionDetailResponse;
import com.optima.oraclejdbc.response.creditcard.UnbilledTransactionResponse;
import com.optima.oraclejdbc.service.AccountListRegisterService;
import com.optima.oraclejdbc.service.CCService;
import com.optima.oraclejdbc.tibco.creditcard.CreditCardBalanceServiceEnvelopeE;
import com.optima.oraclejdbc.tibco.creditcard.CreditCardPointsServiceEnvelopeE;
import com.optima.oraclejdbc.tibco.creditcard.CreditCardUnbilledServiceEnvelopeE;
import com.optima.oraclejdbc.utils.CCRegexUtils;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.RsaUtil;
import com.optima.oraclejdbc.utils.StubUtil;

@RestController
@RequestMapping(value="/creditcard")
public class CreditCardController {

	private static final Logger logger = Logger.getLogger(CreditCardController.class);
	
	@Autowired
	CCService service;	
	
	@Autowired
	CreditCardInfoDao ccInfoDao;
	
	@Autowired
	MappingProductDao mappingProductDao;
	
	@Autowired
	CCRegexDao ccRegexDao;
	
	@Autowired
	StubUtil stubUtil;
	
	@Autowired
	RsaUtil rsaUtil;
	
	@Autowired
	AccountListRegisterService accountListService;
	
	@Resource(name="confProp")
	private java.util.Properties confProperties;
	
	@Autowired
	SystemParameterDao sysParamDao;
	
	@Autowired
	ContentParameterDao contentParamDao;
	
	@RequestMapping(value="/billingstatement", method=RequestMethod.POST)
	public Serializable billingStatement(@Valid @ModelAttribute BillingStatementInput input, BindingResult bindingResult){
		try{
			Response response = new Response();
			
			List<String> errMsg = new ArrayList<String>();
			if( !CommonUtils.validateInput(bindingResult, errMsg) ){
				response.setResultCode(ResponseErrorCode.INVALID_REQUEST_INPUT);
				response.setMessage(ResponseErrorCode.MSG_INVALID_REQUEST_INPUT);
				response.setErrMsg(errMsg);
			}else{
				String strAccountNumber = null;
				String strCustomerNumber = input.getCustomerNumber();
				if( strCustomerNumber == null ){
					strAccountNumber = input.getAccountNumber();
				}
				
				if( strCustomerNumber == null && strAccountNumber == null ){
					errMsg.add("customerNumber/accountNumber: one of customer number or account number must be filled");
				}
				
				String strYear = input.getYear();
				CommonUtils.checkPositiveInteger("year", strYear, errMsg);
				String strMonth = input.getMonth();
				CommonUtils.checkPositiveInteger("month", strMonth, errMsg);
				if( errMsg.size() > 0 ){
					response.setResultCode(ResponseErrorCode.INVALID_REQUEST_INPUT);
					response.setMessage(ResponseErrorCode.MSG_INVALID_REQUEST_INPUT);
					response.setErrMsg(errMsg);
					return response;
				}
				
				int year = Integer.parseInt(strYear);
				int month = Integer.parseInt(strMonth);
				BillingStatementResponse bsr = generateStatement(strCustomerNumber, strAccountNumber, year, month);
				if( bsr != null ){
					response.setData(bsr);
					response.setResultCode(ResponseErrorCode.SUCCESS);
					response.setMessage(ResponseErrorCode.MSG_SUCCESS);
				}else{
					response.setResultCode(ResponseErrorCode.NO_RECORD_FOUND);
					response.setMessage(ResponseErrorCode.MSG_NO_RECORD_FOUND);
				}
				
			}
			return response;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}
	
	@RequestMapping(value = "getsummary", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getSummary(@RequestParam String userId, @RequestParam String cifKey, 
			@RequestParam String accountNumber) {
		try {
			Response res = new Response();
			
			//stub
			String strContextPath = CommonUtils.getContextPath(confProperties);			
			WBST_Inquiry_CCBalanceStub stub = new WBST_Inquiry_CCBalanceStub(strContextPath+"Services/WBST_Inquiry_CCBalance/Processes/MainRequestResponse_HTTP");
//			WBST_Inquiry_CCBalanceStub stub = new WBST_Inquiry_CCBalanceStub(strContextPath+"services/CCBalance_MainRequestResponse__HTTP");
			ServiceEnvelopeE serviceEnvelopeE = CreditCardBalanceServiceEnvelopeE.instance();
			CreditCardBalanceServiceEnvelopeE.setUserId(serviceEnvelopeE, userId);
			CreditCardBalanceServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
			CreditCardBalanceServiceEnvelopeE.setOperationCode(serviceEnvelopeE, "100");
			
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
				List<CreditCardBalanceResponse> data = service.getSummary(serviceEnvelope.getServiceBody().getRsDetail()
						.getRESConsumer().getRESConsumerSequence_type1());
				getPoints(cifKey, data);
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

	@RequestMapping(value = "getaccdetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getAccountDetail(@RequestParam String userId, @RequestParam String cifKey, 
			@RequestParam String ccNumber) {
		try {
			Response res = new Response();
			
			//stub
			String strContextPath = CommonUtils.getContextPath(confProperties);			
			WBST_Inquiry_CCBalanceStub stub = new WBST_Inquiry_CCBalanceStub(strContextPath+"Services/WBST_Inquiry_CCBalance/Processes/MainRequestResponse_HTTP");
//			WBST_Inquiry_CCBalanceStub stub = new WBST_Inquiry_CCBalanceStub(strContextPath+"services/CCBalance_MainRequestResponse__HTTP");
			ServiceEnvelopeE serviceEnvelopeE = CreditCardBalanceServiceEnvelopeE.instance();
			CreditCardBalanceServiceEnvelopeE.setUserId(serviceEnvelopeE, userId);
			CreditCardBalanceServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
			CreditCardBalanceServiceEnvelopeE.setCreditCardNumber(serviceEnvelopeE, ccNumber);
			CreditCardBalanceServiceEnvelopeE.setOperationCode(serviceEnvelopeE, "100");
			
			
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
				List<CreditCardBalanceResponse> data = service.getSummary(serviceEnvelope.getServiceBody().getRsDetail()
						.getRESConsumer().getRESConsumerSequence_type1());
				getPoints(cifKey, data);
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
	
	private void getPoints(String cifKey, List<CreditCardBalanceResponse> data) {
		for (CreditCardBalanceResponse resp : data) {
			try {
				//stub
				String strContextPath = CommonUtils.getContextPath(confProperties);				
				WBST_Inquiry_CCPointsStub stub = new WBST_Inquiry_CCPointsStub(strContextPath+"Services/WBST_Inquiry_CCPoints/Processes/MainRequestResponse_HTTP");
//				WBST_Inquiry_CCPointsStub stub = new WBST_Inquiry_CCPointsStub(strContextPath+"services/CCPoints_MainRequestResponse__HTTP");
				com.ocbc.schemas.soa.emf.service.wbst_inquiry_ccpoints.ServiceEnvelopeE serviceEnvelopeE = CreditCardPointsServiceEnvelopeE.instance();
				CreditCardPointsServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
				CreditCardPointsServiceEnvelopeE.setCreditCardNumber(serviceEnvelopeE, resp.getCreditCardNumber());
				
				com.ocbc.schemas.soa.emf.service.wbst_inquiry_ccpoints.ServiceEnvelopeE response = (com.ocbc.schemas.soa.emf.service.wbst_inquiry_ccpoints.ServiceEnvelopeE)stubUtil.request(stub, serviceEnvelopeE);
				if( response == null ){
					return;
				}
				com.ocbc.schemas.soa.emf.service.wbst_inquiry_ccpoints.ServiceEnvelope serviceEnvelope = response.getServiceEnvelope();			
				
				if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
					resp.setPointsBalance(serviceEnvelope.getServiceBody().getRsDetail().getRESConsumer().getCreditCardPointDetail().getPointsBalance().doubleValue());
					int date = serviceEnvelope.getServiceBody().getRsDetail().getRESConsumer().getCreditCardPointDetail().getExpiryDate().getDayOfDate().intValue();
					int month = serviceEnvelope.getServiceBody().getRsDetail().getRESConsumer().getCreditCardPointDetail().getExpiryDate().getMonthOfDate().intValue();
					int year = serviceEnvelope.getServiceBody().getRsDetail().getRESConsumer().getCreditCardPointDetail().getExpiryDate().getYearOfDate().intValue();
					if( year < 1970 )
						year += 2000;
					Calendar cal = Calendar.getInstance();
					cal.set(year, month, date);
					resp.setExpiryDate(cal.getTime());
				}else{
					resp.setPointsBalance(-1d);
				}	
				
			} catch (Exception e) {
				resp.setPointsBalance(-1d);
				logger.error("Error", e);
				
			}
		}		
	}
	
	@RequestMapping(value = "getunbilled", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getUnbilled(@RequestParam String cifKey, @RequestParam String creditCardNumber,
			@RequestParam(defaultValue="1") int page, @RequestParam(defaultValue="10") int countPerPage) {
		try {
			Response res = new Response();
			
			//stub
			String strContextPath = CommonUtils.getContextPath(confProperties);
			
			WBST_Inquiry_CCUnbilledActivityStub stub = new WBST_Inquiry_CCUnbilledActivityStub(strContextPath+"Services/WBST_Inquiry_CCUnbilledActivity/Processes/MainRequestResponse_HTTP");
//			WBST_Inquiry_CCUnbilledActivityStub stub = new WBST_Inquiry_CCUnbilledActivityStub(strContextPath+"services/CCUnbilled_MainRequestResponse__HTTP");
			
			com.ocbc.schemas.soa.emf.service.wbst_inquiry_ccunbilledactivity.ServiceEnvelopeE serviceEnvelopeE = CreditCardUnbilledServiceEnvelopeE.instance();
			CreditCardUnbilledServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
			CreditCardUnbilledServiceEnvelopeE.setCreditCardNumber(serviceEnvelopeE, creditCardNumber);
			
			com.ocbc.schemas.soa.emf.service.wbst_inquiry_ccunbilledactivity.ServiceEnvelopeE response = (com.ocbc.schemas.soa.emf.service.wbst_inquiry_ccunbilledactivity.ServiceEnvelopeE)stubUtil.request(stub, serviceEnvelopeE);
			if( response == null ){
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				List<String> errMsg = new ArrayList<String>();
				errMsg.add(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				res.setErrMsg(errMsg);
				return res;
			}
			com.ocbc.schemas.soa.emf.service.wbst_inquiry_ccunbilledactivity.ServiceEnvelope serviceEnvelope = response.getServiceEnvelope();			
			
			if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
				int start = (page-1)*countPerPage;
				int end = page*countPerPage-1;
				
				UnbilledTransactionResponse data = service.getUnbilled(serviceEnvelope.getServiceBody().getRsDetail()
						.getRESConsumer().getRESConsumerSequence_type1(), start, end);
				if( data.getCountTransaction() == 0 ){
					res.setResultCode(ResponseErrorCode.NO_RECORD_FOUND);
					res.setMessage(ResponseErrorCode.MSG_NO_RECORD_FOUND);
				}else{
					res.setResultCode(ResponseErrorCode.SUCCESS);
					res.setMessage(ResponseErrorCode.MSG_SUCCESS);
					res.setData(data);
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
	
	@RequestMapping(value = "print-unbilled", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void printUnbilled(@RequestParam String cifKey, @RequestParam String creditCardNumber, 
			@RequestParam int isPrint, @RequestParam(defaultValue="0") int oneStep,
			HttpServletResponse servletResponse) {
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			Response resp = new Response();
			
			//stub
			ElixirModel model = CommonUtils.getElixirModel(confProperties);
			String strContextPath = CommonUtils.getContextPath(confProperties);
			
			WBST_Inquiry_CCUnbilledActivityStub stub = new WBST_Inquiry_CCUnbilledActivityStub(strContextPath+"Services/WBST_Inquiry_CCUnbilledActivity/Processes/MainRequestResponse_HTTP");
//			WBST_Inquiry_CCUnbilledActivityStub stub = new WBST_Inquiry_CCUnbilledActivityStub(strContextPath+"services/CCUnbilled_MainRequestResponse__HTTP");
			
			com.ocbc.schemas.soa.emf.service.wbst_inquiry_ccunbilledactivity.ServiceEnvelopeE serviceEnvelopeE = CreditCardUnbilledServiceEnvelopeE.instance();
			CreditCardUnbilledServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
			CreditCardUnbilledServiceEnvelopeE.setCreditCardNumber(serviceEnvelopeE, creditCardNumber);
			
			com.ocbc.schemas.soa.emf.service.wbst_inquiry_ccunbilledactivity.ServiceEnvelopeE response = (com.ocbc.schemas.soa.emf.service.wbst_inquiry_ccunbilledactivity.ServiceEnvelopeE)stubUtil.request(stub, serviceEnvelopeE);
			if( response == null ){
				resp.setResultCode(ResponseErrorCode.INTERNAL_SERVER_ERROR);
				resp.setMessage(ResponseErrorCode.MSG_INTERNAL_SERVER_ERROR);
				PrintWriter out = servletResponse.getWriter();
				out.print(mapper.writeValueAsString(resp));
				return;
			}

			com.ocbc.schemas.soa.emf.service.wbst_inquiry_ccunbilledactivity.ServiceEnvelope serviceEnvelope = response.getServiceEnvelope();			
			
			if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
				//insert temp
				UnbilledTransactionResponse data = service.getUnbilled(serviceEnvelope.getServiceBody().getRsDetail()
						.getRESConsumer().getRESConsumerSequence_type1(), 0, Integer.MAX_VALUE);
				List<CCUnbilledTrans> listTemp = new ArrayList<CCUnbilledTrans>();
				CCUnbilledTrans temp = null;
				
				String ccName = "TBD";
				CreditCardInfo ccInfo = ccInfoDao.getInfo(creditCardNumber);
				if (ccInfo != null) {
					if (ccInfo.getProductId() != null) {
						MappingProduct mappingProduct = mappingProductDao.getProductById(ccInfo.getProductId());
						if (mappingProduct != null) {
							ccName = mappingProduct.getProductName();
						}
					}
				}
				for (UnbilledTransactionDetailResponse item : data.getTransactionDetails()) {
					temp = new CCUnbilledTrans();
					temp.setCreditCardNumber(creditCardNumber);
					temp.setCreditCardName(ccName);
					temp.setAmountSign("+");
					temp.setAmount(item.getAmount());
					temp.setTransDate(item.getTransactionDate());
					temp.setTransDesc(item.getDescription());
					
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
				
				String elWorkspace = model.getWorkspace();
				String reportName = elWorkspace + "/CCUnbilledTrans.rml";
				String contentType = "application/pdf";
				
				java.util.Properties prop = CreditCardProp.getCCProp(creditCardNumber, ccName, model.getProviderUrl());
				
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
						
						String encryptedPass = accountListService.getStatementPasswordByCifKey(cifKey);
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
					
					resp.setResultCode(ResponseErrorCode.SUCCESS);
					resp.setMessage(ResponseErrorCode.MSG_SUCCESS);
					resp.setData(uuid);
					
					//delete
					service.deleteTemporary(creditCardNumber);
				}finally{
					if( fos != null )
						fos.close();
				}
			} else {
				resp.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				resp.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				ErrorDetail_type0 errorDetail_type0[] = serviceEnvelope.getServiceBody().getError().getErrorDetail();
				ArrayList<String> errorCodes = new ArrayList<String>();
				for (ErrorDetail_type0 err : errorDetail_type0) {
					errorCodes.add(err.getErrorCode()+" : "+err.getErrorDescription());
				}
				resp.setErrMsg(errorCodes);
			}	
			if( oneStep == 0 ){
				PrintWriter out;
				try {
					out = servletResponse.getWriter();
					out.print(mapper.writeValueAsString(resp));
				} catch (IOException e) {
					logger.error("Error", e);
				}			
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
	
	@RequestMapping(value = "print-billingstatement", method = RequestMethod.POST)
	public void getPrintBillingStatement(@RequestParam String accountNumber,
			@RequestParam String month, @RequestParam String year, @RequestParam int isPrint, 
			@RequestParam(defaultValue="0") int isReprint, @RequestParam(defaultValue="0") int oneStep,
			HttpServletResponse servletResponse){
		
		try {
			Response response = new Response();
			
			int iYear = Integer.parseInt(year);
			int iMonth = Integer.parseInt(month);
			BillingStatementResponse statement = generateStatement(null, accountNumber, iYear, iMonth);
			if( statement == null ){
				servletResponse.getOutputStream().write(new byte[0]);
				return;
			}
			CustomerHeaderResponse ccCh = statement.getCustomerHeader();
			AccountHeaderResponse ccAh = statement.getAccountHeader();
			AccountBalanceResponse ccAb = statement.getAccountBalance();
			RewardSummaryResponse ccBs = statement.getRewardSummary();
			RateInfoResponse ccRi = statement.getRateInfo();
			
			//print
			String elHostname = CommonUtils.getElixirHostname(confProperties);
			int elPort = CommonUtils.getElixirPort(confProperties);
			String elUsername = CommonUtils.getElixirUsername(confProperties);
			String elPassword = CommonUtils.getElixirPassword(confProperties);
			String providerUrl = CommonUtils.getElixirProviderURL(confProperties);
			ERSClient ersClient = new ERSClient(elHostname, elPort, elUsername, elPassword);
			
			//check regex
			String logo = "";
			List<BOCCRegex> listCCRegex = ccRegexDao.getAllCCRegex();
			BOCCRegex ccRegex = CCRegexUtils.validatePrefix(listCCRegex, ccAh.getCustomerNumber());
			if (ccRegex != null) {
				logo = ccRegex.getImageUrl();
			}
			
			//put parameter
			String ccPromoMesgTop = contentParamDao.getParameterValue("CCEPMT");
			String ccPromoMesgBottom = contentParamDao.getParameterValue("CCEPMB");
			String ccBankNotes = contentParamDao.getParameterValue("CCEBN");
			java.util.Properties prop = CreditCardProp.getBillingStatementProp(ccCh, ccAh, ccAb, ccBs, ccRi, logo, ccPromoMesgTop, ccPromoMesgBottom, ccBankNotes, isReprint, ccAh.getCreditQuality(), providerUrl);
			
			String elWorkspace = CommonUtils.getElixirWorkspace(confProperties);
			String reportName = elWorkspace + "/CCBillingStatementNew.rml";
			String contentType = "application/pdf";
			
			String tempDir = CommonUtils.getTempDir(confProperties);
			String uuid = UUID.randomUUID().toString();			
			OutputStream fos;
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
				
				String encryptedPass = accountListService.getStatementPassword(accountNumber, 1); //1 = CC
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
			if( oneStep == 0 ){
				PrintWriter out;
				try {
					out = servletResponse.getWriter();
					ObjectMapper mapper = new ObjectMapper();
					out.print(mapper.writeValueAsString(response));
				} catch (IOException e) {
					logger.error("Error", e);
				}
				
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
	
	@RequestMapping(value = "img-billingstatement", method = RequestMethod.POST)
	public @ResponseBody void getImageBillingStatement(@RequestParam String accountNumber,
			@RequestParam String month, @RequestParam String year,
			HttpServletResponse response){
		
		try {
			int iYear = Integer.parseInt(year);
			int iMonth = Integer.parseInt(month);
			BillingStatementResponse statement = generateStatement(null, accountNumber, iYear, iMonth);
			ServletOutputStream outServlet = response.getOutputStream();
			if( statement != null ){
				CustomerHeaderResponse ccCh = statement.getCustomerHeader();
				AccountHeaderResponse ccAh = statement.getAccountHeader();
				AccountBalanceResponse ccAb = statement.getAccountBalance();
				RewardSummaryResponse ccBs = statement.getRewardSummary();
				RateInfoResponse ccRi = statement.getRateInfo();
				
				//print
				String elHostname = CommonUtils.getElixirHostname(confProperties);
				int elPort = CommonUtils.getElixirPort(confProperties);
				String elUsername = CommonUtils.getElixirUsername(confProperties);
				String elPassword = CommonUtils.getElixirPassword(confProperties);
				String providerUrl = CommonUtils.getElixirProviderURL(confProperties);
				ERSClient ersClient = new ERSClient(elHostname, elPort, elUsername, elPassword);
				
				//check regex
				String logo = "";
				List<BOCCRegex> listCCRegex = ccRegexDao.getAllCCRegex();
				BOCCRegex ccRegex = CCRegexUtils.validatePrefix(listCCRegex, ccAh.getCustomerNumber());
				if (ccRegex != null) {
					logo = ccRegex.getImageUrl();
				}
				
				String kolek = "";
				if (ccAh.getCreditQuality() != null) {
					kolek = ccAh.getCreditQuality();
				}
				
				//put parameter
				String ccPromoMesgTop = contentParamDao.getParameterValue("CCEPMT");
				String ccPromoMesgBottom = contentParamDao.getParameterValue("CCEPMB");
				String ccBankNotes = contentParamDao.getParameterValue("CCEBN");
				java.util.Properties prop = CreditCardProp.getBillingStatementProp(ccCh, ccAh, ccAb, ccBs, ccRi, logo, ccPromoMesgTop, ccPromoMesgBottom, ccBankNotes, 0, kolek, providerUrl);
				
				String elWorkspace = CommonUtils.getElixirWorkspace(confProperties);
				String reportName = elWorkspace + "/CCBillingStatementNew.rml";
				String contentType = "image/png";
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ersClient.renderReport(reportName, "application/pdf", baos, prop);
				ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
				
				PDDocument doc = PDDocument.load(bais);
				PDFRenderer render = new PDFRenderer(doc);
				
				BufferedImage bim = null;
				BufferedImage result = null;
				int i = 0;
				int y = 0;
				for(i = 0; i < doc.getNumberOfPages(); i++) {
					bim = render.renderImageWithDPI(i, 200, ImageType.RGB);
					if (i == 0) {
						result = new BufferedImage(bim.getWidth(), bim.getHeight() * doc.getNumberOfPages(), BufferedImage.TYPE_INT_RGB);
					}
//					ImageIOUtil.writeImage(bim, file2 + "-" + (i) + ".png", 100);
					
					result.getGraphics().drawImage(bim, 0, y, null);
					
					y += bim.getHeight();		        
				}
				
				ImageIO.write(result, "png", outServlet);
				
				doc.save(outServlet);
				doc.close();
				
				response.setContentType(contentType);
			}else{
				outServlet.write(new byte[0]);
			}
						
			
		} catch (Exception e) {
			logger.error("Error", e);
			try{
				ObjectMapper mapper = new ObjectMapper();
				PrintWriter out = response.getWriter();				
				out.print(mapper.writeValueAsString(CommonUtils.responseException(e)));
			}catch( Exception e1){
				logger.error("Error", e1);
			}			
		} 
	}
	
	private BillingStatementResponse generateStatement(String strCustomerNumber, String strAccountNumber, int year, int month) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		BillingStatementResponse statement = new BillingStatementResponse();
		CustomerHeaderResponse customerHeader;
		if( strCustomerNumber != null )
			customerHeader = service.getCustomerHeaderByCustomerNumber(strCustomerNumber, year, month);
		else
			customerHeader = service.getCustomerHeaderByAccountNumber(strAccountNumber, year, month);
		if( customerHeader == null )
			return null;
		statement.setCustomerHeader(customerHeader);
		
		AccountHeaderResponse accountHeader = service.getAccountHeader(customerHeader.getId());
		if( accountHeader == null )
			return null;
		statement.setAccountHeader(accountHeader);
		
		List<TransactionDetailResponse> transactionDetails;
		if( strCustomerNumber != null )
			transactionDetails = service.getTransactionDetails(strCustomerNumber, year, month);
		else
			transactionDetails = service.getTransactionDetails(accountHeader.getId());
		
		statement.setTransactionDetails(transactionDetails);
		
		AccountBalanceResponse accountBalance = service.getAccountBalance(customerHeader.getId());
		statement.setAccountBalance(accountBalance);
		
		RewardSummaryResponse rewardSummary = service.getRewardSummary(customerHeader.getId());
		statement.setRewardSummary(rewardSummary);
		
		RateInfoResponse rateInfo = service.getRateInfo(customerHeader.getId());
		statement.setRateInfo(rateInfo);
		return statement;
	}
	
	@RequestMapping(value="/exists", method=RequestMethod.POST)
	public Response exists(@RequestParam String creditCardNumber, @RequestParam String year, @RequestParam String month){
		try{
			Response response = new Response();
			int iYear = Integer.parseInt(year);
			int iMonth = Integer.parseInt(month);
			
			response.setResultCode(ResponseErrorCode.SUCCESS);
			response.setMessage(ResponseErrorCode.MSG_SUCCESS);
			response.setData(service.exists(creditCardNumber, iYear, iMonth));
			return response;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
	}
}
