package com.optima.oraclejdbc.tibco.electronicdetails;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.axis2.AxisFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisp.schemas.soa.wsdl.service.wbst_maintain_electronicdetails.operationimpl.services.wbst__maintain__electronicdetails.processes.WBST_Maintain_ElectronicDetailsStub;
import com.ocbc.schemas.soa.emf.common.body.ErrorDetail_type0;
import com.ocbc.schemas.soa.emf.service.wbst_maintain_electronicdetails.ServiceEnvelope;
import com.ocbc.schemas.soa.emf.service.wbst_maintain_electronicdetails.ServiceEnvelopeE;
import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.daoimpl.DDIDao;
import com.optima.oraclejdbc.model.estatement.ESAccountInfo;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.StubUtil;

@Service
public class ElectronicDetailsRequest {

	@Autowired
	StubUtil stubUtil;
	
	@Autowired
	DDIDao ddiDao;
	
	@Resource(name="confProp")
	private java.util.Properties confProperties;
	
	public Response setCustomerAddress(String cifKey, String emailAddress) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		Response response = requestCustomerAddress(cifKey, emailAddress, "000");
		if( response.getResultCode() == ResponseErrorCode.SUCCESS ){
			ESAccountInfo esai = ddiDao.getEsAccountInfoByCif(cifKey);
			if( esai == null ){
				esai = new ESAccountInfo();
				esai.setCifNo(cifKey);
			}
			esai.setEmail(emailAddress);
			ddiDao.insertOrUpdate(esai);
		}
		return response;
	}
	
	public Response updateCustomerAddress(String cifKey, String emailAddress) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		return setCustomerAddress(cifKey, emailAddress);
	}
	
	private Response requestCustomerAddress(String cifKey, String emailAddress, String operationCode) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Response res = new Response();
		String strContextPath = CommonUtils.getContextPath(confProperties);
		try {
			WBST_Maintain_ElectronicDetailsStub stub = new WBST_Maintain_ElectronicDetailsStub(strContextPath+"Services/WBST_Maintain_ElectronicDetails/Processes/MainRequestResponse_HTTP");
//			WBST_Maintain_ElectronicDetailsStub stub = new WBST_Maintain_ElectronicDetailsStub(strContextPath+"services/ElectronicDetails_MainRequestResponse_HTTP");
			ServiceEnvelopeE serviceEnvelopeE = ElectronicDetailsServiceEnvelopeE.instance();
			ElectronicDetailsServiceEnvelopeE.setCustomerNumber(serviceEnvelopeE, cifKey);
			ElectronicDetailsServiceEnvelopeE.setElectronicAddressDescription(serviceEnvelopeE, emailAddress);
			ElectronicDetailsServiceEnvelopeE.setOperationCode(serviceEnvelopeE, operationCode);
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
				res.setData("Success");
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
		} catch (AxisFault e) {
			res.setResultCode(ResponseErrorCode.INTERNAL_SERVER_ERROR);
			res.setMessage(ResponseErrorCode.MSG_INTERNAL_SERVER_ERROR);
			List<String> errMsg = new ArrayList<String>();
			errMsg.add(e.getMessage());
			res.setErrMsg(errMsg);
			e.printStackTrace();
		}
		return res;
	}
}
