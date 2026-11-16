package com.optima.oraclejdbc.tibco.savingplan;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.axis2.AxisFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailsavingsplan.RESConsumer_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailsavingsplan.RsDetail_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailsavingsplan.SPDetailInfo_type0;
import com.ocbc.schemas.soa.emf.common.body.ErrorDetail_type0;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailsavingsplan.ServiceBody;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailsavingsplan.ServiceEnvelope;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailsavingsplan.ServiceEnvelopeE;
import com.ocbc.schemas.soa.wsdl.service.wbst_inquiry_detailsavingsplan.operationimpl.services.wbst__inquiry__detailsavingsplan.processes.WBST_Inquiry_DetailSavingsPlanStub;
import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.response.dailystatement.SavingPlanResponse;
import com.optima.oraclejdbc.service.SavingPlanService;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.StubUtil;

@Service
public class SavingPlanRequest {
	@Autowired
	SavingPlanService service;
	
	@Autowired
	StubUtil stubUtil;
	
	@Resource(name="confProp")
	private java.util.Properties confProperties;
	
	private WBST_Inquiry_DetailSavingsPlanStub getStub() throws AxisFault{
		String strContextPath = CommonUtils.getContextPath(confProperties);
		WBST_Inquiry_DetailSavingsPlanStub stub = new WBST_Inquiry_DetailSavingsPlanStub(strContextPath+"Services/WBST_Inquiry_DetailSavingsPlan/Processes/MainRequestResponse_HTTP");
		return stub;
	}
	
	public Response getSummary(String userId, String fuid, String cifKey) throws AxisFault, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		Response res = new Response();
		
		//stub
		WBST_Inquiry_DetailSavingsPlanStub stub = getStub();
		
		ServiceEnvelopeE serviceEnvelopeE = SavingPlanServiceEnvelopeE.instance();
		SavingPlanServiceEnvelopeE.setUserId(serviceEnvelopeE, userId);
		SavingPlanServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
		SavingPlanServiceEnvelopeE.setFUID(serviceEnvelopeE, fuid);
		
		ServiceEnvelopeE response = (ServiceEnvelopeE)stubUtil.request(stub, serviceEnvelopeE);
		if( response == null ){
			res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
			res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
			res.setData(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
			return res;
		}
		ServiceEnvelope serviceEnvelope = response.getServiceEnvelope();
		
		// dummy response
		
//		ServiceEnvelope serviceEnvelope = new ServiceEnvelope();
//		ServiceBody serviceBody = new ServiceBody();
//		
//		// dummy response
//		RsDetail_type0 rsDetail_type0 = new RsDetail_type0();
//		RESConsumer_type0 resConsumer_type0 = new RESConsumer_type0();
//		
//		String strContextPath = CommonUtils.getContextPath(properties);
//		WBST_Inquiry_DetailSavingsPlanStub stub = new WBST_Inquiry_DetailSavingsPlanStub(strContextPath+"Services/WBST_Inquiry_DetailSavingsPlan/Processes/MainRequestResponse_HTTP");
//		
//		ServiceEnvelopeE serviceEnvelopeE = SavingPlanServiceEnvelopeE.instance();
//		SavingPlanServiceEnvelopeE.setUserId(serviceEnvelopeE, userId);
//		SavingPlanServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
//		SavingPlanServiceEnvelopeE.setFUID(serviceEnvelopeE, fuid);
//		
//		ServiceEnvelopeE response = (ServiceEnvelopeE)stubUtil.request(stub, serviceEnvelopeE);
//		if( response == null ){
//			res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
//			res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
//			res.setData(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
//			return res;
//		}
//		ServiceEnvelope serviceEnvelope = response.getServiceEnvelope();
		
//		ServiceEnvelope serviceEnvelope = new ServiceEnvelope();
//		ServiceBody serviceBody = new ServiceBody();
//		
//		// dummy response
//		RsDetail_type0 rsDetail_type0 = new RsDetail_type0();
//		RESConsumer_type0 resConsumer_type0 = new RESConsumer_type0();
//		
//		SPDetailInfo_type0 spDetailInfo_type0[] = new SPDetailInfo_type0[1];
//		SPDetailInfo_type0 detail = new SPDetailInfo_type0();
//		
//		detail.setAccountNo1("000010079050");
//		detail.setCurrencyType("MYR");
//		detail.setCurrentBalance(new BigDecimal(100000));
//		detail.setTargetBalance(new BigDecimal(200000));
//		detail.setInstallmentAmount(new BigDecimal(300000));
//		detail.setMaturityDate(new Date());
//		detail.setEffectiveDate(new Date());
//		detail.setSPTenor(new Byte("2"));
//		detail.setInterestRate(new BigDecimal(1000));
//		
//		spDetailInfo_type0[0] = detail;
//		resConsumer_type0.setSPDetailInfo(spDetailInfo_type0);
//		rsDetail_type0.setRESConsumer(resConsumer_type0);
//		
//		serviceBody.setRsDetail(rsDetail_type0);
//		serviceEnvelope.setServiceBody(serviceBody);
		
		// end of dummy response
		if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			if( serviceEnvelope.getServiceBody().getRsDetail()
					.getRESConsumer().isSPDetailInfoSpecified() ){
				List<SavingPlanResponse> data = service.getSummary(serviceEnvelope.getServiceBody().getRsDetail()
						.getRESConsumer().getSPDetailInfo());
				res.setData(data);
			}else{
				res.setData(new ArrayList<SavingPlanResponse>());
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
	
	public Response getAccountDetail(String userId, String fuid, String cifKey, String accountNumber) throws AxisFault, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		Response res = new Response();
		
		//stub
		WBST_Inquiry_DetailSavingsPlanStub stub = getStub();
		
		ServiceEnvelopeE serviceEnvelopeE = SavingPlanServiceEnvelopeE.instance();
		SavingPlanServiceEnvelopeE.setUserId(serviceEnvelopeE, userId);
		SavingPlanServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
		SavingPlanServiceEnvelopeE.setFUID(serviceEnvelopeE, fuid);
		SavingPlanServiceEnvelopeE.setRequestDetailAccount(serviceEnvelopeE, accountNumber);
		
		ServiceEnvelopeE response = (ServiceEnvelopeE)stubUtil.request(stub, serviceEnvelopeE);
		if( response == null ){
			res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
			res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
			res.setData(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
			return res;
		}
		ServiceEnvelope serviceEnvelope = response.getServiceEnvelope();
		
		// dummy response
		
//		ServiceEnvelope serviceEnvelope = new ServiceEnvelope();
//		ServiceBody serviceBody = new ServiceBody();
//		
//		// dummy response
//		RsDetail_type0 rsDetail_type0 = new RsDetail_type0();
//		RESConsumer_type0 resConsumer_type0 = new RESConsumer_type0();
//		
//		String strContextPath = CommonUtils.getContextPath(properties);
//		WBST_Inquiry_DetailSavingsPlanStub stub = new WBST_Inquiry_DetailSavingsPlanStub(strContextPath+"Services/WBST_Inquiry_DetailSavingsPlan/Processes/MainRequestResponse_HTTP");
//		
//		ServiceEnvelopeE serviceEnvelopeE = SavingPlanServiceEnvelopeE.instance();
//		SavingPlanServiceEnvelopeE.setUserId(serviceEnvelopeE, userId);
//		SavingPlanServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
//		SavingPlanServiceEnvelopeE.setFUID(serviceEnvelopeE, fuid);
//		
//		ServiceEnvelopeE response = (ServiceEnvelopeE)stubUtil.request(stub, serviceEnvelopeE);
//		if( response == null ){
//			res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
//			res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
//			res.setData(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
//			return res;
//		}
//		ServiceEnvelope serviceEnvelope = response.getServiceEnvelope();
		
//		ServiceEnvelope serviceEnvelope = new ServiceEnvelope();
//		ServiceBody serviceBody = new ServiceBody();
//		
//		// dummy response
//		RsDetail_type0 rsDetail_type0 = new RsDetail_type0();
//		RESConsumer_type0 resConsumer_type0 = new RESConsumer_type0();
//		
//		SPDetailInfo_type0 spDetailInfo_type0[] = new SPDetailInfo_type0[1];
//		SPDetailInfo_type0 detail = new SPDetailInfo_type0();
//		
//		detail.setAccountNo1("000010079050");
//		detail.setCurrencyType("MYR");
//		detail.setCurrentBalance(new BigDecimal(100000));
//		detail.setTargetBalance(new BigDecimal(200000));
//		detail.setInstallmentAmount(new BigDecimal(300000));
//		detail.setMaturityDate(new Date());
//		detail.setEffectiveDate(new Date());
//		detail.setSPTenor(new Byte("2"));
//		detail.setInterestRate(new BigDecimal(1000));
//		
//		spDetailInfo_type0[0] = detail;
//		resConsumer_type0.setSPDetailInfo(spDetailInfo_type0);
//		rsDetail_type0.setRESConsumer(resConsumer_type0);
//		
//		serviceBody.setRsDetail(rsDetail_type0);
//		serviceEnvelope.setServiceBody(serviceBody);
		
		// end of dummy response
		if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			if( serviceEnvelope.getServiceBody().getRsDetail()
					.getRESConsumer().isSPDetailInfoSpecified() ){
				List<SavingPlanResponse> data = service.getSummary(serviceEnvelope.getServiceBody().getRsDetail()
						.getRESConsumer().getSPDetailInfo());
				res.setData(data);
			}else{
				res.setData(new ArrayList<SavingPlanResponse>());
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
}
