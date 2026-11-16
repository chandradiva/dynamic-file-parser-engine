package com.optima.oraclejdbc.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailsavingsplan.REQConsumer_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailsavingsplan.RESConsumer_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailsavingsplan.RqDetail_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailsavingsplan.RsDetail_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailsavingsplan.SPDetailInfo_type0;
import com.ocbc.schemas.soa.emf.common.body.ErrorDetail_type0;
import com.ocbc.schemas.soa.emf.common.header.CommonDetail_type0;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailsavingsplan.ServiceBody;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailsavingsplan.ServiceEnvelope;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailsavingsplan.ServiceEnvelopeE;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailsavingsplan.ServiceHeader;
import com.ocbc.schemas.soa.wsdl.service.wbst_inquiry_detailsavingsplan.operationimpl.services.wbst__inquiry__detailsavingsplan.processes.WBST_Inquiry_DetailSavingsPlanStub;
import com.optima.oraclejdbc.api.ReturnData;
import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.response.dailystatement.SavingPlanResponse;
import com.optima.oraclejdbc.response.dailystatement.TimedDepositResponse;
import com.optima.oraclejdbc.service.SavingPlanService;
import com.optima.oraclejdbc.tibco.savingplan.SavingPlanRequest;
import com.optima.oraclejdbc.tibco.savingplan.SavingPlanServiceEnvelopeE;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.StubUtil;

@RestController
@RequestMapping(value="/savingplan")
public class SavingPlanController {

	private static final Logger logger = Logger.getLogger(SavingPlanController.class);
			
	@Autowired
	SavingPlanRequest savingPlanRequest;
	
	@RequestMapping(value = "getsummary", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getSavingPlanSummary(@RequestParam String userId, @RequestParam String fuid,
			@RequestParam String cifKey) {
		try{
			return savingPlanRequest.getSummary(userId, fuid, cifKey);
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
	}
	
	@RequestMapping(value = "getaccdetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getSavingPlanAccountDetail(@RequestParam String userId, @RequestParam String fuid,
			@RequestParam String cifKey, @RequestParam String accountNumber) {
		try{
			return savingPlanRequest.getAccountDetail(userId, fuid, cifKey, accountNumber);
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}
}
