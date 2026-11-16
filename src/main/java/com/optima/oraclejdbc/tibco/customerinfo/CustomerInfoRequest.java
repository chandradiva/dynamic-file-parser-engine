package com.optima.oraclejdbc.tibco.customerinfo;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.axis2.AxisFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisp.schemas.soa.wsdl.service.wbst_inquiry_customerinfo.operationimpl.services.wbst__inquiry__customerinfo.processes.WBST_Inquiry_CustomerInfoStub;
import com.ocbc.schemas.soa.emf.common.body.ErrorDetail_type0;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_customerinfo.ServiceEnvelope;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_customerinfo.ServiceEnvelopeE;
import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.model.UserInfo;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.StubUtil;

@Service
public class CustomerInfoRequest {
	@Autowired
	StubUtil stubUtil;
	
	@Resource(name="confProp")
	private java.util.Properties confProperties;
	
	public Response getUserInfo(String cifKey) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Response res = new Response();
		
		String strContextPath = CommonUtils.getContextPath(confProperties);
		try {
			WBST_Inquiry_CustomerInfoStub stub = new WBST_Inquiry_CustomerInfoStub(strContextPath+"Services/WBST_Inquiry_CustomerInfo/Processes/MainRequestResponse_HTTP");
//			WBST_Inquiry_CustomerInfoStub stub = new WBST_Inquiry_CustomerInfoStub(strContextPath+"services/CustomerInfo_MainRequestResponse__HTTP");
			ServiceEnvelopeE serviceEnvelopeE = CustomerInfoServiceEnvelopeE.instance();
			CustomerInfoServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
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
				UserInfo userInfo = new UserInfo(serviceEnvelope.getServiceBody().getRsDetail()
						.getRESConsumer());
				res.setData(userInfo);
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
