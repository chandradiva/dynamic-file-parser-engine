package com.optima.oraclejdbc.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailloan.LoanDetailInfo_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailloan.REQConsumer_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailloan.RESConsumer_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailloan.RqDetail_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailloan.RsDetail_type0;
import com.ocbc.schemas.soa.emf.common.body.ErrorDetail_type0;
import com.ocbc.schemas.soa.emf.common.header.CommonDetail_type0;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailloan.ServiceBody;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailloan.ServiceEnvelope;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailloan.ServiceEnvelopeE;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailloan.ServiceHeader;
import com.ocbc.schemas.soa.wsdl.service.wbst_inquiry_detailloan.operationimpl.services.wbst__inquiry__detailloan.processes.WBST_Inquiry_DetailLOANStub;
import com.optima.oraclejdbc.api.ReturnData;
import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.response.dailystatement.SavingPlanResponse;
import com.optima.oraclejdbc.response.loan.LoanResponse;
import com.optima.oraclejdbc.service.LoanService;
import com.optima.oraclejdbc.tibco.loan.LoanServiceEnvelopeE;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.DateUtility;
import com.optima.oraclejdbc.utils.StubUtil;

@RestController
@RequestMapping(value="/loan")
public class LoanController {
	private static final Logger logger = Logger.getLogger(LoanController.class);
	
	@Autowired
	LoanService service;
	
	@Autowired
	StubUtil stubUtil;
	
	@Resource(name="confProp")
	private java.util.Properties confProperties;
	
	@RequestMapping(value = "getsummary", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getSummary(@RequestParam String userId, @RequestParam String fuid,
			@RequestParam String cifKey) {
		try {
			Response res = new Response();
			
			//stub
			String strContextPath = CommonUtils.getContextPath(confProperties);
			
			WBST_Inquiry_DetailLOANStub stub = new WBST_Inquiry_DetailLOANStub(strContextPath+"Services/WBST_Inquiry_DetailLOAN/Processes/MainRequestResponse_HTTP");
//			WBST_Inquiry_DetailLOANStub stub = new WBST_Inquiry_DetailLOANStub(strContextPath+"services/Loan_MainRequestResponse__HTTP");
			ServiceEnvelopeE serviceEnvelopeE = LoanServiceEnvelopeE.instance();
			LoanServiceEnvelopeE.setUserId(serviceEnvelopeE, userId);
			LoanServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
			LoanServiceEnvelopeE.setFUID(serviceEnvelopeE, fuid);
			
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
						.getRESConsumer().isLoanDetailInfoSpecified() ){
					List<LoanResponse> data = service.getSummary(serviceEnvelope.getServiceBody().getRsDetail()
							.getRESConsumer().getLoanDetailInfo());
					
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
}
