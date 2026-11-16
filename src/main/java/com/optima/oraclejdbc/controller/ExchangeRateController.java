package com.optima.oraclejdbc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ocbc.schemas.soa.emf.common.body.ErrorDetail_type0;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_exchangerate.ServiceEnvelope;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_exchangerate.ServiceEnvelopeE;
import com.ocbc.schemas.soa.wsdl.service.wbst_inquiry_exchangerate.operationimpl.services.wbst__inquiry__exchangerate.processes.WBST_Inquiry_ExchangeRateStub;
import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.daoimpl.KursInfoDao;
import com.optima.oraclejdbc.model.KursInfo;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.response.exchangerate.ExchangeRateResponse;
import com.optima.oraclejdbc.service.ExchangeRateService;
import com.optima.oraclejdbc.tibco.exchangerate.ExchangeRateServiceEnvelopeE;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.StubUtil;

@RestController
@RequestMapping(value="/exchangerate")
public class ExchangeRateController {
	private static final Logger logger = Logger.getLogger(ExchangeRateController.class);
	
	@Autowired
	StubUtil stubUtil;
	
	@Autowired
	ExchangeRateService service;
	
	@Resource(name="confProp")
	private java.util.Properties confProperties;
	
	@Autowired
	KursInfoDao kursInfoDao;
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public Response postExchangeRate(@RequestParam String loginId, @RequestParam String cifKey){
		try{
			Response res = new Response();
			
			String strContextPath = CommonUtils.getContextPath(confProperties);
			WBST_Inquiry_ExchangeRateStub stub = new WBST_Inquiry_ExchangeRateStub(strContextPath+"Services/WBST_Inquiry_ExchangeRate/Processes/MainRequestResponse_HTTP");
//			WBST_Inquiry_ExchangeRateStub stub = new WBST_Inquiry_ExchangeRateStub(strContextPath+"services/ExchangeRate_MainRequestResponse__HTTP");
			ServiceEnvelopeE serviceEnvelopeE = ExchangeRateServiceEnvelopeE.instance();
			ExchangeRateServiceEnvelopeE.setUserId(serviceEnvelopeE, loginId);
			ExchangeRateServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
			
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
						.getRESConsumer().isFXDetailListSpecified() ){
					List<KursInfo> exchangeData = service.getExchangeRate(serviceEnvelope.getServiceBody().getRsDetail()
							.getRESConsumer().getFXDetailList());
					
					kursInfoDao.save(exchangeData);
					ExchangeRateResponse data = new ExchangeRateResponse();
					data.setLastUpdate(new Date());
					data.setData(exchangeData);
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
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public Response getExchangeRate(){
		try{
			Response res = new Response();
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			List<KursInfo> exchangeData = service.getAll();
			ExchangeRateResponse data = new ExchangeRateResponse();
			data.setLastUpdate(service.getKursInfoUpdatedAt());
			data.setData(exchangeData);
			res.setData(data);
			return res;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
	}
}
