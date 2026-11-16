package com.optima.oraclejdbc.utils;


import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.stream.XMLStreamException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Stub;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.context.ServiceContext;
import org.apache.axis2.databinding.ADBBean;
import org.apache.axis2.description.MessageContextListener;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.daoimpl.SoapMessageLogDao;
import com.optima.oraclejdbc.model.SoapMessageLog;
@Component
public class StubUtil {
	private static final Logger logger = Logger.getLogger(StubUtil.class);
	
	private static final String OPERATION_METHOD_NAME="operation";
	private static StubUtil instance = new StubUtil();
	
	@Resource(name="confProp")
	private java.util.Properties confProperties;
	
	@Autowired
	SoapMessageLogDao soapMessageLogDao;
	
	private StubUtil(){}
	
	public static StubUtil getInstance(){
		return instance;
	}
	
	public ADBBean request(final Stub stub, ADBBean serviceEnvelopeE) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> c = stub.getClass();
		Class<?> param[] = new Class<?>[1];
		param[0] = serviceEnvelopeE.getClass();
		int timeOutInMilliSeconds = CommonUtils.getStubTimeout(confProperties);
		stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(timeOutInMilliSeconds);
		stub._getServiceClient().getOptions().setProperty(
                  HTTPConstants.SO_TIMEOUT, new Integer(timeOutInMilliSeconds));
		stub._getServiceClient().getOptions().setProperty(
                  HTTPConstants.CONNECTION_TIMEOUT, new Integer(timeOutInMilliSeconds));
		
		stub._getServiceClient().getAxisService().addMessageContextListener(
				new MessageContextListener() {
				    public void attachServiceContextEvent(ServiceContext sc,
				        MessageContext mc) {}
				    public void attachEnvelopeEvent(MessageContext mc) {
				        try
				        { 
				        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
				        	mc.getEnvelope().cloneOMElement().serialize(baos);
				        	String xmlResponse = baos.toString();
				        	String xmlRequest = stub._getServiceClient().getLastOperationContext().getMessageContext("Out")
									.getEnvelope().toStringWithConsume();
				        	SoapMessageLog log = new SoapMessageLog();
									log.setRequestDate(new Date());
									log.setXmlResponse(xmlResponse);
									log.setXmlRequest(xmlRequest);
									soapMessageLogDao.create(log);
				        }
				        catch (XMLStreamException | AxisFault | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				        	logger.error("Error", e);				        	
				        }
				    }
				});
		Method method = c.getMethod(OPERATION_METHOD_NAME, param);
		ADBBean res = (ADBBean) method.invoke(stub, serviceEnvelopeE);
		
//		stub._getServiceClient().getLastOperationContext().getMessageContext("In")
//				.getEnvelope().toString();
		
		
		
		return res;
		
	}
}
