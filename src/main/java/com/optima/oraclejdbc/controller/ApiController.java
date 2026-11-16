package com.optima.oraclejdbc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocbc.estatement.wbst_admin_generaterandomnumber.CustomServiceHeader;
import com.ocbc.schemas.soa.accounts.service.wbst_admin_generaterandomnumber.REQConsumer_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_admin_generaterandomnumber.RqDetail_type0;
import com.ocbc.schemas.soa.emf.common.body.ErrorDetail_type0;
import com.ocbc.schemas.soa.emf.service.wbst_admin_generaterandomnumber.ServiceBody;
import com.ocbc.schemas.soa.emf.service.wbst_admin_generaterandomnumber.ServiceEnvelope;
import com.ocbc.schemas.soa.emf.service.wbst_admin_generaterandomnumber.ServiceEnvelopeE;
import com.ocbc.schemas.soa.emf.service.wbst_admin_generaterandomnumber.ServiceHeader;
import com.ocbc.schemas.soa.wsdl.service.wbst_admin_generaterandomnumber.operationimpl.services.wbst__admin__generaterandomnumber.processes.WBST_Admin_GenerateRandomNumberStub;
import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.daoimpl.SystemParameterDao;
import com.optima.oraclejdbc.model.CustomerAddress;
import com.optima.oraclejdbc.model.CustomerInfo;
import com.optima.oraclejdbc.model.UserInfo;
import com.optima.oraclejdbc.model.estatement.ESAccountInfo;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.response.api.RandomNumberResponse;
import com.optima.oraclejdbc.service.ActivityLogService;
import com.optima.oraclejdbc.service.BOSendEmailService;
import com.optima.oraclejdbc.service.CustomerAddressService;
import com.optima.oraclejdbc.service.CustomerInfoService;
import com.optima.oraclejdbc.service.DDIService;
import com.optima.oraclejdbc.service.HouseKeepingService;
import com.optima.oraclejdbc.tibco.customerinfo.CustomerInfoRequest;
import com.optima.oraclejdbc.tibco.electronicdetails.ElectronicDetailsRequest;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.RsaUtil;
import com.optima.oraclejdbc.utils.StubUtil;

@RestController
@RequestMapping(value = "/api")
public class ApiController {
		
	private static final Logger logger = Logger.getLogger(ApiController.class);
	
	@Autowired
	CustomerInfoService customerInfoService;
	
	@Autowired
	StubUtil stubUtil;
	
	@Autowired
	RsaUtil rsaUtil;
	
	@Resource(name="confProp")
	private java.util.Properties confProperties;
	
	@Autowired
	DDIService ddiService;
	
	@Autowired
	ElectronicDetailsRequest electronicDetailsRequest;
	
	@Autowired
	CustomerInfoRequest customerInfoRequest;
	
	@Autowired
	ActivityLogService activityLogService;
	
	@Autowired
	SystemParameterDao sysParamDao;
	
	@Autowired
	CustomerAddressService customerAddressService;
	
	@Autowired
	HouseKeepingService hkService;
	
	@Autowired
	BOSendEmailService emailService;
	
	@RequestMapping(value = "forcehousekeeping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response forceHousekeeping(){
		try{
			Response res = new Response();
			hkService.houseKeeping();
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			return res;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
	}
	
	@RequestMapping(value = "getmodulusstring", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getModulusString() {
		try{
			Response res = new Response();
			String modulusString = sysParamDao.getParameterValue("LMS");
			if( modulusString == null )
				modulusString = "C48977640A703FDDAD9F955E0C0524EEE6121B72D69C4271D7220D41A5EA05D405C77B935B2E982F5BA02A06C7519EB959BC83B466A780B619EBEBE7E58FE1F4A4F9F797F3568A2A9CDD6DB7163A48E09D6ABD30A8B07B556CA68A246D4C2B80A2514CACEB714FAE0F8D0CF5D0B7A9BBB6D313B0C1DFA85D34FA29BE06F7AE23";
			
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			res.setData(modulusString);	
			return res;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}
		
	@RequestMapping(value = "getrandomnumber", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getRandomNumber() {
				
		try {
			Response res = new Response();
			String strContextPath = CommonUtils.getContextPath(confProperties);			
//			WBST_Admin_GenerateRandomNumberStub stub = new WBST_Admin_GenerateRandomNumberStub(strContextPath+"services/MainRequestResponse__HTTP");
			WBST_Admin_GenerateRandomNumberStub stub = new WBST_Admin_GenerateRandomNumberStub(strContextPath+"Services/WBST_Admin_GenerateRandomNumber/Processes/MainRequestResponse_HTTP");
			ServiceEnvelope serviceEnvelope=new ServiceEnvelope();
			ServiceHeader serviceHeader=CustomServiceHeader.createServiceHeader("WBST_Admin_GenerateRandomNumber", "000");

			serviceEnvelope.setServiceHeader(serviceHeader);
			
			ServiceBody serviceBody = new ServiceBody();
			RqDetail_type0 rqDetail_type0=new RqDetail_type0();
			REQConsumer_type0 rEQConsumer_type0 = new REQConsumer_type0();
			rEQConsumer_type0.setTransactionSequenceNumber(UUID.randomUUID().toString());
			rqDetail_type0.setREQConsumer(rEQConsumer_type0);
			serviceBody.setRqDetail(rqDetail_type0);
			
			serviceEnvelope.setServiceBody(serviceBody);
			
			ServiceEnvelopeE serviceEnvelopeE = new ServiceEnvelopeE();
			serviceEnvelopeE.setServiceEnvelope(serviceEnvelope);
			
			//System.out.println(serviceEnvelope.toString());
			
			ServiceEnvelopeE response = (ServiceEnvelopeE)stubUtil.request(stub, serviceEnvelopeE);
			if( response == null ){
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				List<String> errMsg = new ArrayList<String>();
				errMsg.add(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				res.setErrMsg(errMsg);				
				return res;
			}
			serviceEnvelope = response.getServiceEnvelope();		
			
			if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
				res.setResultCode(ResponseErrorCode.SUCCESS);
				res.setMessage(ResponseErrorCode.MSG_SUCCESS);
				res.setData(new RandomNumberResponse(serviceEnvelope.getServiceBody().getRsDetail().getRESConsumer()));				
			} else {
				ArrayList<String> errors = new ArrayList<String>();
				ErrorDetail_type0 errorDetail_type0[] = serviceEnvelope.getServiceBody().getError().getErrorDetail();
				for (ErrorDetail_type0 err : errorDetail_type0) {
					errors.add(err.getErrorCode()+": "+err.getErrorDescription());
				}
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				res.setErrMsg(errors);
			}
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
//		try {
//			ServiceEnvelope serviceEnvelope = new ServiceEnvelope();
//			RsDetail_type0 rsDetail_type0 = new RsDetail_type0();
//			RESConsumer_type0 resConsumer_type0 = new RESConsumer_type0();
//			
//			resConsumer_type0.setRandomID("463526");
//			resConsumer_type0.setRandomNumber("0000000000000000000000000000000000000000");
//			rsDetail_type0.setRESConsumer(resConsumer_type0);
//			ServiceBody serviceBody = new ServiceBody();
//			serviceEnvelope.setServiceBody(serviceBody);
//			serviceEnvelope.getServiceBody().setRsDetail(rsDetail_type0);
//			
//			if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
//				res.setResultCode(ResponseErrorCode.SUCCESS);
//				res.setMessage(ResponseErrorCode.MSG_SUCCESS);
//				res.setData(new RandomNumberResponse(serviceEnvelope.getServiceBody().getRsDetail().getRESConsumer()));				
//			} else {
//				ArrayList<String> errors = new ArrayList<String>();
//				ErrorDetail_type0 errorDetail_type0[] = serviceEnvelope.getServiceBody().getError().getErrorDetail();
//				for (ErrorDetail_type0 err : errorDetail_type0) {
//					System.out.println(err.getErrorCode());
//					errors.add(err.getErrorCode()+": "+err.getErrorDescription());
//				}
//				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
//				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
//				res.setData(errors);
//			}
//		} catch (Exception e) {
//			res.setResultCode(ResponseErrorCode.INTERNAL_SERVER_ERROR);
//			res.setMessage(ResponseErrorCode.MSG_INTERNAL_SERVER_ERROR);
//			res.setData(e.getStackTrace());
//			e.printStackTrace();
//		}			
	}
	
	@RequestMapping(value = "settings/changepassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response settingChangePassword(@RequestParam String cifKey, @RequestParam String password, @RequestParam String confirmPassword) {
		try {
			Response res = new Response();
			CustomerInfo customerInfo = customerInfoService.getUserByCIFKey(cifKey);
			if (customerInfo != null){
				if (password.equals(confirmPassword)){
					boolean sameOldPass = false;
					String encPass = customerInfo.getStatementPassword();
					if( encPass != null ){
						String oldPass = rsaUtil.rsaDecrypt(encPass);
						if( password.equals(oldPass) ){
							sameOldPass = true;
						}
					}
					if( sameOldPass ){
						res.setResultCode(ResponseErrorCode.SAME_OLD_PASSWORD);
						res.setMessage(ResponseErrorCode.MSG_SAME_OLD_PASSWORD);
					}else{
						customerInfo.setStatementPassword(rsaUtil.rsaEncrypt(password));
						customerInfoService.save(customerInfo);
						
						res.setResultCode(ResponseErrorCode.SUCCESS);
						res.setMessage(ResponseErrorCode.MSG_CHANGE_PASSWORD_SUCCESS);
					}
					
				} else {
					res.setResultCode(ResponseErrorCode.INVALID_REQUEST_INPUT);
					res.setMessage(ResponseErrorCode.MSG_PASSWORD_NOT_MATCH);
				}
			} else {
				res.setResultCode(ResponseErrorCode.NO_RECORD_FOUND);
				res.setMessage(ResponseErrorCode.MSG_CUST_NOT_EXIST);
			}
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}			
	}
	
	@RequestMapping(value = "settings/resetpassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response settingResetPassword(@RequestParam String cifKey) {
		try{
			Response res = new Response();
			ESAccountInfo esai = ddiService.getESAccountInfoByCifkey(cifKey);
			if( esai == null ){
				res.setResultCode(ResponseErrorCode.NO_RECORD_FOUND);
				res.setMessage(ResponseErrorCode.MSG_CUST_NOT_EXIST);
				return res;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy");
			String dob = sdf.format(esai.getDob());
			CustomerInfo customerInfo = customerInfoService.getUserByCIFKey(cifKey);
			if (customerInfo != null){
				customerInfo.setStatementPassword(rsaUtil.rsaEncrypt(dob));
				customerInfoService.save(customerInfo);
				
				res.setResultCode(ResponseErrorCode.SUCCESS);
				res.setMessage(ResponseErrorCode.MSG_RESET_PASSWORD_SUCCESS);
			} else {
				res.setResultCode(ResponseErrorCode.NO_RECORD_FOUND);
				res.setMessage(ResponseErrorCode.MSG_CUST_NOT_EXIST);
			}
			
			return res;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
	}
	
	@RequestMapping(value = "settings/changeemail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response settingChangeEmail(@RequestParam String cifKey
			, @RequestParam String email, @RequestParam String isInsert, @RequestParam String requestPerMonth) {
		try{
			CustomerInfo ci = customerInfoService.getUserByCIFKey(cifKey);
			ci.setIsMonthly(Integer.parseInt(requestPerMonth));
			customerInfoService.save(ci);
			if( isInsert.equals("1"))
				return electronicDetailsRequest.setCustomerAddress(cifKey, email);
			else
				return electronicDetailsRequest.updateCustomerAddress(cifKey, email);
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}
	
	@RequestMapping(value = "getcustomerinfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getCustomerInfo(@RequestParam String cifKey) {
		try{
			Response res = customerInfoRequest.getUserInfo(cifKey);
			if( res.getResultCode() == ResponseErrorCode.SUCCESS ){
				UserInfo userInfo = (UserInfo)res.getData();
				customerAddressService.saveBatch(cifKey, userInfo.getCustomerAddresses());
				CustomerAddress customAddr = customerAddressService.getCustomerCustomAddress(cifKey);
				if( customAddr != null )
					userInfo.getCustomerAddresses().add(customAddr);
				CustomerInfo ci = customerInfoService.getUserByCIFKey(cifKey);
				userInfo.setRequestPerMonth(ci.getIsMonthly());
			}
			return res;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}

	@RequestMapping(value = "init-setup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response initSetup(HttpServletRequest request) {
		try{
			Response res = new Response();
			List<Object> tempList = new ArrayList<Object>(request.getParameterMap().values());
			String[] actionName = new String[request.getParameterMap().values().size()];
			String[] urlMapping = (String[]) request.getParameterMap().keySet().toArray(new String[request.getParameterMap().keySet().size()]);
			for (int i = 0; i < request.getParameterMap().values().size(); i++) {
				actionName[i] = ((String[])tempList.get(i))[0].toString();
			}
			
			activityLogService.insertUrlMapping(urlMapping,actionName);
			
			return res;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}
	
	@RequestMapping(value = "getemailsetting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getEmailSetting(@RequestParam String cifKey) {
		try {
			Response res = new Response();
			CustomerInfo custInfo = customerInfoService.getUserByCIFKey(cifKey);
			if (custInfo != null) {
				res.setData(custInfo);
				res.setResultCode(ResponseErrorCode.SUCCESS);
				res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			} else {
				res.setResultCode(ResponseErrorCode.NO_RECORD_FOUND);
				res.setMessage(ResponseErrorCode.MSG_NO_RECORD_FOUND);
			}
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}				
	}

	@RequestMapping(value = "downloadfile/{uuid:.+}", method = RequestMethod.GET)
	public @ResponseBody void downloadFile(@PathVariable String uuid, HttpServletResponse servletResponse) {
		try {
			String tempDir = CommonUtils.getTempDir(confProperties);
			File file = new File(tempDir+uuid);
			if( !file.exists() )
				return;
			FileInputStream fis = new FileInputStream(file);
			byte buff[] = new byte[1024*100];
			ServletOutputStream sos = servletResponse.getOutputStream();
			int byteRead;
			while( (byteRead=fis.read(buff)) != -1){
				sos.write(buff, 0, byteRead);
			}
			
			fis.close();
			file.delete();
			
			
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
	
	@RequestMapping(value = "getaddresses", method = RequestMethod.POST)
	public Response getAddresses(@RequestParam String cifKey){
		try {
			Response res = new Response();
			List<CustomerAddress> addresses = customerAddressService.getCustomerAddresses(cifKey);
			
			if (addresses.size() > 0 ) {
				res.setData(addresses);
				res.setResultCode(ResponseErrorCode.SUCCESS);
				res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			} else {
				res.setResultCode(ResponseErrorCode.NO_RECORD_FOUND);
				res.setMessage(ResponseErrorCode.MSG_NO_RECORD_FOUND);
			}
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
	}
	
	@RequestMapping(value = "emailcallback/{emailId}", method = RequestMethod.GET)
	public Response emailCallback(@PathVariable String emailId, @RequestParam String email, 
			@RequestParam String subject){
		try{
			emailService.processContent("N--"+emailId+"--N", email, subject);
			Response res = new Response();
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			return res;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}	
	}
	
	@RequestMapping(value = "readnewemail", method = RequestMethod.GET)
	public Response readNewEmail(){
		try{
			emailService.processNewEmail();
			Response res = new Response();
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			return res;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
	}
	
	@RequestMapping(value = "generateexception", method = RequestMethod.GET)
	public Response gerateException(){
		RuntimeException ex = new RuntimeException("Generated exception");
		logger.error("Exception", ex);
		return CommonUtils.responseException(ex);
	}
	
	@RequestMapping(value = "downloadlog", method = RequestMethod.GET)
	public @ResponseBody void downloadLog(@RequestParam(defaultValue="/tmp/appServer.log") String logFile,
			HttpServletResponse servletResponse) {
		try {
			if( logFile.endsWith(".log") ){
				File fLogFile = new File(logFile);
				FileInputStream fis = new FileInputStream(fLogFile);
				byte buff[] = new byte[1024*100];
				ServletOutputStream sos = servletResponse.getOutputStream();
				int byteRead;
				while( (byteRead=fis.read(buff)) != -1){
					sos.write(buff, 0, byteRead);
				}
				servletResponse.setContentType("plain/text");
				servletResponse.setHeader("Content-disposition", "attachment; filename=" + fLogFile.getName());
				fis.close();				
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
}