package com.optima.oraclejdbc.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_exchangerate.FXDetailListSequenceE;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_exchangerate.FXDetailList_type0;
import com.ocbc.schemas.soa.emf.common.body.ErrorDetail_type0;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_exchangerate.ServiceEnvelope;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_exchangerate.ServiceEnvelopeE;
import com.ocbc.schemas.soa.wsdl.service.wbst_inquiry_exchangerate.operationimpl.services.wbst__inquiry__exchangerate.processes.WBST_Inquiry_ExchangeRateStub;
import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.daoimpl.KursInfoDao;
import com.optima.oraclejdbc.model.KursInfo;
import com.optima.oraclejdbc.response.exchangerate.ExchangeRateResponse;
import com.optima.oraclejdbc.tibco.exchangerate.ExchangeRateServiceEnvelopeE;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.StubUtil;

@Service
public class ExchangeRateService {
	private static final Logger logger = Logger.getLogger(ExchangeRateService.class);
	
	@Resource(name="confProp")
	private java.util.Properties confProperties;
	
	@Autowired
	KursInfoDao kursInfoDao;
	
	@Autowired
	StubUtil stubUtil;
	
	public List<KursInfo> getExchangeRate(FXDetailList_type0 fxDetailList) {
		
		List<KursInfo> res = new ArrayList<KursInfo>();
		for (FXDetailListSequenceE seq : fxDetailList.getFXDetailListSequence()) {
			KursInfo response = new KursInfo(seq.getFXDetails());
			if( !response.getCurrency().equals("IDR") )
				res.add(response);
		}
		return res;
	}
	
	public void getExchangeRate(){
		try{
			String strContextPath = CommonUtils.getContextPath(confProperties);
			WBST_Inquiry_ExchangeRateStub stub = new WBST_Inquiry_ExchangeRateStub(strContextPath+"Services/WBST_Inquiry_ExchangeRate/Processes/MainRequestResponse_HTTP");
//			WBST_Inquiry_ExchangeRateStub stub = new WBST_Inquiry_ExchangeRateStub(strContextPath+"services/ExchangeRate_MainRequestResponse__HTTP");
			ServiceEnvelopeE serviceEnvelopeE = ExchangeRateServiceEnvelopeE.instance();
			ExchangeRateServiceEnvelopeE.setUserId(serviceEnvelopeE, "000000000000");
			ExchangeRateServiceEnvelopeE.setCifKey(serviceEnvelopeE, "0000000000000000000");
			
			ServiceEnvelopeE response = (ServiceEnvelopeE)stubUtil.request(stub, serviceEnvelopeE);
			if( response == null ){				
				return;
			}
			ServiceEnvelope serviceEnvelope = response.getServiceEnvelope();
			if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
				if( serviceEnvelope.getServiceBody().getRsDetail()
						.getRESConsumer().isFXDetailListSpecified() ){
					List<KursInfo> exchangeData = getExchangeRate(serviceEnvelope.getServiceBody().getRsDetail()
							.getRESConsumer().getFXDetailList());
					
					kursInfoDao.save(exchangeData);										
				}
			}
		}catch( Exception e){			
			logger.error("Error", e);					
		}
	}
	
	public List<KursInfo> getAll() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		return kursInfoDao.getAll();
	}
	
	public Date getKursInfoUpdatedAt(){
		return kursInfoDao.getKursInfoUpdatedAt();
	}
}

	
