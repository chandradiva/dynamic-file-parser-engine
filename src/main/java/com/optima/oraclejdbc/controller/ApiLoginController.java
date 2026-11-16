package com.optima.oraclejdbc.controller;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nisp.schemas.soa.wsdl.service.wbst_inquiry_accountbalance.operationimpl.services.wbst__inquiry__accountbalance.processes.WBST_Inquiry_AccountBalanceStub;
import com.ocbc.schemas.soa.accounts.service.wbst_admin_generaterandomnumber.RESConsumer;
import com.ocbc.schemas.soa.accounts.service.wbst_admin_loginchannelbanking.AccountsInfo_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_admin_loginchannelbanking.AccountsList_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_admin_loginchannelbanking.REQConsumer_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_admin_loginchannelbanking.RESConsumer_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_admin_loginchannelbanking.RqDetail_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_admin_loginchannelbanking.RsDetail_type0;
import com.ocbc.schemas.soa.emf.account.aggregates.AlternateAccountKeyDetail_type0;
import com.ocbc.schemas.soa.emf.account.aggregates.CustomerAccountsInfo_type0;
import com.ocbc.schemas.soa.emf.common.body.ErrorDetail_type0;
import com.ocbc.schemas.soa.emf.common.body.Error_type0;
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
import com.ocbc.schemas.soa.emf.service.wbst_admin_loginchannelbanking.ServiceBody;
import com.ocbc.schemas.soa.emf.service.wbst_admin_loginchannelbanking.ServiceEnvelope;
import com.ocbc.schemas.soa.emf.service.wbst_admin_loginchannelbanking.ServiceEnvelopeE;
import com.ocbc.schemas.soa.emf.service.wbst_admin_loginchannelbanking.ServiceHeader;
import com.ocbc.schemas.soa.emf.service.wbst_admin_loginchannelbanking.ServiceHeaderChoice_type1;
import com.ocbc.schemas.soa.wsdl.service.wbst_admin_loginchannelbanking.operationimpl.services.wbst__admin__loginchannelbanking.processes.WBST_Admin_LoginChannelBankingStub;
import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.model.AccountList;
import com.optima.oraclejdbc.model.ConfigValues;
import com.optima.oraclejdbc.model.CreditCardInfo;
import com.optima.oraclejdbc.model.LoginHistory;
import com.optima.oraclejdbc.model.User;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.response.login.LoginResponse;
import com.optima.oraclejdbc.service.AccountListRegisterService;
import com.optima.oraclejdbc.service.ApplicationParameterService;
import com.optima.oraclejdbc.service.LoginService;
import com.optima.oraclejdbc.service.UserService;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.StubUtil;

@RestController
@RequestMapping(value = "/api")
public class ApiLoginController {
	private static final Logger logger = Logger.getLogger(ApiLoginController.class);
	
	@Autowired
	AccountListRegisterService registerService;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ApplicationParameterService applicationParameterService;
	
	@Autowired
	StubUtil stubUtil;
	
	@Resource(name="confProp")
	private java.util.Properties confProperties;
	
	@RequestMapping(value = "login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Serializable login(@RequestParam String username, @RequestParam String randomId, 
			@RequestParam String x_pString, @RequestParam String x_cString) {
//		create login history object
		LoginHistory loginHistory = new LoginHistory(username);		
		try {
			Response response = new Response();
			

			User user = userService.getUserByUsername(username);
			Date lastLogin;
			if (user == null){
				user = new User();
				user.setUsername(username);
				user.setFailedLoginCount(0);
				user.setReadAgreement(User.IS_UNREAD_AGGREMENT);
				lastLogin = new Date();
			}else
				lastLogin = user.getLastLogin();
			
			user.setLastLogin(new Date());
			//stub
			String strContextPath = CommonUtils.getContextPath(confProperties);			
			
//			WBST_Admin_LoginChannelBankingStub stub = new WBST_Admin_LoginChannelBankingStub(strContextPath+"services/LoginChannelBanking_MainRequestResponse__HTTP");
			WBST_Admin_LoginChannelBankingStub stub = new WBST_Admin_LoginChannelBankingStub(strContextPath+"Services/WBST_Admin_LoginChannelBanking/Processes/MainRequestResponse_HTTP");
			
			ServiceHeader serviceHeader = new ServiceHeader();
			ServiceEnvelopeE serviceEnvelopeE = new ServiceEnvelopeE();
			ServiceEnvelope serviceEnvelope = new ServiceEnvelope();
			ServiceBody serviceBody = new ServiceBody();
			
			CommonDetail_type0 commonDetail_type0 = new CommonDetail_type0();
			commonDetail_type0.setServiceName("WBST_Admin_LoginChannelBanking");
			commonDetail_type0.setOperationCode("000");
			commonDetail_type0.setEMFVersion("000");
			commonDetail_type0.setServiceVersion("1.0");
			commonDetail_type0.setOperationVersion("000");
			commonDetail_type0.setRqUuid(UUID.randomUUID().toString());
			commonDetail_type0.setRqExpiryTime(new BigInteger("99"));
			CommonDetailChoice_type2 commonDetailChoice_type2=new CommonDetailChoice_type2();
			commonDetailChoice_type2.setRqDateTime(Calendar.getInstance());
			commonDetail_type0.setCommonDetailChoice_type2(commonDetailChoice_type2);
			
			RecordControl_type0 recordControl_type0=new RecordControl_type0();
			RecordControlChoice_type0 recordControlChoice_type0 = new RecordControlChoice_type0();
			ClientControl_type0 clientControl_type0=new ClientControl_type0();
			clientControl_type0.setRqNoOfRecords(new BigInteger("0"));
			clientControl_type0.setCursor("1");
			
			recordControlChoice_type0.setClientControl(clientControl_type0);
			recordControl_type0.setRecordControlChoice_type0(recordControlChoice_type0);
			commonDetail_type0.setRecordControl(recordControl_type0);
			
			CommonDetailChoice_type3 commonDetailChoice_type3 = new CommonDetailChoice_type3();
			RqNature_type0 rqNature_type0=new RqNature_type0();
			rqNature_type0.setTransactionCode("1");
			rqNature_type0.setTransactionRefNo(new BigInteger("1"));
			rqNature_type0.setTransactionMode("000");
			rqNature_type0.setRqStatus("000");
			commonDetailChoice_type3.setRqNature(rqNature_type0);
			
			commonDetail_type0.setCommonDetailChoice_type3(commonDetailChoice_type3);
			
			RemarkDetail_type0 remarkDetail_type0 = new RemarkDetail_type0();
			remarkDetail_type0.setRemarkType("1");
			remarkDetail_type0.setRemarkCode("2");
			remarkDetail_type0.setRemarkStatus("1");
			remarkDetail_type0.setRemarkDescription("Description");
			remarkDetail_type0.setRemarkActor("herman");
			commonDetail_type0.setRemarkDetail(remarkDetail_type0);
			
			serviceHeader.setCommonDetail(commonDetail_type0);
			
			ServiceHeaderChoice_type1 serviceHeaderChoice_type1 = new ServiceHeaderChoice_type1();
			ClientDetail_type0 clientDetail_type0 = new ClientDetail_type0();
			clientDetail_type0.setID("NISP");
			clientDetail_type0.setOrganization("NISP");
			clientDetail_type0.setSubsystem("NISP");
			RegionDetail_type0 regionDetail_type0 = new RegionDetail_type0();
			regionDetail_type0.setCountryCode("ID");
			regionDetail_type0.setRegionCode("BDG");
			clientDetail_type0.setRegionDetail(regionDetail_type0);
			clientDetail_type0.setChannel("CONS"); //tergantung consumer atau coorporate
			UserDetail_type0 userDetail_type0 = new UserDetail_type0();
			userDetail_type0.setUserId("77777");
			userDetail_type0.setUserRole("");
			userDetail_type0.setUserPassword("");
			clientDetail_type0.setUserDetail(userDetail_type0);
			serviceHeaderChoice_type1.setClientDetail(clientDetail_type0);
			serviceHeader.setServiceHeaderChoice_type1(serviceHeaderChoice_type1);
			
			RqDetail_type0 rqDetail_type0 = new RqDetail_type0();
			REQConsumer_type0 reqConsumer_type0 = new REQConsumer_type0();
			reqConsumer_type0.setFUID(username);
			reqConsumer_type0.setLoginID(username);
			reqConsumer_type0.setRandomID(randomId);
			reqConsumer_type0.setEncodingParameter(x_pString);
			reqConsumer_type0.setExtendedPinBlock(x_cString);
			reqConsumer_type0.setCIFKey("");
			
			rqDetail_type0.setREQConsumer(reqConsumer_type0);
			serviceBody.setRqDetail(rqDetail_type0);
			serviceEnvelope.setServiceBody(serviceBody);
			serviceEnvelope.setServiceHeader(serviceHeader);
			
			serviceEnvelopeE.setServiceEnvelope(serviceEnvelope);
			ServiceEnvelopeE res = (ServiceEnvelopeE)stubUtil.request(stub, serviceEnvelopeE);
			if( res == null ){
				response.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				response.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				List<String> errMsg = new ArrayList<String>();
				errMsg.add(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				response.setErrMsg(errMsg);
				
				loginHistory.setIsSuccess(LoginHistory.FAILED);
				
				userService.save(user);
				loginService.save(loginHistory);
				return response;
			}
			
			//response
			serviceEnvelope = res.getServiceEnvelope();
			
			// set error
//			serviceBody.setRsDetail(null);
//			Error_type0 error = new Error_type0();
//			ErrorDetail_type0[] errorDetails = new ErrorDetail_type0[2];
//			errorDetails[0] = new ErrorDetail_type0();
//			errorDetails[0].setErrorCode("400");
//			errorDetails[0].setErrorDescription("pin terlalu pendek");
//			errorDetails[1] = new ErrorDetail_type0();
//			errorDetails[1].setErrorCode("401");
//			errorDetails[1].setErrorDescription("ya jadinya password salah lah");
//			error.setErrorDetail(errorDetails);
//			serviceBody.setError(error);
			// end of set error
			
			
			if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
//				record login history
				loginHistory.setIsSuccess(LoginHistory.SUCCESS);
				
				LoginResponse loginResponse = new LoginResponse(serviceEnvelope.getServiceBody().getRsDetail().getRESConsumer());
				registerService.registerESAccountInfo(serviceEnvelope.getServiceBody().getRsDetail().getRESConsumer());
				List<AccountList> accountList = registerService.accountListRegister(username, 
						serviceEnvelope.getServiceBody().getRsDetail().getRESConsumer());
				List<CreditCardInfo> creditCardInfo = registerService.creditCardInfoRegister(username, 
						serviceEnvelope.getServiceBody().getRsDetail().getRESConsumer());
				loginResponse.setAccountList(accountList);
				loginResponse.setCardList(creditCardInfo);
				loginResponse.setReadAgreement(user.getReadAgreement());
				loginResponse.setFailedLogin(user.getFailedLoginCount());
				loginResponse.setLastLogin(lastLogin);
				loginResponse.setConfigValues(new ConfigValues(applicationParameterService.getParameterValues()));
				response.setResultCode(ResponseErrorCode.SUCCESS);
				response.setMessage(ResponseErrorCode.MSG_SUCCESS);
				response.setData(loginResponse);
				
			} else {
//				record login history
				user.setFailedLoginCount(user.getFailedLoginCount()+1);
				loginHistory.setIsSuccess(LoginHistory.FAILED);
				
				response.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				response.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				ErrorDetail_type0 errorDetail_type0[] = serviceEnvelope.getServiceBody().getError().getErrorDetail();
				ArrayList<String> errorCodes = new ArrayList<String>();
				for (ErrorDetail_type0 err : errorDetail_type0) {
					errorCodes.add(err.getErrorCode()+" : "+err.getErrorDescription());
				}
				response.setErrMsg(errorCodes);
			}
			
			
//			/************* DUMMY ************/
//			loginHistory.setIsSuccess(LoginHistory.SUCCESS);
//			
//			ServiceEnvelope serviceEnvelope = new ServiceEnvelope();
//			RsDetail_type0 rsDetail_type0 = new RsDetail_type0();
//			RESConsumer_type0 resConsumer_type0 = new RESConsumer_type0();
//			resConsumer_type0.setShortName("463526");
//			rsDetail_type0.setRESConsumer(resConsumer_type0);
//			ServiceBody serviceBody = new ServiceBody();
//			serviceEnvelope.setServiceBody(serviceBody);
//			serviceEnvelope.getServiceBody().setRsDetail(rsDetail_type0);
//			
//			LoginResponse loginResponse = new LoginResponse(serviceEnvelope.getServiceBody().getRsDetail().getRESConsumer());
////			List<AccountList> accountList = registerService.accountListRegister(username, 
////					serviceEnvelope.getServiceBody().getRsDetail().getRESConsumer());
////			List<CreditCardInfo> creditCardInfo = registerService.creditCardInfoRegister(username, 
////					serviceEnvelope.getServiceBody().getRsDetail().getRESConsumer());
////			loginResponse.setAccountList(accountList);
////			loginResponse.setCardList(creditCardInfo);
//			
//			user.setFailedLoginCount(user.getFailedLoginCount()+1);
//			
//			loginResponse.setReadAgreement(user.getReadAgreement());
//			loginResponse.setFailedLogin(user.getFailedLoginCount());
//			
//			response.setResultCode(ResponseErrorCode.SUCCESS);
//			response.setMessage(ResponseErrorCode.MSG_SUCCESS);
//			response.setData(loginResponse);
//			/************* END OF DUMMY ************/
			userService.save(user);
			loginService.save(loginHistory);
			
			return response;
		} catch (Exception e) {
			loginHistory.setIsSuccess(LoginHistory.FAILED);
			try {
				loginService.save(loginHistory);
			} catch (NoSuchMethodException | SecurityException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e1) {				
				logger.error("Error", e1);
			}
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
		
	}
	
	@RequestMapping(value = "read/agreement", method = RequestMethod.POST)
	public @ResponseBody Response confirmReadAgreement(@RequestParam String username) {
		try {
			Response res = new Response();
			
			User user = userService.getUserByUsername(username);
			
			if (user != null){
				user.setReadAgreement(User.IS_READ_AGGREMENT);
				userService.save(user);
				
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
	
	@RequestMapping(value = "reset/failed", method = RequestMethod.POST)
	public @ResponseBody Response resetFailed(@RequestParam String username) {
		try {
			Response res = new Response();
			User user = userService.getUserByUsername(username);
			
			if (user != null){
				user.setFailedLoginCount(0);
				userService.save(user);
				
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
	
}
