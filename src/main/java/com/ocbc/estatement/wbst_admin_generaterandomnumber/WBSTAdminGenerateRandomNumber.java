package com.ocbc.estatement.wbst_admin_generaterandomnumber;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.UUID;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HTTPConstants;

import com.ocbc.schemas.soa.accounts.service.wbst_admin_generaterandomnumber.REQConsumer_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_admin_generaterandomnumber.REQCorporate_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_admin_generaterandomnumber.RqDetail_type0;
import com.ocbc.schemas.soa.emf.common.body.ErrorDetail_type0;
//import com.ocbc.schemas.soa.emf.common.envelope.ServiceBody;
//import com.ocbc.schemas.soa.emf.common.envelope.ServiceEnvelope;
//import com.ocbc.schemas.soa.emf.common.envelope.ServiceEnvelopeE;
//import com.ocbc.schemas.soa.emf.common.envelope.ServiceHeader;
//import com.ocbc.schemas.soa.emf.common.envelope.ServiceHeaderChoice_type1;
import com.ocbc.schemas.soa.emf.common.header.ClientControl_type0;
import com.ocbc.schemas.soa.emf.common.header.ClientDetail_type0;
import com.ocbc.schemas.soa.emf.common.header.CommonDetailChoice_type2;
import com.ocbc.schemas.soa.emf.common.header.CommonDetailChoice_type3;
import com.ocbc.schemas.soa.emf.common.header.CommonDetail_type0;
import com.ocbc.schemas.soa.emf.common.header.ProviderControl_type0;
import com.ocbc.schemas.soa.emf.common.header.RecordControlChoice_type0;
import com.ocbc.schemas.soa.emf.common.header.RecordControl_type0;
import com.ocbc.schemas.soa.emf.common.header.RegionDetail_type0;
import com.ocbc.schemas.soa.emf.common.header.RemarkDetail_type0;
import com.ocbc.schemas.soa.emf.common.header.RqNature_type0;
import com.ocbc.schemas.soa.emf.common.header.SecurityContext_type0;
import com.ocbc.schemas.soa.emf.common.header.SecurityDetail;
import com.ocbc.schemas.soa.emf.common.header.SecurityDetail_type0;
//import com.ocbc.schemas.soa.emf.common.header.ServiceHeader;
import com.ocbc.schemas.soa.emf.common.header.UserDetail_type0;
import com.ocbc.schemas.soa.emf.service.wbst_admin_generaterandomnumber.ServiceBody;
import com.ocbc.schemas.soa.emf.service.wbst_admin_generaterandomnumber.ServiceEnvelope;
import com.ocbc.schemas.soa.emf.service.wbst_admin_generaterandomnumber.ServiceEnvelopeE;
import com.ocbc.schemas.soa.emf.service.wbst_admin_generaterandomnumber.ServiceHeader;
import com.ocbc.schemas.soa.wsdl.service.wbst_admin_generaterandomnumber.operationimpl.services.wbst__admin__generaterandomnumber.processes.FaultMessage;
import com.ocbc.schemas.soa.wsdl.service.wbst_admin_generaterandomnumber.operationimpl.services.wbst__admin__generaterandomnumber.processes.WBST_Admin_GenerateRandomNumber;
import com.ocbc.schemas.soa.wsdl.service.wbst_admin_generaterandomnumber.operationimpl.services.wbst__admin__generaterandomnumber.processes.WBST_Admin_GenerateRandomNumberStub;
//import com.ocbc.schemas.soa.wsdl.service.wbst_admin_generaterandomnumber.operationimpl.services.wbst__admin__generaterandomnumber.processes.MainRequestResponse__HTTPStub;
import com.optima.oraclejdbc.utils.CommonUtils;

public class WBSTAdminGenerateRandomNumber {
	public static void main(String args[]){
//		try {
//			ServiceEnvelope response=generateRandomNumberRequest();			
//			//System.out.println("Response: "+response.toString());
//			if(response.getServiceBody().getRsDetail()!=null){
//				System.out.println(response.getServiceBody().getRsDetail().getRESConsumer().getRandomID());
//			}else{
//				ErrorDetail_type0[] error=response.getServiceBody().getError().getErrorDetail();
//				for(ErrorDetail_type0 errorDetail_type0 :error){
//					System.out.println(errorDetail_type0.getErrorCode()+":"+ errorDetail_type0.getErrorDescription());
//				}
//			}
//			
//		} catch (AxisFault e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FaultMessage e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
//	public static ServiceEnvelope generateRandomNumberRequest(String strContextPath) throws RemoteException, FaultMessage{
//		
//	}

}
