package com.optima.oraclejdbc.tibco.exchangerate;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.UUID;

import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_exchangerate.REQConsumer_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_exchangerate.RqDetail_type0;
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
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_exchangerate.ServiceBody;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_exchangerate.ServiceEnvelope;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_exchangerate.ServiceEnvelopeE;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_exchangerate.ServiceHeader;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_exchangerate.ServiceHeaderChoice_type1;

public class ExchangeRateServiceEnvelopeE {
	public static ServiceEnvelopeE instance(){
		ServiceEnvelopeE serviceEnvelopeE = new ServiceEnvelopeE();
		ServiceEnvelope serviceEnvelope = new ServiceEnvelope();
		ServiceBody serviceBody = new ServiceBody();
		
		//request
		ServiceHeader serviceHeader = new ServiceHeader();
		CommonDetail_type0 commonDetail_type0 = new CommonDetail_type0();
		
		commonDetail_type0.setServiceName("WBST_Inquiry_ExchangeRate");
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
		remarkDetail_type0.setRemarkActor("system");
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
		serviceHeader.setCommonDetail(commonDetail_type0);
		
		RqDetail_type0 rqDetail_type0 = new RqDetail_type0();
		REQConsumer_type0 reqConsumer_type0 = new REQConsumer_type0();
		
		rqDetail_type0.setREQConsumer(reqConsumer_type0);
		serviceBody.setRqDetail(rqDetail_type0);
		serviceEnvelope.setServiceHeader(serviceHeader);
		serviceEnvelope.setServiceBody(serviceBody);
		
		serviceEnvelopeE.setServiceEnvelope(serviceEnvelope);
		return serviceEnvelopeE;
	}
	
	public static void setUserId(ServiceEnvelopeE serviceEnvelopeE, String userId){
		serviceEnvelopeE.getServiceEnvelope().getServiceBody().getRqDetail().getREQConsumer().setLoginID(userId);
	}
	
	public static void setCifKey(ServiceEnvelopeE serviceEnvelopeE, String cifKey){
		serviceEnvelopeE.getServiceEnvelope().getServiceBody().getRqDetail().getREQConsumer().setCIFKey(cifKey);
	}
}
